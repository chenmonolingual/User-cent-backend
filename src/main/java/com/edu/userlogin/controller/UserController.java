package com.edu.userlogin.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.edu.userlogin.commos.ErrorCode;
import com.edu.userlogin.commos.Result;
import com.edu.userlogin.constant.userConstant;
import com.edu.userlogin.domain.User;
import com.edu.userlogin.domain.request.userLogin;
import com.edu.userlogin.domain.request.userRegister;
import com.edu.userlogin.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins ={"http://user-backend.code-nav.cn"} , allowCredentials = "true")
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 用户登录
     * @param userLogin 前端传过来的数据
     * @return 返回用户信息
     */
    @PostMapping("/login")
    @CrossOrigin("http://user-backend.code-nav.cn")
    public Result<User> userLogin(@RequestBody userLogin userLogin, HttpServletRequest request){

        if (userLogin == null){
            return Result.error(ErrorCode.NULL_CODE,"请求参数为空");
        }
        String account = userLogin.getUserAccount();
        String password = userLogin.getUserPassword();
        return userService.userLogin(account, password, request);
    }

    /**
     * 用户注册功能
     * @param userRegister 前端传过来的数据
     * @return 注册成功返回对应的ID
     */
    @PostMapping("/register")
    public Result<Long> userRegister(@RequestBody userRegister userRegister, HttpServletRequest request){
        //如果用户处于登录状态并且不是管理员用户的话
        Object attribute = request.getSession().getAttribute(userConstant.USER_LOGIN_STATUS);
        if (attribute != null && !isAdmin(request)){
            return Result.error(ErrorCode.NO_ADMIN_CODE,"你处于登录状态");
        }
        if (userRegister == null){
            return Result.error(ErrorCode.NULL_CODE,"请求参数为空");
        }
        String userName = userRegister.getUsername();
        String account = userRegister.getUserAccount();
        String password = userRegister.getUserPassword();
        String checkCode = userRegister.getCheckPassword();
        String planetCode = userRegister.getPlanetCode();
        return userService.userRegister(account, password, checkCode,userName,planetCode);
    }

    /**
     * 用户的删除功能只能是管理员才能删除
     * @param id 需要删除的id
     * @return 删除成功true否则false
     */
    @DeleteMapping("/{id}")
    public Result<Boolean> userDelete(@PathVariable("id")Long id,HttpServletRequest request){
        //管理员的判断
        if (!isAdmin(request)){
            return Result.error(ErrorCode.NO_ADMIN_CODE,"你不是管理员");
        }
        if (id == null || id < 0){
            return null;
        }
        return Result.success(userService.removeById(id));
    }

    /**
     * 查询操作
     * @param username 条件查询
     * @return 返回结果集
     */
    @GetMapping("/search")
    public Result<List<User>> searchUsers(String username, HttpServletRequest request){
        //判断用户是否是管理员
        if (!isAdmin(request)){
            return Result.error(ErrorCode.NO_ADMIN_CODE,"你不是管理员");
        }

        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        //条件
        lambdaQueryWrapper.like(StringUtils.isNotBlank(username),User::getUserName,username);

        List<User> list = userService.list(lambdaQueryWrapper);
        //进行解敏
        list = list.stream().map((item) -> userService.dissolve(item)).collect(Collectors.toList());
        return Result.success(list);
    }

    /**
     * 用户退出
     * @param request session
     * @return 返回ture获取false
     */
    @PostMapping("/logout")
    public Result<Boolean> quitUser(HttpServletRequest request){
        request.getSession().removeAttribute(userConstant.USER_LOGIN_STATUS);
        return Result.success(true);
    }

    //获取当前用户
    @GetMapping("/current")
    public Result<User> current(HttpServletRequest request){
        User attribute = (User)request.getSession().getAttribute(userConstant.USER_LOGIN_STATUS);
        if (attribute == null){
            return Result.error(ErrorCode.NO_LOGIN);
        }
        return Result.success(userService.getById(attribute.getId()));
    }

    //判断是否是管理员
    public boolean isAdmin(HttpServletRequest request){
        Object attribute = request.getSession().getAttribute(userConstant.USER_LOGIN_STATUS);
        User user = (User) attribute;
        return user != null && user.getIsAdmin() != 0;
    }
}
