package com.Training.BankingApp.customerrequest;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CustomerRequestDTO {
    private Long id;
    private String accountType;
    private long balance;
    private String username;
    private String email;
    private long phoneNumber;
    private String name;
    private String password;
    private String address;
    private String cnic;
}
