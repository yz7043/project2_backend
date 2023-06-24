package com.bfs.hibernateprojectdemo.service.user;


import com.bfs.hibernateprojectdemo.dao.ProductDao;
import com.bfs.hibernateprojectdemo.dao.UserDao;
import com.bfs.hibernateprojectdemo.domain.DomainConst;
import com.bfs.hibernateprojectdemo.domain.Permission;
import com.bfs.hibernateprojectdemo.domain.Product;
import com.bfs.hibernateprojectdemo.domain.User;
import com.bfs.hibernateprojectdemo.dto.register.RegisterRequest;
import com.bfs.hibernateprojectdemo.dto.watchlist.WatchListItem;
import com.bfs.hibernateprojectdemo.dto.watchlist.WatchListResponse;
import com.bfs.hibernateprojectdemo.exception.ResourceNotFoundException;
import com.bfs.hibernateprojectdemo.exception.UserExistedException;
import com.bfs.hibernateprojectdemo.security.AuthUserDetail;
import com.bfs.hibernateprojectdemo.service.watchlist.WatchListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserDao userDao;

    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    private ProductDao productDao;

    @Autowired
    public void setProductDao(ProductDao productDao){
        this.productDao = productDao;
    }

    private AuthUserDetail getLoginUser(){
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        AuthUserDetail loginUser = (AuthUserDetail) authentication.getPrincipal();
        return loginUser;
    }

    @Transactional
    public void registerUser(RegisterRequest request){
        String username = request.getUsername();
        String email = request.getEmail();
        String password = request.getPassword();
        String iconUrl = request.getIconUrl();
        Optional<User> userByUsername = userDao.loadUserByUsername(username);
        Optional<User> userByEmail = userDao.loadUserByEmail(email);
        if(userByUsername.isPresent())
            throw new UserExistedException("Username has been used!");
        if(userByEmail.isPresent())
            throw new UserExistedException("Email has been used!");
        User user = User.builder()
                .username(username)
                .password(password)
                .email(email)
                .iconUrl(iconUrl)
                .role(DomainConst.USER_ROLE)
                .build();
        Permission permission = Permission.builder()
                        .value(DomainConst.USER)
                        .build();
        permission.setUser(user);
        user.getPermissions().add(permission);
        userDao.createUser(user);
    }

    @Transactional
    public boolean addToWatchList(Long productId) throws ResourceNotFoundException{
        AuthUserDetail loginUser = getLoginUser();
        User user = userDao.loadUserByUsername(loginUser.getUsername()).get();
        for(Product product : user.getProducts()){
            if(productId.equals(product.getId())){
                return false;
            }
        }
        Product product = productDao.getProductById(productId);
        if(product == null || product.getQuantity() <= 0){
            throw new ResourceNotFoundException("No such product");
        }
        user.addProduct(product);
        return true;
    }

    @Transactional
    public List<WatchListItem> getAllWatchList() {
        AuthUserDetail loginUser = getLoginUser();
        User user = userDao.loadUserByUsername(loginUser.getUsername()).get();
        List<WatchListItem> watchListItems = new LinkedList<>();

        Iterator<Product> productIterator = user.getProducts().iterator();
        while (productIterator.hasNext()) {
            Product product = productIterator.next();
            if(product.getQuantity() <= 0){
                productIterator.remove();
                product.getUsers().remove(user);
                continue;
            }
            watchListItems.add(
                    WatchListItem.builder()
                            .id(product.getId())
                            .description(product.getDescription())
                            .name(product.getName())
                            .quantity(product.getQuantity())
                            .retailPrice(product.getRetailPrice())
                            .build()
            );
        }
        return watchListItems;
    }

    @Transactional
    public void deleteProductFromWatchList(Long productID) {
        AuthUserDetail loginUser = getLoginUser();
        User user = userDao.loadUserByUsername(loginUser.getUsername()).get();
        boolean foundProduct = false;
        Iterator<Product> productIterator = user.getProducts().iterator();
        while(productIterator.hasNext()){
            Product product = productIterator.next();
            if(product.getId().equals(productID)){
                foundProduct = true;
                productIterator.remove();
                product.getUsers().remove(user);
                break;
            }
        }
        if(!foundProduct)
            throw new ResourceNotFoundException("No such product in your watchlist!");
    }
}
