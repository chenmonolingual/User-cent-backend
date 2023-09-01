package com.edu.userlogin.domain.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class userLogin implements Serializable {
    private static final long serialVersionUID = UUID.randomUUID().getLeastSignificantBits();
    private String userAccount;
    private String userPassword;
}
