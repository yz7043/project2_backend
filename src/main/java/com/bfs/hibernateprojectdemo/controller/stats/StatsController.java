package com.bfs.hibernateprojectdemo.controller.stats;

import com.bfs.hibernateprojectdemo.dto.common.StatusResponse;
import com.bfs.hibernateprojectdemo.dto.stats.seller.ProductSellerPopularDTO;
import com.bfs.hibernateprojectdemo.dto.stats.seller.ProductSellerPopularResponse;
import com.bfs.hibernateprojectdemo.dto.stats.seller.ProductSellerProfitDTO;
import com.bfs.hibernateprojectdemo.dto.stats.seller.ProductSellerProfitResponse;
import com.bfs.hibernateprojectdemo.service.product.SellerProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("products")
public class StatsController {
    private SellerProductService sellerProductService;
    @Autowired
    public void setSellerProductService(SellerProductService sellerProductService){
        this.sellerProductService = sellerProductService;
    }

    @GetMapping("/items")
    @PreAuthorize("hasAuthority('SELLER')")
    public ResponseEntity<ProductSellerPopularResponse> getTotalProductAmount(){
        List<ProductSellerPopularDTO> result = sellerProductService.getAllItemsSold();
        return ResponseEntity.ok(
                ProductSellerPopularResponse
                        .builder()
                        .status(StatusResponse.builder().message("Found!").success(true).build())
                        .popularProducts(result)
                        .build()
        );
    }

    @GetMapping("/popular/{limit}")
    @PreAuthorize("hasAuthority('SELLER')")
    public ResponseEntity<ProductSellerPopularResponse> getMostPopularProduct(
            @PathVariable("limit") Integer limit
    ){
        if(limit <= 0)
            limit = 3;
        List<ProductSellerPopularDTO> result = sellerProductService.getPopularProducts(limit);

        return ResponseEntity.ok(
                ProductSellerPopularResponse
                        .builder()
                        .status(StatusResponse.builder().message("Found!").success(true).build())
                        .popularProducts(result)
                        .build()
        );
    }

    @GetMapping("/profit/{limit}")
    public ResponseEntity<ProductSellerProfitResponse> getMostProfitableProduct(
            @PathVariable("limit") Integer limit
    ){
        if(limit <= 0)
            limit = 3;
        List<ProductSellerProfitDTO> result = sellerProductService.getProfitableProducts(limit);
        return ResponseEntity.ok(
                ProductSellerProfitResponse
                        .builder()
                        .status(StatusResponse.builder().message("Found!").success(true).build())
                        .profitableProducts(result)
                        .build()
        );
    }
}
