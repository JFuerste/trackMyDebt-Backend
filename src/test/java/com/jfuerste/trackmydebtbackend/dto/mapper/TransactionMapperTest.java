package com.jfuerste.trackmydebtbackend.dto.mapper;

import com.jfuerste.trackmydebtbackend.domain.Transaction;
import com.jfuerste.trackmydebtbackend.domain.User;
import com.jfuerste.trackmydebtbackend.dto.TransactionDTO;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TransactionMapperTest {

    public static final String SENDERMAIL = "sender@test.com";
    public static final String RECEIVERMAIL = "receiver@test.com";
    TransactionMapper mapper = TransactionMapper.INSTANCE;


    @Test
    void transactionToTransactionDTO() {
        User sender = User.builder().email(SENDERMAIL).build();
        User receiver = User.builder().email(RECEIVERMAIL).build();

        Transaction transaction = Transaction.builder()
                .sender(sender)
                .receiver(receiver)
                .amount(1.5)
                .id(1L)
                .reason("reson")
                .timestamp(LocalDateTime.now())
                .build();

        TransactionDTO dto = mapper.transactionToTransactionDTO(transaction);

        assertEquals(SENDERMAIL, dto.getSender());
        assertEquals(RECEIVERMAIL, dto.getReceiver());
        assertEquals(transaction.getAmount(), dto.getAmount());
        assertEquals(transaction.getId(), dto.getId());
        assertEquals(transaction.getReason(), dto.getReason());
        assertEquals(transaction.getTimestamp(), dto.getTimestamp());

    }
}