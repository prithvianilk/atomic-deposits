package com.prithvianilk.atomicdeposits.controller;

import com.prithvianilk.atomicdeposits.model.Deposit;
import com.prithvianilk.atomicdeposits.repository.DepositsRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping(path = "/deposits")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DepositsController {
    DepositsRepository depositsRepository;

    @GetMapping(path = "/{userId}")
    public ResponseEntity<Deposit> getBankAccount(@PathVariable String userId) {
        return depositsRepository.getByUserId(userId)
                                 .map(ResponseEntity::ok)
                                 .orElseGet(ResponseEntity.notFound()::build);
    }

    // Shitty API but it's ok
    @PostMapping(path = "/{userId}")
    public ResponseEntity<Deposit> createDeposit(@PathVariable String userId, @RequestBody BigDecimal amount) {
        return ResponseEntity.ok(depositsRepository.save(new Deposit(userId, amount)));
    }
}
