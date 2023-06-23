package com.bfs.hibernateprojectdemo.controller.user;

import com.bfs.hibernateprojectdemo.dto.register.RegisterRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterController {
    @PostMapping("signup")
    public ResponseEntity<String> signup(@RequestBody RegisterRequest request){
        return ResponseEntity.ok("haha");
    }
}
