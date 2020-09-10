package com.jfuerste.trackmydebtbackend.services;

import com.jfuerste.trackmydebtbackend.domain.Transaction;
import com.jfuerste.trackmydebtbackend.domain.User;
import com.jfuerste.trackmydebtbackend.dto.TransactionDTO;
import com.jfuerste.trackmydebtbackend.dto.mapper.TransactionMapper;
import com.jfuerste.trackmydebtbackend.repositories.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionMapper mapper;

    public TransactionServiceImpl(TransactionRepository transactionRepository, TransactionMapper mapper) {
        this.transactionRepository = transactionRepository;
        this.mapper = mapper;
    }

    @Override
    public List<TransactionDTO> findAllInvolved(User user) {
        List<Transaction> transactions = new ArrayList<>();
        transactions.addAll(user.getReceivedTransactions());
        transactions.addAll(user.getSentTransactions());
        return transactions.stream()
                .map(mapper::transactionToTransactionDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<TransactionDTO> findAllReceived(User user) {
        return user.getReceivedTransactions().stream()
                .map(mapper::transactionToTransactionDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<TransactionDTO> findAllSent(User user) {
        return user.getSentTransactions().stream()
                .map(mapper::transactionToTransactionDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TransactionDTO createNewTransaction(TransactionDTO dto) {
        Transaction transaction = mapper.transactionDtoToTransaction(dto);
        return mapper.transactionToTransactionDTO(transactionRepository.save(transaction));
    }
}
