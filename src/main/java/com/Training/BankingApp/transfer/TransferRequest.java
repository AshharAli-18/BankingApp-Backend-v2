package com.Training.BankingApp.transfer;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TransferRequest {

    private long fromAccountId;
//    private long toAccountId;
    private String toAccountNumber;
    private long amount;
    private String email;
    private String otp;

}
