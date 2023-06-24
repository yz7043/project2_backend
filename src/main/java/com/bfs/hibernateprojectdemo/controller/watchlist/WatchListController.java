package com.bfs.hibernateprojectdemo.controller.watchlist;

import com.bfs.hibernateprojectdemo.dto.base.BaseSuccessResponse;
import com.bfs.hibernateprojectdemo.dto.common.StatusResponse;
import com.bfs.hibernateprojectdemo.dto.watchlist.WatchListItem;
import com.bfs.hibernateprojectdemo.dto.watchlist.WatchListResponse;
import com.bfs.hibernateprojectdemo.service.user.UserService;
import com.bfs.hibernateprojectdemo.service.watchlist.WatchListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("watchlist")
public class WatchListController {
    private WatchListService watchListService;
    @Autowired
    public void setWatchListService(WatchListService watchListService){
        this.watchListService = watchListService;
    }

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService){
        this.userService = userService;
    }

    @GetMapping("products/all")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<WatchListResponse> getAllWatchedProduct(){
        List<WatchListItem> allWatchList = userService.getAllWatchList();
        return ResponseEntity.ok(WatchListResponse.builder()
                .status(StatusResponse.builder().message("Watchlist found").success(true).build())
                .products(allWatchList)
                .build());
    }

    @PostMapping("product/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<BaseSuccessResponse> addProductToWatchList(@PathVariable("id") Long productId){
        boolean res = userService.addToWatchList(productId);
        if(res)
            return new ResponseEntity(BaseSuccessResponse.builder().message("Added!").build(),
                    HttpStatus.CREATED);
        else
            return new ResponseEntity(BaseSuccessResponse.builder().message("Product already in list!").build(),
                    HttpStatus.FOUND);
    }

    @DeleteMapping("product/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity deleteFromWatchList(@PathVariable("id") Long productId){
        userService.deleteProductFromWatchList(productId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
