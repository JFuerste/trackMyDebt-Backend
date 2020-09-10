package com.jfuerste.trackmydebtbackend.services;

import com.jfuerste.trackmydebtbackend.domain.User;
import com.jfuerste.trackmydebtbackend.dto.TransactionDTO;

import java.util.List;

public interface TransactionService {

    List<TransactionDTO> findAllInvolved(User user);
    List<TransactionDTO> findAllReceived(User user);
    List<TransactionDTO> findAllSent(User user);
    TransactionDTO createNewTransaction(TransactionDTO dto);


}
