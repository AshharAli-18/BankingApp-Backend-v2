package com.Training.BankingApp.user;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class LoginRequest {

    //    private int userId;
    private String username;
    private String email;
    private String password;

}
