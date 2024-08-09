package com.Training.BankingApp.deletedaccount;

import com.Training.BankingApp.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity(name = "deletedaccounts")
public class DeletedAccount {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long deletedaccountId;

    @ManyToOne
    @JoinColumn(name = "userId", insertable = false, updatable = false)
    private User user;
    private long userId;
    private String accountNumber;
    private String accountType;
    private long balance;
    private LocalDate openingDate;

    public User getUser() {
        return user == null ? null : new User(this.user); // Return a copy of the user if not null
    }

    public void setUser(User user) {
        this.user = user == null ? null : new User(user); // Store a copy of the user if not null
    }
}
