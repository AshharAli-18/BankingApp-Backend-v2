package com.Training.BankingApp.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequest {

//    private int userId;
    private String username;
    private String password;
    private String email;
    private Long phoneNumber;
    private String accountType;


}
