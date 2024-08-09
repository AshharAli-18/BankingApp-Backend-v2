package com.Training.BankingApp.deletedaccount;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DeletedAccountController {

    @Autowired
    private DeletedAccountService deletedAccountService;

    @GetMapping("/api/getAllDeletedAccounts")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<DeletedAccount> getAllDeletedAccounts(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                                      @RequestParam(name = "size", defaultValue = "10") Integer size) {
        return deletedAccountService.getAllDeletedAccounts(page, size);
    }

    @DeleteMapping("/api/admin/permanentAccountDelete/{id}")
    @CrossOrigin(origins = "http://localhost:3000")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> permanentDeleteAccount(@PathVariable Long id) {
        try {
            deletedAccountService.permanentlyDeleteAccount(id);
            return ResponseEntity.ok("Account successfully deleted");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }
}
