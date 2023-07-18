package com.prithvianilk.atomicdeposits.repository;

import com.prithvianilk.atomicdeposits.model.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, String> {
    Optional<BankAccount> getByUserId(String userId);
}
