package com.bfs.hibernateprojectdemo.dto.order.orderdetail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder

public class OrderProductSellerDetailDTO{
    private Long id; // product id
    private String name;
    private String description;
    private Double retailPrice;
    protected Double wholesalePrice;
}
