package com.Training.BankingApp.rolepermission;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity(name = "rolepermissions")
public class RolePermission {

    @Id
    private int roleId;
    private int permissionId;

}
