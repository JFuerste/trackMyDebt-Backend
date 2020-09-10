package com.jfuerste.trackmydebtbackend.services;

import com.jfuerste.trackmydebtbackend.domain.Transaction;
import com.jfuerste.trackmydebtbackend.domain.User;
import com.jfuerste.trackmydebtbackend.repositories.TransactionRepository;

import java.util.ArrayList;
import java.util.List;

public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public List<Transaction> findAllInvolved(User user) {
        List<Transaction> transactions = new ArrayList<>();
        transactions.addAll(transactionRepository.findByReceiver(user));
        transactions.addAll(transactionRepository.findBySender(user));
        return transactions;
    }

    @Override
    public List<Transaction> findAllReceived(User user) {
        return transactionRepository.findByReceiver(user);
    }

    @Override
    public List<Transaction> findAllSent(User user) {
        return transactionRepository.findBySender(user);
    }
}
