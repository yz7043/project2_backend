package com.bfs.hibernateprojectdemo.dto.register;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequest{
    @NotBlank(message = "Email can't be empty")
    @Size(max = 255, message = "Email length can't exceeds {max}")
    private String email;

    @NotBlank(message = "Password can't be empty")
    @Size(max = 255, message = "Password length can't exceeds {max}")
    private String password;

    @NotBlank(message = "Username can't be empty")
    @Size(max = 255, message = "Username length can't exceeds {max}")
    private String username;

    private String iconUrl;
}
