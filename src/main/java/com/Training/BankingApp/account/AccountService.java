package com.Training.BankingApp.account;

import com.Training.BankingApp.deletedaccount.DeletedAccountRepository;
import com.Training.BankingApp.transaction.TransactionRepository;
import com.Training.BankingApp.transfer.TransferRepository;
import com.Training.BankingApp.user.User;
import com.Training.BankingApp.deletedaccount.DeletedAccount;
import com.Training.BankingApp.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.List;
import java.util.HashSet;
import java.util.Set;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TransferRepository transferRepository;

    @Autowired
    private DeletedAccountRepository deletedAccountRepository;

    private static final String PREFIX = "MB";
    private static final int NUMBER_LENGTH = 8;
    private static final int MAX_PAGE_SIZE = 1000;
    private static final int RANDOM_DIGIT_UPPER_BOUND = 10;
    private final SecureRandom random = new SecureRandom();
    private final Set<String> generatedAccountNumbers = new HashSet<>();

    @Autowired
    private PasswordEncoder passwordEncoder;



    public String generateAccountNumber() {
        String accountNumber;
        do {
            accountNumber = PREFIX + generateUniqueNumber();
        } while (generatedAccountNumbers.contains(accountNumber));
        generatedAccountNumbers.add(accountNumber);
        return accountNumber;
    }

    private String generateUniqueNumber() {
        StringBuilder sb = new StringBuilder(NUMBER_LENGTH);
        for (int i = 0; i < NUMBER_LENGTH; i++) {
            sb.append(random.nextInt(RANDOM_DIGIT_UPPER_BOUND)); // Generates a random digit from 0 to 9
        }
        return sb.toString();
    }

    public Account getAccount(long accountId) {
        return accountRepository.findById(accountId)
                .orElse(null);
    }

    public Account getAccountByUserId(long userId) {
        return accountRepository.findByUserId(userId);
    }

    public List<Account> getAllAccounts(Integer page, Integer size) {
        if (page < 0) {
            page = 0;
        }
        if (size > MAX_PAGE_SIZE) {
            size = MAX_PAGE_SIZE;
        }
        return accountRepository.findAll(PageRequest.of(page, size)).getContent();
    }

    public void updateAccount(AccountUpdateRequest updateRequest) {
        Account account = accountRepository.findById(updateRequest.getAccountId())
                .orElseThrow(() -> new RuntimeException("Account not found"));

        // Update account details
        account.setAccountType(updateRequest.getAccountType());
        account.setBalance(updateRequest.getBalance());
        accountRepository.save(account); // Save updated account

        // Update user details
        User user = userRepository.findById(account.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setUsername(updateRequest.getUsername());
        user.setEmail(updateRequest.getEmail());
        user.setPhoneNumber(updateRequest.getPhone());
        user.setCnic(updateRequest.getCnic());
        user.setName(updateRequest.getName());
        userRepository.save(user); // Save updated user
    }

    public void deleteAccount(long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        DeletedAccount deletedAccount = new DeletedAccount();
        deletedAccount.setDeletedaccountId(account.getAccountId());
        deletedAccount.setAccountNumber(account.getAccountNumber());
        deletedAccount.setUser(account.getUser());
        deletedAccount.setAccountType(account.getAccountType());
        deletedAccount.setBalance(account.getBalance());
        deletedAccount.setOpeningDate(account.getOpeningDate());
        deletedAccount.setUserId(account.getUserId());
        deletedAccountRepository.save(deletedAccount);

        accountRepository.delete(account);
    }

    public void createAccount(AccountCreateRequest accountCreateRequest) {
        if (userRepository.existsByUsername(accountCreateRequest.getUsername())) {
            throw new RuntimeException("User already registered!");
        }
        if (userRepository.existsByEmail(accountCreateRequest.getEmail())) {
            throw new RuntimeException("User already registered!");
        }

        User user = new User();
        user.setUsername(accountCreateRequest.getUsername());
//        user.setPassword(passwordEncoder.encode(accountCreateRequest.getPassword()));
        user.setPassword(accountCreateRequest.getPassword());
        user.setEmail(accountCreateRequest.getEmail());
        user.setPhoneNumber(accountCreateRequest.getPhoneNumber());
        user.setRoleId(2);
        user.setName(accountCreateRequest.getName());
        user.setAddress(accountCreateRequest.getAddress());
        user.setCnic(accountCreateRequest.getCnic());

        userRepository.save(user);

        Account account = new Account();
        account.setUserId(user.getUserId());
        account.setBalance(0);
        account.setOpeningDate(LocalDate.now());
        account.setAccountType(accountCreateRequest.getAccountType());
        account.setAccountNumber(generateAccountNumber());

        accountRepository.save(account);
    }
}
