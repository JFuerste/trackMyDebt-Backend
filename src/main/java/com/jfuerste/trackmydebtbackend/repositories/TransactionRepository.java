package com.jfuerste.trackmydebtbackend.repositories;

import com.jfuerste.trackmydebtbackend.domain.Transaction;
import com.jfuerste.trackmydebtbackend.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {


    List<Transaction> findBySender(User sender);
    List<Transaction> findByReceiver(User receiver);
}
