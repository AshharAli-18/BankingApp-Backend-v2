package com.Training.BankingApp.deletedaccount;

import com.Training.BankingApp.transaction.Transaction;
import com.Training.BankingApp.transaction.TransactionRepository;
import com.Training.BankingApp.transfer.Transfer;
import com.Training.BankingApp.transfer.TransferRepository;
import com.Training.BankingApp.user.User;
import com.Training.BankingApp.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeletedAccountService {
    @Autowired
    private DeletedAccountRepository deletedAccountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TransferRepository transferRepository;

    @Autowired
    private UserRepository userRepository;
    private static final int NUMBER_LENGTH = 8;
    private static final int MAX_PAGE_SIZE = 1000;

    public List<DeletedAccount> getAllDeletedAccounts(Integer page, Integer size) {
        if (page < 0) {
            page = 0;
        }
        if (size > MAX_PAGE_SIZE) {
            size = MAX_PAGE_SIZE;
        }
        return deletedAccountRepository.findAll(PageRequest.of(page, size)).getContent();
    }


    public void permanentlyDeleteAccount(long deletedAccountId) {
        DeletedAccount account = deletedAccountRepository.findById(deletedAccountId)
                .orElseThrow(() -> new EntityNotFoundException("Account not found"));

        List<Transaction> transactions = transactionRepository.findByAccountId(account.getDeletedaccountId());
        List<Transfer> transfers = transferRepository.findByFromAccountId(account.getDeletedaccountId());

        User user = userRepository.findByUserId(account.getUserId());

        transferRepository.deleteAll(transfers);
        transactionRepository.deleteAll(transactions);
        deletedAccountRepository.delete(account);
        userRepository.delete(user);
    }

}
