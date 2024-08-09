package com.Training.BankingApp.transaction;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
@Entity(name = "transactions")
public class Transaction {

    @Id
    private long transactionId;
    private long accountId;
    private String toAccountNumber;
    private long amount;
    private LocalDate date;
    private String creditDebit;
    private long transferId;

}
