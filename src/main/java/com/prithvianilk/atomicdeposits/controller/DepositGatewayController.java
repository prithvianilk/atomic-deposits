package com.prithvianilk.atomicdeposits.controller;

import com.prithvianilk.atomicdeposits.repository.BankAccountRepository;
import com.prithvianilk.atomicdeposits.service.DepositsOrchestratorService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping(path = "/deposit-gateway")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DepositGatewayController {
    BankAccountRepository bankAccountRepository;
    DepositsOrchestratorService depositsOrchestratorService;

    @PostMapping(path = "/deposit/{userId}")
    public ResponseEntity<?> depositFromBankAccount(@PathVariable String userId, @RequestBody BigDecimal depositAmount) {
        bankAccountRepository.getByUserId(userId)
                             .filter(bankAccount -> bankAccount.getAmount().compareTo(depositAmount) > 0)
                             .ifPresent(bankAccount -> depositsOrchestratorService.depositAmount(bankAccount, depositAmount));
        return ResponseEntity.ok().build();
    }
}
