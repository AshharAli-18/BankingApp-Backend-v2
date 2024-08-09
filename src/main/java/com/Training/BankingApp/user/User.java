package com.Training.BankingApp.user;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Entity(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;
    private String name;
    private String address;
    private String cnic;
    private String username;
    private String password;
    private String email;
    private long phoneNumber;
    private int roleId;

    // Default constructor
    public User() {
    }

    // Copy constructor
    public User(User user) {
        this.userId = user.userId;
        this.name = user.name;
        this.address = user.address;
        this.cnic = user.cnic;
        this.username = user.username;
        this.password = user.password;
        this.email = user.email;
        this.phoneNumber = user.phoneNumber;
        this.roleId = user.roleId;
    }

    @Override
    public String toString() {
        return "User{"
                + "id=" + userId
                + ", email='" + email + '\''
                + ", roleId=" + roleId
                + '}';
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String roleName = getRoleNameFromRoleId(roleId); // Implement this method to map roleId to a role name
        return List.of(new SimpleGrantedAuthority(roleName));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    private String getRoleNameFromRoleId(int roleId) {
        // Example mapping, adjust as needed
        switch (roleId) {
            case 1:
                return "ROLE_ADMIN";
            case 2:
                return "ROLE_CUSTOMER";
            default:
                throw new IllegalArgumentException("Invalid roleId: " + roleId);
        }
    }
}
