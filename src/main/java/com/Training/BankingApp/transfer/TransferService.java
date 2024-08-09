package com.Training.BankingApp.transfer;

import com.Training.BankingApp.account.Account;
import com.Training.BankingApp.account.AccountRepository;
import com.Training.BankingApp.otp.OtpService;
import com.Training.BankingApp.transaction.Transaction;
import com.Training.BankingApp.transaction.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TransferService {

    private static final int MAX_PAGE_SIZE = 1000;

    @Autowired
    private TransferRepository transferRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private OtpService otpService;

    @Transactional
    public void transfer(Long fromAccountId, String toAccountNumber, long amount, String otp, String email) {
        // Validate OTP
        if (!otpService.validateOtp(email, otp)) {
            throw new RuntimeException("Invalid or expired OTP");
        }

        Account fromAccount = accountRepository.findById(fromAccountId)
                .orElseThrow(() -> new RuntimeException("From account not found"));
        Account toAccount = accountRepository.findByAccountNumber(toAccountNumber);
        if (toAccount == null) {
            throw new RuntimeException("To account not found");
        }

        if (fromAccount.getBalance() < amount) {
            throw new RuntimeException("Insufficient balance");
        }
        fromAccount.setBalance(fromAccount.getBalance() - amount);
        toAccount.setBalance(toAccount.getBalance() + amount);

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);

        Transfer transfer = new Transfer();
        transfer.setFromAccountId(fromAccountId);
        transfer.setToAccountNumber(toAccountNumber);
        transfer.setAmount(amount);
        transfer.setToAccountId(toAccount.getAccountId());
        transfer.setDate(LocalDate.now());

        transferRepository.save(transfer);

        long transactionIdSender = System.currentTimeMillis();
        long transactionIdReceiver = transactionIdSender + 1; // Ensuring unique transaction IDs

        Transaction transactionSender = new Transaction();
        transactionSender.setTransactionId(transactionIdSender);
        transactionSender.setAccountId(fromAccountId);
        transactionSender.setToAccountNumber(toAccountNumber);
        transactionSender.setAmount(amount);
        transactionSender.setDate(LocalDate.now());
        transactionSender.setCreditDebit("DB");
        transactionSender.setTransferId(transfer.getTransferId());

        transactionRepository.save(transactionSender);

        Transaction transactionReceiver = new Transaction();
        transactionReceiver.setTransactionId(transactionIdReceiver);
        transactionReceiver.setAccountId(toAccount.getAccountId());
        transactionReceiver.setToAccountNumber(toAccount.getAccountNumber());
        transactionReceiver.setAmount(amount);
        transactionReceiver.setDate(LocalDate.now());
        transactionReceiver.setCreditDebit("CR");
        transactionReceiver.setTransferId(transfer.getTransferId());

        transactionRepository.save(transactionReceiver);
    }

    public List<Transfer> getAllTransfers(Integer page, Integer size) {
        if (page < 0) {
            page = 0;
        }
        if (size > MAX_PAGE_SIZE) {
            size = MAX_PAGE_SIZE;
        }
        return transferRepository.findAll(PageRequest.of(page, size)).getContent();
    }
}
