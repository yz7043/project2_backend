package com.bfs.hibernateprojectdemo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class HibernateProjectDemoApplicationTests {

    @Test
    void contextLoads() {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        System.out.println(passwordEncoder.encode("zyx"));
        System.out.println(passwordEncoder.encode("zyx1"));
        System.out.println(passwordEncoder.encode("zyx2"));
        System.out.println(passwordEncoder.encode("seller"));
    }

}
