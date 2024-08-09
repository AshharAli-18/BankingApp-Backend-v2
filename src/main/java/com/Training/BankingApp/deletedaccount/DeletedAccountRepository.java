package com.Training.BankingApp.deletedaccount;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeletedAccountRepository extends JpaRepository<DeletedAccount, Long> {

}
