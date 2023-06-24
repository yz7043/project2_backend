package com.bfs.hibernateprojectdemo.dto.order.orderdetail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderProductDetailDTO {
    private Long id; // product id
    private String name;
    private String description;
    private Double retailPrice;
}
