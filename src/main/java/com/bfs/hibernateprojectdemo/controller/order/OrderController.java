package com.bfs.hibernateprojectdemo.controller.order;

import com.bfs.hibernateprojectdemo.dto.order.OrderRequest;
import com.bfs.hibernateprojectdemo.dto.order.UserAllOrdersDTO;
import com.bfs.hibernateprojectdemo.exception.PlaceOrderException;
import com.bfs.hibernateprojectdemo.service.order.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<UserAllOrdersDTO> getUserAllOrders(){
        // TODO: to be tested
        UserAllOrdersDTO userAllOrders = orderService.getUserAllOrders();
        return ResponseEntity.ok(userAllOrders);
    }

    @PostMapping("")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity placeOrder(@Valid @RequestBody OrderRequest request) throws PlaceOrderException {
        orderService.placeOrder(request);
        return new ResponseEntity(HttpStatus.CREATED);
    }
}
