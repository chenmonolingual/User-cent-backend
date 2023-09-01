package com.edu.userlogin.service;



import com.baomidou.mybatisplus.extension.service.IService;
import com.edu.userlogin.commos.Result;
import com.edu.userlogin.domain.User;

import javax.servlet.http.HttpServletRequest;

/**
* @author chen
* @description 针对表【user】的数据库操作Service
* @createDate 2023-07-18 20:46:29
*/
public interface UserService extends IService<User> {
    /**
     * 用户注册功能
     * @param account 用户名
     * @param password 密码
     * @param checkCode 校验码
     * @return 返回该用户的id
     */
    Result<Long> userRegister(String account, String password, String checkCode, String userName, String planetCode);

    /**
     * 用户登录功能
     * @param account 账户
     * @param password 密码
     * @param request 请求
     * @return 有则返回该用户的基本信息没有则报错
     */
    Result<User> userLogin(String account, String password, HttpServletRequest request);

    /**
     * 进行解敏操作
     * @param user 用户传过来的数据
     * @return 返回解敏过后的数据
     */
    User dissolve(User user);



}
