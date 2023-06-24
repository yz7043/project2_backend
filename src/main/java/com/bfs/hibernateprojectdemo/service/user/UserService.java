package com.bfs.hibernateprojectdemo.service.user;


import com.bfs.hibernateprojectdemo.dao.ProductDao;
import com.bfs.hibernateprojectdemo.dao.UserDao;
import com.bfs.hibernateprojectdemo.domain.*;
import com.bfs.hibernateprojectdemo.dto.register.RegisterRequest;
import com.bfs.hibernateprojectdemo.dto.stats.ProductFrequencyDTO;
import com.bfs.hibernateprojectdemo.dto.stats.ProductFrequencyResponse;
import com.bfs.hibernateprojectdemo.dto.stats.ProductRecentDto;
import com.bfs.hibernateprojectdemo.dto.watchlist.WatchListItem;
import com.bfs.hibernateprojectdemo.dto.watchlist.WatchListResponse;
import com.bfs.hibernateprojectdemo.exception.ResourceNotFoundException;
import com.bfs.hibernateprojectdemo.exception.UserExistedException;
import com.bfs.hibernateprojectdemo.security.AuthUserDetail;
import com.bfs.hibernateprojectdemo.service.watchlist.WatchListService;
import com.bfs.hibernateprojectdemo.utils.AuthUserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import java.time.LocalDateTime;
import java.util.*;

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

    @Transactional
    public List<ProductFrequencyDTO> getFrequentProducts(Integer limit){
        AuthUserDetail loginUser = AuthUserUtils.getLoginUser();
        User user = userDao.loadUserByUsername(loginUser.getUsername()).get();
        HashMap<Long, ProductFrequencyDTO> productCounter = new HashMap<>(); // productId : freq
        for(Order order : user.getOrders()){
            if(order.getStatus().equals(DomainConst.ORDER_CANCELLED))
                continue;
            for(OrderItem orderItem : order.getOrderItems()){
                Product product = orderItem.getProduct();
                long productId = product.getId();
                ProductFrequencyDTO productFrequencyDto = productCounter.get(productId);
                if(productFrequencyDto == null){
                    productFrequencyDto = ProductFrequencyDTO
                            .builder()
                            .productId(productId)
                            .name(product.getName())
                            .description(product.getDescription())
                            .frequency(0)
                            .build();
                    productCounter.put(productId, productFrequencyDto);
                }
                productFrequencyDto.setFrequency(productFrequencyDto.getFrequency() + 1);
            }
        }
        List<ProductFrequencyDTO> result = new LinkedList<>(productCounter.values());
        Collections.sort(result,
                (p1, p2) -> p2.getFrequency().compareTo(p1.getFrequency()));
        if(result.isEmpty())
            return result;
        return result.subList(0, Math.min(limit, result.size()));
    }

    @Transactional
    public List<ProductRecentDto> getRecentProducts(Integer limit){
        AuthUserDetail loginUser = AuthUserUtils.getLoginUser();
        User user = userDao.loadUserByUsername(loginUser.getUsername()).get();
        HashMap<Long, ProductRecentDto> productDic = new HashMap<>();
        for(Order order : user.getOrders()){
            if(order.getStatus().equals(DomainConst.ORDER_CANCELLED))
                continue;
            for(OrderItem orderItem : order.getOrderItems()){
                Product product = orderItem.getProduct();
                long productId = product.getId();
                LocalDateTime date = order.getDatePlaced();
                ProductRecentDto productRecentDto = productDic.get(productId);
                if(productRecentDto == null){
                    productRecentDto = ProductRecentDto
                            .builder()
                            .productId(productId)
                            .name(product.getName())
                            .description(product.getDescription())
                            .datePurchased(date)
                            .build();
                    productDic.put(productId, productRecentDto);
                }
                if(productRecentDto.getDatePurchased().isBefore(date))
                    productRecentDto.setDatePurchased(date);
            }
        }
        List<ProductRecentDto> result = new ArrayList<>(productDic.values());
        result.sort((o1, o2) -> o2.getDatePurchased().compareTo(o1.getDatePurchased()));
        return result.subList(0, Math.min(limit, result.size()));
    }
}
