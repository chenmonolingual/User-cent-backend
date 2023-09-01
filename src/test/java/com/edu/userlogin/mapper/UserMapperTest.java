package com.edu.userlogin.mapper;
import java.util.Date;

import com.edu.userlogin.domain.User;
import com.edu.userlogin.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserMapperTest {

    @Autowired
    private UserService userMapper;

    @Test
    public void testInsert(){
        User user = new User();
        user.setUserName("chentest");
        user.setUserAccount("123");
        user.setAvatarUrl("https://www.baidu.com");
        user.setSex(0);
        user.setUserPassword("123456");
        user.setPhone("12345");
        user.setUpdateTime(new Date());
        user.setIsDelete(0);
        userMapper.save(user);
    }
}