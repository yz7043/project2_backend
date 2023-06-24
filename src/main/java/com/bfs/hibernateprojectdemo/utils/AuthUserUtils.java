package com.bfs.hibernateprojectdemo.utils;

import com.bfs.hibernateprojectdemo.security.AuthUserDetail;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

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
}
