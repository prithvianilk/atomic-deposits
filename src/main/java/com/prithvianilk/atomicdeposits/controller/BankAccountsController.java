package com.prithvianilk.atomicdeposits.controller;

import com.prithvianilk.atomicdeposits.model.BankAccount;
import com.prithvianilk.atomicdeposits.repository.BankAccountRepository;
import com.prithvianilk.atomicdeposits.service.DepositsOrchestratorService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping(path = "/bank_accounts")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BankAccountsController {
    BankAccountRepository bankAccountRepository;
    DepositsOrchestratorService depositsOrchestratorService;

    @GetMapping(path = "/{userId}")
    public ResponseEntity<BankAccount> getBankAccount(@PathVariable String userId) {
        return bankAccountRepository.getByUserId(userId)
                                    .map(ResponseEntity::ok)
                                    .orElseGet(ResponseEntity.notFound()::build);
    }

    // Shitty API but it's ok
    @PostMapping(path = "/{userId}")
    public ResponseEntity<BankAccount> getBankAccount(@PathVariable String userId, @RequestBody BigDecimal amount) {
        return ResponseEntity.ok(bankAccountRepository.save(new BankAccount(userId, amount)));
    }

    @PostMapping(path = "/deposit/{userId}")
    public ResponseEntity<?> depositFromBankAccount(@PathVariable String userId, @RequestBody BigDecimal depositAmount) {
        bankAccountRepository.getByUserId(userId)
                             .filter(bankAccount -> bankAccount.getAmount().compareTo(depositAmount) > 0)
                             .ifPresent(bankAccount -> depositsOrchestratorService.depositAmount(bankAccount, depositAmount));
        return ResponseEntity.ok().build();
    }
}
