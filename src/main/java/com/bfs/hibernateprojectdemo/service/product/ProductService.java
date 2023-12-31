package com.bfs.hibernateprojectdemo.service.product;

import com.bfs.hibernateprojectdemo.dao.ProductDao;
import com.bfs.hibernateprojectdemo.dao.UserDao;
import com.bfs.hibernateprojectdemo.domain.Product;
import com.bfs.hibernateprojectdemo.dto.product.AddProductRequest;
import com.bfs.hibernateprojectdemo.dto.product.AdminProductDTO;
import com.bfs.hibernateprojectdemo.dto.product.UpdateProductRequest;
import com.bfs.hibernateprojectdemo.dto.product.UserProductDTO;
import com.bfs.hibernateprojectdemo.dto.stats.ProductFrequencyDTO;
import com.bfs.hibernateprojectdemo.exception.ResourceNotFoundException;
import com.bfs.hibernateprojectdemo.security.AuthUserDetail;
import com.bfs.hibernateprojectdemo.utils.AuthUserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductDao productDao;

    @Autowired
    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Transactional
    public List<UserProductDTO> getAllProductForUser(){
        List<Product> products = productDao.getAllProduct();
        return products.stream().filter(product -> product.getQuantity() > 0)
                .map(product -> UserProductDTO.builder()
                        .id(product.getId())
                        .description(product.getDescription())
//                        .quantity(product.getQuantity())
                        .retailPrice(product.getRetailPrice())
                        .name(product.getName())
                        .build()).collect(Collectors.toList());
    }

    @Transactional
    public List<AdminProductDTO> getAllProductForAdmin(){
        List<Product> products = productDao.getAllProduct();
        return products.stream()
                .map(product -> AdminProductDTO.builder()
                    .id(product.getId())
                    .description(product.getDescription())
                    .quantity(product.getQuantity())
                    .retailPrice(product.getRetailPrice())
                    .wholesalePrice(product.getWholesalePrice())
                    .name(product.getName())
                    .build())
                .collect(Collectors.toList());
    }

    @Transactional
    public UserProductDTO getProductById(Long id) throws ResourceNotFoundException {
        Product product = productDao.getProductById(id);
        if(product == null)
            throw new ResourceNotFoundException("Cannot find product with id " + id);
        return UserProductDTO.builder()
                .id(product.getId())
                .description(product.getDescription())
                .name(product.getName())
//                .quantity(product.getQuantity())
                .retailPrice(product.getRetailPrice())
                .build();
    }

    @Transactional
    public AdminProductDTO getAdminProductById(Long id) throws ResourceNotFoundException{
        Product product = productDao.getProductById(id);
        if(product == null)
            throw new ResourceNotFoundException("Cannot find product with id " + id);
        return AdminProductDTO.builder()
                .id(product.getId())
                .description(product.getDescription())
                .name(product.getName())
                .quantity(product.getQuantity())
                .retailPrice(product.getRetailPrice())
                .wholesalePrice(product.getWholesalePrice())
                .build();
    }

    @Transactional
    public void addProduct(AddProductRequest request){
        Product product = Product.builder()
                .description(request.getDescription())
                .name(request.getName())
                .wholesalePrice(request.getWholesalePrice())
                .retailPrice(request.getRetailPrice())
                .quantity(request.getQuantity())
                .build();
        productDao.add(product);
    }

    @Transactional
    public void updateProduct(Long productId, UpdateProductRequest request){
        Product product = productDao.getProductById(productId);
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setWholesalePrice(request.getWholesalePrice());
        product.setRetailPrice(request.getRetailPrice());
        product.setQuantity(request.getQuantity());
    }
}
