
package com.Training.BankingApp.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import java.util.NoSuchElementException;

@RestController
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/v2/account/{accountId}")
    @PreAuthorize("hasRole('ROLE_CUSTOMER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Account> getAccount(@PathVariable("accountId") long accountId) {
        return accountService.getAccount(accountId);
    }

    @GetMapping("/v2/user/{userId}/account")
    @PreAuthorize("hasRole('ROLE_CUSTOMER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Account> getAccountByUserId(@PathVariable("userId") long userId) {
        return accountService.getAccountByUserId(userId);
    }


    @GetMapping("/v2/accounts")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<Account>> getAllAccounts(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                                        @RequestParam(name = "size", defaultValue = "10") Integer size) {
        return accountService.getAllAccounts(page, size);
    }

    @PutMapping("/v2/account/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> updateAccount(@PathVariable Long id, @RequestBody AccountUpdateRequest accountUpdateRequest) {

        try {
            accountUpdateRequest.setAccountId(id);  // Set the ID from the path variable
            accountService.updateAccount(accountUpdateRequest);
            return ResponseEntity.ok("Account updated successfully"); // 200 OK
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage()); // 404 Not Found
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred"); // 500 Internal Server Error
        }
    }


    @DeleteMapping("/v2/account/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteAccount(@PathVariable Long id) {
        accountService.deleteAccount(id);
    }

    @PostMapping("/v2/account")
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
