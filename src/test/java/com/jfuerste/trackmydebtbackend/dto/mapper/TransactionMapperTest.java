package com.jfuerste.trackmydebtbackend.dto.mapper;

import com.jfuerste.trackmydebtbackend.domain.Transaction;
import com.jfuerste.trackmydebtbackend.domain.User;
import com.jfuerste.trackmydebtbackend.dto.TransactionDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class TransactionMapperTest {

    public static final String SENDERMAIL = "sender@test.com";
    public static final String RECEIVERMAIL = "receiver@test.com";

    @Mock
    UserQualifier qualifier;

    @InjectMocks
    TransactionMapperImpl mapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void transactionToTransactionDTO() {
        User sender = User.builder().email(SENDERMAIL).build();
        User receiver = User.builder().email(RECEIVERMAIL).build();

        Transaction transaction = Transaction.builder()
                .sender(sender)
                .receiver(receiver)
                .amount(1.5)
                .id(1L)
                .shortReason("reson")
                .timestamp(LocalDateTime.now())
                .build();

        TransactionDTO dto = mapper.transactionToTransactionDTO(transaction);

        assertEquals(SENDERMAIL, dto.getSender());
        assertEquals(RECEIVERMAIL, dto.getReceiver());
        assertEquals(transaction.getAmount(), dto.getAmount());
        assertEquals(transaction.getId(), dto.getId());
        assertEquals(transaction.getShortReason(), dto.getShortReason());
        assertEquals(transaction.getTimestamp(), dto.getTimestamp());

    }

    @Test
    void dtoToTransaction() {
        User sender = User.builder().email(SENDERMAIL).id(1L).build();
        User receiver = User.builder().email(RECEIVERMAIL).id(2L).build();

        TransactionDTO dto = TransactionDTO.builder()
                .amount(1.5)
                .id(4L)
                .receiverId(2L)
                .senderId(1L)
                .timestamp(LocalDateTime.now())
                .build();

        when(qualifier.idToUser(1L)).thenReturn(sender);
        when(qualifier.idToUser(2L)).thenReturn(receiver);


        Transaction transaction = mapper.transactionDtoToTransaction(dto);

        assertThat(transaction.getSender()).isEqualTo(sender);
        assertThat(transaction.getReceiver()).isEqualTo(receiver);
        assertThat(transaction.getAmount()).isEqualTo(1.5);
        assertThat(transaction.getId()).isEqualTo(4L);
    }
}