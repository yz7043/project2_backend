package com.bfs.hibernateprojectdemo.controller.user;

import com.bfs.hibernateprojectdemo.dao.UserDao;
import com.bfs.hibernateprojectdemo.dto.login.LoginRequest;
import com.bfs.hibernateprojectdemo.dto.login.LoginUserDTO;
import com.bfs.hibernateprojectdemo.security.AuthUserDetail;
import com.bfs.hibernateprojectdemo.security.JwtProvider;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class LoginController {
    private AuthenticationManager authenticationManager;

    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    private JwtProvider jwtProvider;

    @Autowired
    public void setJwtProvider(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }
    @PostMapping("login")
    public ResponseEntity<LoginUserDTO> login(@RequestBody LoginRequest request){
        // TODO: move the logic to Service
        Authentication authentication;

        //Try to authenticate the user using the username and password
        try{
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
        } catch (AuthenticationException e){
            throw new BadCredentialsException("Provided credential is invalid.");
        }

        //Successfully authenticated user will be stored in the authUserDetail object
        AuthUserDetail authUserDetail = (AuthUserDetail) authentication.getPrincipal(); //getPrincipal() returns the user object

        //A token wil be created using the username/email/userId and permission
        String token = jwtProvider.createToken(authUserDetail);

        //Returns the token as a response to the frontend/postman
        LoginUserDTO loggedUser = LoginUserDTO.builder()
                .message("Welcome " + authUserDetail.getUsername())
                .token(token)
                .build();
        return ResponseEntity.ok(loggedUser);
    }

    @GetMapping("test")
    public ResponseEntity<String> test(){
        return ResponseEntity.ok("ok");
    }
}
