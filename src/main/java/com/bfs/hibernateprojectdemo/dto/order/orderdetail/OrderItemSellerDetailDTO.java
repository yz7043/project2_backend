package com.bfs.hibernateprojectdemo.dto.order.orderdetail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OrderItemSellerDetailDTO {
    private Long id; // order item id
    private Double purchasedPrice;
    private Integer quantity;
    private Double wholesalePrice;
    private OrderProductSellerDetailDTO product;
}
