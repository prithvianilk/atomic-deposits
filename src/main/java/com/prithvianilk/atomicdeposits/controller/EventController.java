package com.prithvianilk.atomicdeposits.controller;

import ch.qos.logback.core.testUtil.RandomUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.prithvianilk.atomicdeposits.constant.Constants;
import com.prithvianilk.atomicdeposits.model.Deposit;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping(path = "/events")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EventController {
    KafkaTemplate<String, String> kafkaTemplate;
    ObjectMapper objectMapper;

    @PostMapping(path = "/")
    public ResponseEntity<?> pushEvent() throws JsonProcessingException {
        Deposit deposit = new Deposit(UUID.randomUUID().toString(), UUID.randomUUID().toString(), new BigDecimal(RandomUtil.getPositiveInt()));
        String message = objectMapper.writeValueAsString(deposit);
        kafkaTemplate.send(Constants.DEPOSITS_TOPIC, message);
        return ResponseEntity.ok().build();
    }
}
