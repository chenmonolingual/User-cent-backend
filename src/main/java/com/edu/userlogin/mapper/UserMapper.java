package com.edu.userlogin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.edu.userlogin.domain.User;
import org.apache.ibatis.annotations.Mapper;

/**
* @author chen
* @description 针对表【user】的数据库操作Mapper
* @createDate 2023-07-18 20:46:29
* @Entity com.edu.userlogin.domain.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

}




