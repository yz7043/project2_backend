package com.bfs.hibernateprojectdemo.dao;

import com.bfs.hibernateprojectdemo.domain.User;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


public class UserDao extends AbstractHibernateDao<User>{

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
