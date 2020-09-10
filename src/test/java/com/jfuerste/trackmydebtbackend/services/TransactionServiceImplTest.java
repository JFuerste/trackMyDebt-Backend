package com.jfuerste.trackmydebtbackend.services;

import com.jfuerste.trackmydebtbackend.domain.Transaction;
import com.jfuerste.trackmydebtbackend.domain.User;
import com.jfuerste.trackmydebtbackend.dto.TransactionDTO;
import com.jfuerste.trackmydebtbackend.dto.mapper.TransactionMapperImpl;
import com.jfuerste.trackmydebtbackend.dto.mapper.UserQualifier;
import com.jfuerste.trackmydebtbackend.repositories.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
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

    @Mock
    UserQualifier qualifier;

    @InjectMocks
    TransactionMapperImpl mapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        service = new TransactionServiceImpl(transactionRepository, mapper);
    }

    @Test
    void findAllInvolved() {
        Transaction transactionReceived = new Transaction();
        transactionReceived.setId(1L);
        Transaction transactionSent = new Transaction();
        transactionSent.setId(2L);

        when(transactionRepository.findByReceiver(any())).thenReturn(List.of(transactionReceived));
        when(transactionRepository.findBySender(any())).thenReturn(List.of(transactionSent));


        List<TransactionDTO> transactions = service.findAllInvolved(new User());

        assertThat(transactions).hasSize(2);
        verify(transactionRepository).findBySender(any());
        verify(transactionRepository).findByReceiver(any());
    }

    @Test
    void createNewTransaction() {
        TransactionDTO dto = TransactionDTO.builder().id(1L).build();
        Transaction transaction = Transaction.builder().id(1L).build();

        when(transactionRepository.save(any())).thenReturn(transaction);
        when(qualifier.idToUser(1L)).thenReturn(new User());

        TransactionDTO savedDto = service.createNewTransaction(dto);

        assertThat(savedDto.getId()).isEqualTo(transaction.getId());
    }
}