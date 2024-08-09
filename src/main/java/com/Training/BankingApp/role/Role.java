package com.Training.BankingApp.role;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity(name = "roles")
public class Role {

    @Id
    private int roleId;
    private String roleName;

}
