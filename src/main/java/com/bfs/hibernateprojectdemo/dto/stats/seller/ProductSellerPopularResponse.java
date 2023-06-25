package com.bfs.hibernateprojectdemo.dto.stats.seller;

import com.bfs.hibernateprojectdemo.dto.common.StatusResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProductSellerPopularResponse {
    private StatusResponse status;
    private List<ProductSellerPopularDTO> popularProducts;
}
