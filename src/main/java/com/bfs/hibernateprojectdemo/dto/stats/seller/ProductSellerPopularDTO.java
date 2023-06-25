package com.bfs.hibernateprojectdemo.dto.stats.seller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProductSellerPopularDTO {
    private Long productId;
    private String name;
    private String description;
    private Integer sales;
}
