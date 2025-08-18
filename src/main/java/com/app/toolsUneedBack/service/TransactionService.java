package com.app.toolsUneedBack.service;

import com.app.toolsUneedBack.entity.TransactionEntity;

import java.util.List;

public interface TransactionService {
    void newTransaction(TransactionEntity transaction);
    List<TransactionEntity> findAllTransactions(Long budgetId);
    TransactionEntity findById(Long id);
    void deleteTransaction(Long id);
    void editTransaction(Long id, TransactionEntity transaction);

}
