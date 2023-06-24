package com.bfs.hibernateprojectdemo.service.product;

import com.bfs.hibernateprojectdemo.dao.ProductDao;
import com.bfs.hibernateprojectdemo.domain.Product;
import com.bfs.hibernateprojectdemo.dto.product.ProductUserDTO;
import com.bfs.hibernateprojectdemo.exception.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductDao productDao;

    @Autowired
    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    public List<ProductUserDTO> getAllProductForUser(){
        List<Product> products = productDao.getAllProduct();
        return products.stream().filter(product -> product.getQuantity() > 0)
                .map(product -> ProductUserDTO.builder()
                        .id(product.getId())
                        .description(product.getDescription())
                        .quantity(product.getQuantity())
                        .retailPrice(product.getRetailPrice())
                        .name(product.getName())
                        .build()).collect(Collectors.toList());
    }

    public ProductUserDTO getProductById(Long id) throws ProductNotFoundException {
        Product product = productDao.getProductById(id);
        if(product == null)
            throw new ProductNotFoundException("Cannot find product with id " + id);
        return ProductUserDTO.builder()
                .id(product.getId())
                .description(product.getDescription())
                .name(product.getName())
                .quantity(product.getQuantity())
                .retailPrice(product.getRetailPrice())
                .build();
    }
}
