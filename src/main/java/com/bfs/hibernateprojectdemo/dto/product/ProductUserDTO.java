package com.bfs.hibernateprojectdemo.dto.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductUserDTO {
    private Long id;
    private String description;
    private String name;
    private Integer quantity;
    private Double retailPrice;
}
