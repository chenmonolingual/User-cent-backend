package com.edu.userlogin.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceImplTest {

    @Autowired
    private UserServiceImpl userService;

//    @Test
//    void testLogin(){
//        String username = "chen";
//        String password = "12345678";
//        String check = "12345678";
//        long l = userService.userRegister(username, password, check,null);
//        System.out.println(l);
//    }
}