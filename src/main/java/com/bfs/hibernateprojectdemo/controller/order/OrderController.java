package com.bfs.hibernateprojectdemo.controller.order;

import com.bfs.hibernateprojectdemo.dto.base.BaseSuccessResponse;
import com.bfs.hibernateprojectdemo.dto.order.OrderRequest;
import com.bfs.hibernateprojectdemo.dto.order.UserAllOrdersDTO;
import com.bfs.hibernateprojectdemo.dto.order.orderdetail.OrderDetailResponse;
import com.bfs.hibernateprojectdemo.dto.order.seller.PagedResponse;
import com.bfs.hibernateprojectdemo.exception.NotEnoughInventoryException;
import com.bfs.hibernateprojectdemo.exception.OrderStatusTransferException;
import com.bfs.hibernateprojectdemo.exception.ResourceNotFoundException;
import com.bfs.hibernateprojectdemo.service.order.OrderService;
import com.bfs.hibernateprojectdemo.utils.AuthUserUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('USER') || hasAuthority('SELLER')")
    public ResponseEntity<?> getUserAllOrders(@RequestParam(defaultValue = "0") int page){

        if(AuthUserUtils.isUser()){
            UserAllOrdersDTO userAllOrders = orderService.getUserAllOrders();
            return ResponseEntity.ok(userAllOrders);
        }else if(AuthUserUtils.isSeller()){
            PagedResponse allOrder = orderService.getAllOrder(page);
            return ResponseEntity.ok(allOrder);
        }
        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<BaseSuccessResponse> placeOrder(@Valid @RequestBody OrderRequest request) throws NotEnoughInventoryException {
        orderService.placeOrder(request);
        return new ResponseEntity(BaseSuccessResponse.builder().message("Order has been placed!").build()
                , HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('USER') || hasAuthority('SELLER')")
    public ResponseEntity<OrderDetailResponse> getOrderDetail(@PathVariable("id") Long orderId) throws ResourceNotFoundException {
        OrderDetailResponse orderDetail = orderService.getOrderDetail(orderId);
        return ResponseEntity.ok(orderDetail);
    }

    @PatchMapping("/{id}/cancel")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<BaseSuccessResponse> cancelOrder(@PathVariable("id") Long orderId) {
        orderService.cancelOrderUserByOrderId(orderId);
        return ResponseEntity.ok(BaseSuccessResponse.builder()
                .message("Cancelled successfully!")
                .build());
    }

}
