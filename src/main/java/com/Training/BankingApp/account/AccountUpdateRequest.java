package com.Training.BankingApp.account;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AccountUpdateRequest {
    private long accountId;
    private String accountType;
    private long balance;
    private String username;
    private String email;
    private long phone;
    private String name;
    private String address;
    private String cnic;
}
