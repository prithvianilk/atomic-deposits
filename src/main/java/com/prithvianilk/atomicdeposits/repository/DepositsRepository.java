package com.prithvianilk.atomicdeposits.repository;

import com.prithvianilk.atomicdeposits.model.Deposit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepositsRepository extends JpaRepository<Deposit, String> {
    Optional<Deposit> getByUserId(String userId);
}
