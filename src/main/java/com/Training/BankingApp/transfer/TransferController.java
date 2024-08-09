package com.Training.BankingApp.transfer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@RestController
public class TransferController {

    @Autowired
    private TransferService transferService;

    @PostMapping("/api/transferMoney")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<String> transferMoney(@RequestBody TransferRequest transferRequest) {
        try {
            transferService.transfer(
                    transferRequest.getFromAccountId(),
                    transferRequest.getToAccountNumber(),
                    transferRequest.getAmount(),
                    transferRequest.getOtp(),
                    transferRequest.getEmail()
            );
            return ResponseEntity.ok("Money transferred successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/api/getAllTransfers")
    @CrossOrigin(origins = "http://localhost:3000")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<Transfer> getAllTransfers(
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size
    ) {
        return transferService.getAllTransfers(page, size);
    }
}
