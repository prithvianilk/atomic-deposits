package com.prithvianilk.atomicdeposits.controller;

import com.prithvianilk.atomicdeposits.model.Deposit;
import com.prithvianilk.atomicdeposits.repository.DepositsRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/deposits")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DepositsController {
    DepositsRepository depositsRepository;

    @GetMapping(path = "/{userId}")
    public ResponseEntity<List<Deposit>> getDeposits(@PathVariable String userId) {
        return ResponseEntity.ok(depositsRepository.getByUserId(userId));
    }

    // Shitty API but it's ok
    @PostMapping(path = "/{userId}")
    public ResponseEntity<Deposit> createDeposit(@PathVariable String userId, @RequestBody BigDecimal amount) {
        return ResponseEntity.ok(depositsRepository.save(new Deposit(UUID.randomUUID().toString(), userId, amount)));
    }
}
