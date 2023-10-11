package ru.ylab.task1.repository.impl;

import ru.ylab.task1.model.transaction.TransactionType;
import ru.ylab.task1.model.transaction.Transaction;
import ru.ylab.task1.repository.TransactionRepository;

import java.util.List;
import java.util.Map;

/**
 * The type Transaction repository.
 */
public class TransactionRepositoryImpl implements TransactionRepository {

    private Map<Long, Transaction> transactionMap;

    /**
     * Instantiates a new Transaction repository.
     *
     * @param transactionMap the transaction map
     */
    public TransactionRepositoryImpl(Map<Long, Transaction> transactionMap) {
        this.transactionMap = transactionMap;
    }

    public Transaction createTransaction(Long id, TransactionType type, double amount, Long playerId) {
        Transaction transaction = new Transaction(id, type, amount, playerId);
        transactionMap.put(id, transaction);
        return transaction;
    }

    @Override
    public List<Transaction> getAllPlayerTransaction(Long playerId) {
        return transactionMap.values().stream().filter(x -> playerId.equals(x.getPlayerId())).toList();
    }


}
