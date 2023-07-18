package com.prithvianilk.atomicdeposits.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prithvianilk.atomicdeposits.constant.Constants;
import com.prithvianilk.atomicdeposits.model.BankAccount;
import com.prithvianilk.atomicdeposits.model.Deposit;
import com.prithvianilk.atomicdeposits.repository.BankAccountRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DepositsOrchestratorService {
    BankAccountRepository bankAccountRepository;
    KafkaTemplate<String, String> kafkaTemplate;
    ObjectMapper objectMapper;

    @SneakyThrows
    @Transactional
    public void depositAmount(BankAccount bankAccount, BigDecimal depositAmount) {
        BigDecimal newAmount = bankAccount.getAmount().subtract(depositAmount);
        bankAccount.setAmount(newAmount);
        bankAccountRepository.save(bankAccount);
        simulateFailure();
        Deposit deposit = new Deposit(UUID.randomUUID().toString(), bankAccount.getUserId(), depositAmount);
        String message = objectMapper.writeValueAsString(deposit);
        kafkaTemplate.send(Constants.DEPOSITS_TOPIC, message);
    }

    private static void simulateFailure() {
        boolean isThrowException = new Random().nextInt(10) < 5;
        if (isThrowException) {
            throw new RuntimeException();
        }
    }
}
