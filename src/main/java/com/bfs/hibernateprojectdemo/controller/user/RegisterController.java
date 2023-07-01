package com.bfs.hibernateprojectdemo.controller.user;

import com.bfs.hibernateprojectdemo.dto.base.BaseSuccessResponse;
import com.bfs.hibernateprojectdemo.dto.register.RegisterRequest;
import com.bfs.hibernateprojectdemo.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class RegisterController {
    private final UserService userService;

    @Autowired
    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("signup")
    public ResponseEntity<BaseSuccessResponse> signup(@Valid @RequestBody RegisterRequest request){
        userService.registerUser(request);
        return ResponseEntity.ok(BaseSuccessResponse.builder().message("Signed up successfully!").build());
    }
}
