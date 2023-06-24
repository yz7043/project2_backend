package com.bfs.hibernateprojectdemo.controller.product;

import com.bfs.hibernateprojectdemo.dto.common.StatusResponse;
import com.bfs.hibernateprojectdemo.dto.product.InStockProductsResponse;
import com.bfs.hibernateprojectdemo.dto.product.UserProductDTO;
import com.bfs.hibernateprojectdemo.dto.stats.ProductFrequencyDTO;
import com.bfs.hibernateprojectdemo.dto.stats.ProductFrequencyResponse;
import com.bfs.hibernateprojectdemo.dto.stats.ProductRecentDto;
import com.bfs.hibernateprojectdemo.dto.stats.ProductRecentResponse;
import com.bfs.hibernateprojectdemo.exception.ResourceNotFoundException;
import com.bfs.hibernateprojectdemo.service.product.ProductService;
import com.bfs.hibernateprojectdemo.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("products")
public class ProductController {
    private final ProductService productService;
    private final UserService userService;

    @Autowired
    public ProductController(ProductService productService, UserService userService) {
        this.productService = productService;
        this.userService = userService;
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<InStockProductsResponse> getAllInStockProduct(){
        List<UserProductDTO> products = productService.getAllProductForUser();
        InStockProductsResponse response = InStockProductsResponse.builder()
                                            .products(products)
                                            .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<UserProductDTO> getProductById(@PathVariable("id") Long id)
            throws ResourceNotFoundException {
        UserProductDTO product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/frequent/{limit}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<ProductFrequencyResponse> getTopFrequentProducts(@PathVariable("limit") Integer limit){
        if(limit <= 0)
            limit = 3;
        List<ProductFrequencyDTO> result = userService.getFrequentProducts(limit);
        return ResponseEntity.ok(ProductFrequencyResponse
                .builder()
                .status(StatusResponse.builder().message("Found!").success(true).build())
                .frequentProducts(result)
                .build());
    }

    @GetMapping("/recent/{limit}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<ProductRecentResponse> getRecentProducts(@PathVariable("limit") Integer limit){
        if(limit <= 0)
            limit = 3;
        List<ProductRecentDto> result = userService.getRecentProducts(limit);
        return ResponseEntity.ok(
                ProductRecentResponse
                        .builder()
                        .status(StatusResponse.builder().message("Found!").success(true).build())
                        .recentProducts(result)
                        .build()
        );
    }
}
