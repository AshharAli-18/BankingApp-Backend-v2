package com.Training.BankingApp.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    User findOneByEmailAndPassword(String email, String password);
    Optional<User> findByEmail(String email);
    User findByUserId(Long userId);
    User findOneByEmail(String email);
}
