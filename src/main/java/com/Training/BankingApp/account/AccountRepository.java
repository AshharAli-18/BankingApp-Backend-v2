package com.Training.BankingApp.account;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    @EntityGraph(attributePaths = {"user"})
    Account findByAccountNumber(String accountNumber);
    Account findByUserId(Long userId);
}
