package com.bfs.hibernateprojectdemo.dao;

import com.bfs.hibernateprojectdemo.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
public class UserDaoTest {
    @Autowired
    private UserDao userDao;


    @Test
    public void loadUserByUsernameTest(){
        Optional<User> user = userDao.loadUserByUsername("zyx");
        System.out.println(user.get());
    }
}
