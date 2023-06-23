package com.bfs.hibernateprojectdemo.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;


@Data
@ToString
@EqualsAndHashCode
public class UserDTO {
    private Integer id;

    private String email;

    private String username;

    private String iconUrl;
}
