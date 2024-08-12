
package com.Training.BankingApp.user;

import com.Training.BankingApp.util.EncryptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private EncryptionUtil encryptionUtil;
    String secretKey = "MySecretKey12345";

    @PostMapping("/v2/auth/customer-login")
    public ResponseEntity<?> loginCustomer(@RequestBody LoginRequest loginRequest) {
        try {
            ResponseEntity<?> response = userService.loginCustomer(loginRequest);
            return response;
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

//    @PostMapping("/v2/auth/customer-login")
//    public ResponseEntity<?> loginCustomer(@RequestBody LoginRequest loginRequest) {
//        try {
//            String secretKey = "1234567890123456";
//            String decryptedPassword = EncryptionUtil.decryptPassword(loginRequest.getPassword(), secretKey);
//            System.out.println("Dcrypted pass is :"+ decryptedPassword);
//            return ResponseEntity.ok().build();
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }



    @PostMapping("/v2/auth/admin-login")
    public ResponseEntity<?> loginAdmin(@RequestBody LoginRequest loginRequest) {
        try {
            ResponseEntity<?> response = userService.loginAdmin(loginRequest);
            return response;
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/v2/users")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<User> getUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/v2/user/{userId}")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public User getUserByUserId(@PathVariable("userId") long userId) {
        return userService.getUserByUserId(userId);
    }
}
