package com.bfs.hibernateprojectdemo.service.user;


import com.bfs.hibernateprojectdemo.dao.UserDao;
import com.bfs.hibernateprojectdemo.domain.DomainConst;
import com.bfs.hibernateprojectdemo.domain.Permission;
import com.bfs.hibernateprojectdemo.domain.User;
import com.bfs.hibernateprojectdemo.dto.register.RegisterRequest;
import com.bfs.hibernateprojectdemo.exception.UserExistedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {
    private final UserDao userDao;

    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public void registerUser(RegisterRequest request){
        String username = request.getUsername();
        String email = request.getEmail();
        String password = request.getPassword();
        String iconUrl = request.getIconUrl();
        Optional<User> userByUsername = userDao.loadUserByUsername(username);
        Optional<User> userByEmail = userDao.loadUserByEmail(email);
        if(userByUsername.isPresent())
            throw new UserExistedException("Username has been used!");
        if(userByEmail.isPresent())
            throw new UserExistedException("Email has been used!");
        User user = User.builder()
                .username(username)
                .password(password)
                .email(email)
                .iconUrl(iconUrl)
                .role(DomainConst.USER_ROLE)
                .build();
        Permission permission = Permission.builder()
                        .value(DomainConst.USER)
                        .build();
        permission.setUser(user);
        user.getPermissions().add(permission);
        userDao.createUser(user);
    }
}
