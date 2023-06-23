package com.bfs.hibernateprojectdemo.dto.login;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode
@Builder
public class LoginUserDTO {
    private String message;
    private String token;
}
