package com.Training.BankingApp.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    private static final int MAX_PAGE_SIZE = 1000;

    public List<Transaction> getAllTransactions(Integer page, Integer size) {
        if (page < 0) {
            page = 0;
        }
        if (size > MAX_PAGE_SIZE) {
            size = MAX_PAGE_SIZE;
        }
        return transactionRepository.findAll(PageRequest.of(page, size)).getContent();
    }

    public List<Transaction> getAllByAccountId(long accountId) {
        return transactionRepository.findByAccountId(accountId);
    }

    public List<Transaction> getAllByTransferId(long transferId) {
        return transactionRepository.findByTransferId(transferId);
    }
}
