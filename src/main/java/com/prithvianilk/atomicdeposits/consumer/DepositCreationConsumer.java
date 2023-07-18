package com.prithvianilk.atomicdeposits.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.prithvianilk.atomicdeposits.constant.Constants;
import com.prithvianilk.atomicdeposits.model.Deposit;
import com.prithvianilk.atomicdeposits.repository.DepositsRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DepositCreationConsumer {
    ObjectMapper objectMapper;
    DepositsRepository depositsRepository;

    @KafkaListener(topics = Constants.DEPOSITS_TOPIC, groupId = Constants.GROUP_ID)
    public void receiveMessage(String message) throws JsonProcessingException {
        Deposit deposit = objectMapper.readValue(message, Deposit.class);
        depositsRepository.save(deposit);
    }
}
