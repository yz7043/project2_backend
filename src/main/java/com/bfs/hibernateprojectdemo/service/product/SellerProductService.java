package com.bfs.hibernateprojectdemo.service.product;

import com.bfs.hibernateprojectdemo.dao.OrderDao;
import com.bfs.hibernateprojectdemo.dao.ProductDao;
import com.bfs.hibernateprojectdemo.domain.DomainConst;
import com.bfs.hibernateprojectdemo.domain.Order;
import com.bfs.hibernateprojectdemo.domain.OrderItem;
import com.bfs.hibernateprojectdemo.domain.Product;
import com.bfs.hibernateprojectdemo.dto.stats.seller.ProductSellerPopularDTO;
import com.bfs.hibernateprojectdemo.dto.stats.seller.ProductSellerProfitDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class SellerProductService {
    private ProductDao productDao;
    @Autowired
    public void setProductDao(ProductDao productDao){
        this.productDao = productDao;
    }

    private OrderDao orderDao;

    @Autowired
    public void setOrderDao(OrderDao orderDao){
        this.orderDao = orderDao;
    }

    @Transactional
    public List<ProductSellerPopularDTO> getPopularProducts(Integer limit){
        List<ProductSellerPopularDTO> result = getSoldItemInfo();
        result.sort((o1, o2) -> o2.getSales().compareTo(o1.getSales()));
        return result.subList(0, Math.min(limit, result.size()));
    }

    @Transactional
    public List<ProductSellerProfitDTO> getProfitableProducts(Integer limit){
        HashMap<Long, ProductSellerProfitDTO> dic = new HashMap<>();
        List<Order> orders = orderDao.getAll();
        for(Order order : orders){
            if(!order.getStatus().equals(DomainConst.ORDER_COMPLETED))
                continue;
            for(OrderItem orderItem : order.getOrderItems()){
                Product product = orderItem.getProduct();
                Double profitPerItem = orderItem.getPurchasedPrice() - orderItem.getWholesalePrice();
                Double fullProfit = profitPerItem * orderItem.getQuantity();
                ProductSellerProfitDTO dto = dic.get(product.getId());
                if(dto == null){
                    dto = ProductSellerProfitDTO
                            .builder()
                            .productId(product.getId())
                            .name(product.getName())
                            .description(product.getDescription())
                            .profit(0.0)
                            .build();
                    dic.put(product.getId(), dto);
                }
                dto.setProfit(dto.getProfit() + fullProfit);
            }
        }
        List<ProductSellerProfitDTO> result = new ArrayList<>(dic.values());
        result.sort((o1, o2) -> o2.getProfit().compareTo(o1.getProfit()));
        return result.subList(0, Math.min(limit, result.size()));
    }

    @Transactional
    public List<ProductSellerPopularDTO> getAllItemsSold(){
        List<ProductSellerPopularDTO> soldItemInfo = getSoldItemInfo();
        return soldItemInfo;
    }

    private List<ProductSellerPopularDTO> getSoldItemInfo(){
        HashMap<Long, ProductSellerPopularDTO> dic = new HashMap<>();
        List<Order> orders = orderDao.getAll();
        for(Order order : orders){
            if(!order.getStatus().equals(DomainConst.ORDER_COMPLETED))
                continue;
            for(OrderItem orderItem : order.getOrderItems()){
                Product product = orderItem.getProduct();
                ProductSellerPopularDTO dto = dic.get(product.getId());
                if(dto == null){
                    dto = ProductSellerPopularDTO
                            .builder()
                            .productId(product.getId())
                            .name(product.getName())
                            .description(product.getDescription())
                            .sales(0)
                            .build();
                    dic.put(product.getId(), dto);
                }
                dto.setSales(dto.getSales() + orderItem.getQuantity());
            }
        }
        List<ProductSellerPopularDTO> result = new ArrayList<>(dic.values());
        return result;
    }
}
