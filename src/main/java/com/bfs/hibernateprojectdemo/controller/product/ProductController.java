package com.bfs.hibernateprojectdemo.controller.product;

import com.bfs.hibernateprojectdemo.dto.base.BaseSuccessResponse;
import com.bfs.hibernateprojectdemo.dto.common.StatusResponse;
import com.bfs.hibernateprojectdemo.dto.product.*;
import com.bfs.hibernateprojectdemo.dto.stats.ProductFrequencyDTO;
import com.bfs.hibernateprojectdemo.dto.stats.ProductFrequencyResponse;
import com.bfs.hibernateprojectdemo.dto.stats.ProductRecentDto;
import com.bfs.hibernateprojectdemo.dto.stats.ProductRecentResponse;
import com.bfs.hibernateprojectdemo.exception.ResourceNotFoundException;
import com.bfs.hibernateprojectdemo.service.product.ProductService;
import com.bfs.hibernateprojectdemo.service.user.UserService;
import com.bfs.hibernateprojectdemo.utils.AuthUserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    @PreAuthorize("hasAuthority('USER') || hasAuthority('SELLER')")
    public ResponseEntity<?> getAllInStockProduct(){
        if(AuthUserUtils.isUser()) {
            List<UserProductDTO> products = productService.getAllProductForUser();
            InStockProductsResponse response = InStockProductsResponse.builder()
                    .products(products)
                    .build();
            return ResponseEntity.ok(response);
        }else if(AuthUserUtils.isSeller()){
            List<AdminProductDTO> products = productService.getAllProductForAdmin();
            AllProductResponse response = AllProductResponse.builder().products(products)
                    .build();
            return ResponseEntity.ok(response);
        }
        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('USER') || hasAuthority('SELLER')")
    public ResponseEntity<?> getProductById(@PathVariable("id") Long id)
            throws ResourceNotFoundException {
        if(AuthUserUtils.isUser()){
            UserProductDTO product = productService.getProductById(id);
            return ResponseEntity.ok(product);
        } else if(AuthUserUtils.isSeller()){
            AdminProductDTO product = productService.getAdminProductById(id);
            return ResponseEntity.ok(product);
        }else{
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
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

    @PostMapping
    @PreAuthorize("hasAuthority('SELLER')")
    public ResponseEntity<BaseSuccessResponse> addProduct(@Valid @RequestBody AddProductRequest request){
        productService.addProduct(request);
        return new ResponseEntity<>(BaseSuccessResponse.builder().message("success!").build(),
                HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('SELLER')")
    public ResponseEntity<BaseSuccessResponse> updateProduct(@Valid @RequestBody UpdateProductRequest request,
                                                             @PathVariable("id") Long productId){
        productService.updateProduct(productId, request);
        return new ResponseEntity<>(BaseSuccessResponse.builder().message("success!").build(),
                HttpStatus.OK);
    }
}
