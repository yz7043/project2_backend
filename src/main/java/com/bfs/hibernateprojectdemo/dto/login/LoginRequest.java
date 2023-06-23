package com.bfs.hibernateprojectdemo.dto.login;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginRequest {
    @NotBlank(message = "Username can't be empty")
    @Size(max = 255, message = "Username length can't exceeds {max}")
    private String username;

    @NotBlank(message = "Password can't be empty")
    @Size(max = 255, message = "Password length can't exceeds {max}")
    private String password;
}
