package com.Training.BankingApp.user;

import lombok.Data;

@Data
public class JWTAuthenticationResponse {
    private String token;
    private long userId;
    private String username;
    private String email;
    private long phoneNumber;
    private int roleId;
    private boolean loggedIn;
//    private String refreshToken;
}
