package com.edu.userlogin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.edu.userlogin.mapper")
public class UserLoginApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserLoginApplication.class, args);
    }

}
