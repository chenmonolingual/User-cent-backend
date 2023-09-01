package com.edu.userlogin.domain.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class userRegister extends userLogin implements Serializable {

    private static final long serialVersionUID = UUID.randomUUID().getLeastSignificantBits();

    private String username;

    private String checkPassword;

    private String planetCode;
}
