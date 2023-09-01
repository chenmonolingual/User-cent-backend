package com.edu.userlogin.utlis;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * 工具类
 */
public class encryption {

    private static final String PREFIX = "chen";
    /**
     * 密码加密
     * @param password 传过来的数据
     * @return 返回后的结果
     */
    public static String passwordEncryption(String password){
        String newPassword = PREFIX + password;
        //进行sha-256加密
        return DigestUtils.sha256Hex(newPassword.getBytes());
    }
}
