package ru.ylab.task1.repository.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.ylab.task1.model.transaction.Transaction;
import ru.ylab.task1.model.transaction.TransactionType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TransactionRepositoryImplTest {

    private TransactionRepositoryImpl transactionRepository;
    private Map<Long, Transaction> transactionMap;

    @BeforeEach
    public void setUp() {
        transactionMap = new HashMap<>();
        transactionRepository = new TransactionRepositoryImpl(transactionMap);
    }

    @Test
    public void testCreateTransactionWhenNewTransactionThenAddedToMap() {

        Long id = 1L;
        TransactionType type = TransactionType.CREDIT;
        double amount = 100.0;
        Long playerId = 1L;


        Transaction transaction = transactionRepository.createTransaction(id, type, amount, playerId);


        assertEquals(transaction, transactionMap.get(id));
    }

    @Test
    public void testGetAllPlayerTransactionWhenTransactionsExistThenReturnCorrectTransactions() {

        Long playerId = 1L;
        Transaction transaction1 = new Transaction(1L, TransactionType.CREDIT, 100.0, playerId);
        Transaction transaction2 = new Transaction(2L, TransactionType.DEBIT, 50.0, playerId);
        transactionMap.put(1L, transaction1);
        transactionMap.put(2L, transaction2);


        List<Transaction> transactions = transactionRepository.getAllPlayerTransaction(playerId);


        assertEquals(2, transactions.size());
        assertTrue(transactions.contains(transaction1));
        assertTrue(transactions.contains(transaction2));
    }

    @Test
    public void testGetAllPlayerTransactionWhenNoTransactionsThenReturnEmptyList() {

        Long playerId = 1L;


        List<Transaction> transactions = transactionRepository.getAllPlayerTransaction(playerId);


        assertTrue(transactions.isEmpty());
    }
}
