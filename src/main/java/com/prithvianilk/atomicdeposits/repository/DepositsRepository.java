package com.prithvianilk.atomicdeposits.repository;

import com.prithvianilk.atomicdeposits.model.Deposit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepositsRepository extends JpaRepository<Deposit, String> {
    List<Deposit> getByUserId(String userId);
}
