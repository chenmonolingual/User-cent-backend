package com.edu.userlogin.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edu.userlogin.commos.ErrorCode;
import com.edu.userlogin.commos.Result;
import com.edu.userlogin.constant.userConstant;
import com.edu.userlogin.domain.User;
import com.edu.userlogin.service.UserService;
import com.edu.userlogin.mapper.UserMapper;
import com.edu.userlogin.utlis.encryption;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
* @author chen
* @description 针对表【user】的数据库操作Service实现
* @createDate 2023-07-18 20:46:29
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,User>
        implements UserService{
    @Override
    public Result<Long> userRegister(String account, String password, String checkCode, String userName, String planetCode) {

        if ("0".equals(planetCode) || planetCode.length() >= 5) {
            return Result.error(ErrorCode.PARAMETER_CODE, "星球编号长度大于5");
        }
        //1.判断用户的账号,密码，校验码是否未空
        if (StringUtils.isAnyBlank(account,password,checkCode,userName)) {
            return Result.error(ErrorCode.PARAMETER_CODE, "账号或者密码为null");
        }
        //2.判断用户名长度是否大于4
        if (account.length() < 4){
            return Result.error(ErrorCode.PARAMETER_CODE, "用户名长度小于4");
        }
        //3.判断用户名的密码是否小于8
        if (password.length() < 8){
            return Result.error(ErrorCode.PARAMETER_CODE, "密码长度小于8");
        }
        //如果密码和校验码不同则返回-1
        if (!checkCode.equals(password)){
            return Result.error(ErrorCode.PARAMETER_CODE,"两次密码不一致");
        }
        //2.账户不含特殊字符
        String regex = "[!@#$%^&*(),.?\":{}|<>]";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(account);
        if (matcher.find()){
            return Result.error(ErrorCode.PARAMETER_CODE, "用户名含有特殊字符");
        }
        User user = new User();
        user.setUserAccount(account);
        //先判断是否存在
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getUserAccount,account);
        long count = this.count(lambdaQueryWrapper);
        if (count > 0) {
            return Result.error(ErrorCode.PARAMETER_CODE, "用户名已存在");
        }
        //判断是星球账户是否存在
        lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getPlanetCode,planetCode);
        count = this.count(lambdaQueryWrapper);
        if (count > 0) {
            return Result.error(ErrorCode.PARAMETER_CODE, "星球账户已存在");
        }

        //3.密码进行加密
        String passwordEncryption = encryption.passwordEncryption(password);
        user.setUserPassword(passwordEncryption);
        //用户名的设置
        user.setUserName(userName);
        user.setPlanetCode(planetCode);
        //数据库插入
        boolean save = this.save(user);
        //如果插入成功返回该id
        return save ? Result.success(user.getId()) : Result.error(ErrorCode.SERVER_CODE, "插入数据失败");
    }

    @Override
    public Result<User> userLogin(String account, String password, HttpServletRequest request) {
        //1.判断用户的账号,密码，校验码是否未空
        if (StringUtils.isAnyBlank(account,password)) {
            return Result.error(ErrorCode.NULL_CODE, "账号密码为空");
        }
        //2.判断用户名长度是否大于4
        if (account.length() < 4){
            return Result.error(ErrorCode.PARAMETER_CODE, "用户名长度小于4");
        }
        //3.判断用户名的密码是否小于8
        if (password.length() < 8){
             return Result.error(ErrorCode.PARAMETER_CODE, "密码长度小于8");
        }
        //2.账户不含特殊字符
        String regex = "[!@#$%^&*(),.?\":{}|<> a-zA-z]";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(account);
        if (matcher.find()){
            return Result.error(ErrorCode.PARAMETER_CODE,"账号含有特殊字符");
        }

        //3.密码进行加密
        String passwordEncryption = encryption.passwordEncryption(password);
        //先判断是否存在,并且账号处于正常状态
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getUserAccount,account)
                .eq(User::getUserStatus,0)
                .eq(User::getUserPassword,passwordEncryption);
        User one = this.getOne(lambdaQueryWrapper);
        if (one == null) {
            return Result.error(ErrorCode.PARAMETER_CODE,"账户不存在,请先注册账户");
        }
        User dissolve = dissolve(one);

        request.getSession().setAttribute(userConstant.USER_LOGIN_STATUS,dissolve);
        return Result.success(dissolve);
    }



    public User dissolve(User user){
        if (user == null){
            return null;
        }
        User safeUser = new User();
        safeUser.setId(user.getId());
        safeUser.setUserName(user.getUserName());
        safeUser.setUserAccount(user.getUserAccount());
        safeUser.setAvatarUrl(user.getAvatarUrl());
        safeUser.setSex(user.getSex());
        safeUser.setPhone(user.getPhone());
        safeUser.setEmail(user.getEmail());
        safeUser.setUserStatus(user.getUserStatus());
        safeUser.setCreateTime(user.getCreateTime());
        safeUser.setIsAdmin(user.getIsAdmin());
        safeUser.setPlanetCode(user.getPlanetCode());
        return safeUser;
    }
}




