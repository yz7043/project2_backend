package com.bfs.hibernateprojectdemo.dto.order.orderdetail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderItemDetailDTO {
    private Long id; // order item id
    private Double purchasedPrice;
    private Integer quantity;
    private OrderProductDetailDTO product;
}
