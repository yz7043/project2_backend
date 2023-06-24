package com.bfs.hibernateprojectdemo.service.order;

import com.bfs.hibernateprojectdemo.dao.OrderDao;
import com.bfs.hibernateprojectdemo.dao.ProductDao;
import com.bfs.hibernateprojectdemo.dao.UserDao;
import com.bfs.hibernateprojectdemo.domain.*;
import com.bfs.hibernateprojectdemo.dto.order.OrderItemRequest;
import com.bfs.hibernateprojectdemo.dto.order.OrderRequest;
import com.bfs.hibernateprojectdemo.dto.order.UserAllOrdersDTO;
import com.bfs.hibernateprojectdemo.dto.order.UserOrderDTO;
import com.bfs.hibernateprojectdemo.exception.PlaceOrderException;
import com.bfs.hibernateprojectdemo.security.AuthUserDetail;
import com.bfs.hibernateprojectdemo.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    private final OrderDao orderDao;
    private final UserDao userDao;

    private final ProductDao productDao;

    @Autowired
    public OrderService(OrderDao orderDao, UserDao userDao, ProductDao productDao) {
        this.orderDao = orderDao;
        this.userDao = userDao;
        this.productDao = productDao;
    }

    private AuthUserDetail getLoginUser(){
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        AuthUserDetail loginUser = (AuthUserDetail) authentication.getPrincipal();
        return loginUser;
    }


    public UserAllOrdersDTO getUserAllOrders(){
        AuthUserDetail loginUser = getLoginUser();
        List<Order> orders = orderDao.getAllByUsername(loginUser.getUsername());
        List<UserOrderDTO> ordersDTO = new ArrayList<>();
        for(Order order : orders){
            ordersDTO.add(
                    UserOrderDTO.builder()
                            .orderId(order.getId())
                            .datePlaced(order.getDatePlaced())
                            .status(order.getStatus())
                            .username(order.getUser().getUsername())
                            .userId(order.getUser().getId())
                            .build()
            );
        }
        return UserAllOrdersDTO.builder()
                .orders(ordersDTO)
                .build();
    }

    @Transactional
    public void placeOrder(OrderRequest request) throws PlaceOrderException {
        AuthUserDetail loginUser = getLoginUser();
        User user = userDao.loadUserByUsername(loginUser.getUsername()).get();
        List<OrderItemRequest> items = request.getOrder();
        Order order = Order.builder()
                .datePlaced(DateUtils.roundToMicroseconds(LocalDateTime.now()))
                .status(DomainConst.ORDER_PROCESSING)
                .user(user)
                .build();
        for(OrderItemRequest item : items){
            Long productId = item.getProductId();
            Integer quantity = item.getQuantity();
            Product product = productDao.getProductById(productId);
            if(product == null){
                throw new PlaceOrderException("No such product: " + product.getName() + "!");
            }
            if(product.getQuantity() < quantity){
                throw new PlaceOrderException("Not enough " + product.getName() + "!");
            }
            OrderItem orderItem = OrderItem.builder()
                                    .purchasedPrice(product.getRetailPrice())
                                    .quantity(quantity)
                                    .wholesalePrice(product.getWholesalePrice())
                                    .order(order)
                                    .product(product)
                                    .build();
            product.setQuantity(product.getQuantity() - quantity);
            order.getOrderItems().add(orderItem);
            productDao.updateProduct(product);
        }
        orderDao.addOrder(order);
    }
}