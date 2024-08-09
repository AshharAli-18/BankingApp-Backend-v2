package com.Training.BankingApp.persmission;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity(name = "permissions")
public class Permission {

    @Id
    private int permissionId;
    private String permissionName;

}
