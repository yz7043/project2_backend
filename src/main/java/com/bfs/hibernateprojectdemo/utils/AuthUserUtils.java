package com.bfs.hibernateprojectdemo.utils;

import com.bfs.hibernateprojectdemo.domain.DomainConst;
import com.bfs.hibernateprojectdemo.security.AuthUserDetail;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.stream.Collectors;

public class AuthUserUtils {
    /*
    * Only for logged-in user with JWT token
    * */
    public static AuthUserDetail getLoginUser(){
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        AuthUserDetail loginUser = (AuthUserDetail) authentication.getPrincipal();
        return loginUser;
    }

    public static boolean isUser(){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return Arrays.stream(userDetails.getAuthorities().toArray()).filter(
                auth -> auth.toString().equals(DomainConst.USER)
        ).collect(Collectors.toList()).size() > 0;
    }

    public static boolean isSeller(){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return Arrays.stream(userDetails.getAuthorities().toArray()).filter(
                auth -> auth.toString().equals(DomainConst.SELLER)
        ).collect(Collectors.toList()).size() > 0;
    }
}
