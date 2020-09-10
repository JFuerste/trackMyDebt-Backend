package com.jfuerste.trackmydebtbackend.services;

import com.jfuerste.trackmydebtbackend.domain.Transaction;
import com.jfuerste.trackmydebtbackend.domain.User;

import java.util.List;

public interface TransactionService {

    List<Transaction> findAllInvolved(User user);
    List<Transaction> findAllReceived(User user);
    List<Transaction> findAllSent(User user);


}
