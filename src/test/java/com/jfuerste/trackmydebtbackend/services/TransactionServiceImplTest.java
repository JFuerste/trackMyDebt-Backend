package com.jfuerste.trackmydebtbackend.services;

import com.jfuerste.trackmydebtbackend.domain.Transaction;
import com.jfuerste.trackmydebtbackend.domain.User;
import com.jfuerste.trackmydebtbackend.repositories.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TransactionServiceImplTest {

    @Mock
    TransactionRepository transactionRepository;

    TransactionServiceImpl service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        service = new TransactionServiceImpl(transactionRepository);
    }

    @Test
    void findAllInvolved() {
        Transaction transactionReceived = new Transaction();
        transactionReceived.setId(1L);
        Transaction transactionSent = new Transaction();
        transactionSent.setId(2L);

        when(transactionRepository.findByReceiver(any())).thenReturn(List.of(transactionReceived));
        when(transactionRepository.findBySender(any())).thenReturn(List.of(transactionSent));


        List<Transaction> transactions = service.findAllInvolved(new User());

        assertThat(transactions).contains(transactionSent, transactionReceived);
        verify(transactionRepository).findBySender(any());
        verify(transactionRepository).findByReceiver(any());
    }
}