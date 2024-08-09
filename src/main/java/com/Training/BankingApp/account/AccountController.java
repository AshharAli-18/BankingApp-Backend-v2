package com.Training.BankingApp.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@RestController
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/v2/account/{accountId}")
    @PreAuthorize("hasRole('ROLE_CUSTOMER') or hasRole('ROLE_ADMIN')")
    public Account getAccount(@PathVariable("accountId") long accountId) {
        return accountService.getAccount(accountId);
    }

    @GetMapping("/api/user/{userId}/account")
    @PreAuthorize("hasRole('ROLE_CUSTOMER') or hasRole('ROLE_ADMIN')")
    public Account getAccountByUserId(@PathVariable("userId") long userId) {
        return accountService.getAccountByUserId(userId);
    }

//    @GetMapping("/api/auth/getAllAccounts")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/api/account")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<Account> getAllAccounts(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                        @RequestParam(name = "size", defaultValue = "10") Integer size) {
        return accountService.getAllAccounts(page, size);
    }

    @PutMapping("/api/account/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void updateAccount(@PathVariable Long id, @RequestBody AccountUpdateRequest accountUpdateRequest) {
        accountUpdateRequest.setAccountId(id);  // Set the ID from the path variable
        accountService.updateAccount(accountUpdateRequest);
    }

    @DeleteMapping("/api/account/{id}")
    @CrossOrigin(origins = "http://localhost:3000")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteAccount(@PathVariable Long id) {
        accountService.deleteAccount(id);
    }

    @PostMapping("/api/account")
    @PreAuthorize("hasRole('ROLE_CUSTOMER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> registration(@RequestBody AccountCreateRequest accountCreateRequest) {
        try {

            accountService.createAccount(accountCreateRequest);
            return ResponseEntity.ok("Account Created Successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
