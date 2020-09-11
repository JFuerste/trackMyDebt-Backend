package com.jfuerste.trackmydebtbackend.controllers;

import com.jfuerste.trackmydebtbackend.dto.TransactionDTO;
import com.jfuerste.trackmydebtbackend.services.TransactionService;
import com.jfuerste.trackmydebtbackend.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TransactionControllerTest {

    @Mock
    UserService userService;

    @Mock
    TransactionService transactionService;

    @InjectMocks
    TransactionController controller;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void listTransaction() throws Exception {
        TransactionDTO dto = TransactionDTO.builder()
                .id(1L)
                .amount(20.4)
                .receiverId(2L)
                .senderId(3L)
                .timestamp(LocalDateTime.now())
                .build();


        when(transactionService.findAllInvolved(any())).thenReturn(List.of(dto));

        mockMvc.perform(get("/api/v1/transactions").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        System.out.println("");

    }

    @Test
    void listSentTransaction() throws Exception {
        TransactionDTO dto = TransactionDTO.builder()
                .id(1L)
                .amount(20.4)
                .receiverId(2L)
                .senderId(3L)
                .timestamp(LocalDateTime.now())
                .build();


        when(transactionService.findAllInvolved(any())).thenReturn(List.of(dto));

        mockMvc.perform(get("/api/v1/transactions/sent").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        System.out.println("");

    }

    @Test
    void listReceivedTransaction() throws Exception {
        TransactionDTO dto = TransactionDTO.builder()
                .id(1L)
                .amount(20.4)
                .receiverId(2L)
                .senderId(3L)
                .timestamp(LocalDateTime.now())
                .build();


        when(transactionService.findAllInvolved(any())).thenReturn(List.of(dto));

        mockMvc.perform(get("/api/v1/transactions/received").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        System.out.println("");
    }
}
