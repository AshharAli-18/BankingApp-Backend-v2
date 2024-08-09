package com.Training.BankingApp.customerrequest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRequestRepository extends JpaRepository<CustomerRequest, Integer> {

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
