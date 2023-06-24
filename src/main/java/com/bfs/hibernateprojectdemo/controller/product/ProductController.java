package com.bfs.hibernateprojectdemo.controller.product;

import com.bfs.hibernateprojectdemo.dto.product.InStockProductsResponse;
import com.bfs.hibernateprojectdemo.dto.product.UserProductDTO;
import com.bfs.hibernateprojectdemo.exception.ResourceNotFoundException;
import com.bfs.hibernateprojectdemo.service.product.ProductService;
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
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
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
}
