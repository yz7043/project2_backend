package com.bfs.hibernateprojectdemo.dto.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InStockProductsResponse {
    private List<ProductUserDTO> products;
}
