package com.app.toolsUneedBack.service;

import com.app.toolsUneedBack.entity.BudgetEntity;
import com.app.toolsUneedBack.entity.TransactionEntity;
import com.app.toolsUneedBack.repository.CustomerRepository;
import com.app.toolsUneedBack.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService{

    private TransactionRepository transactionRepository;
    private BudgetService budgetService;


    public TransactionServiceImpl(TransactionRepository transactionRepository, BudgetService budgetService){
        this.transactionRepository = transactionRepository;
        this.budgetService = budgetService;
    }

    @Override
    public void newTransaction(TransactionEntity transaction) {

        Long budgetId = transaction.getBudget().getId();
        //proxy permet de simuler un objet
        BudgetEntity budgetProxy = budgetService.getReferenceById(budgetId);
        transaction.setBudget(budgetProxy);
        this.transactionRepository.save(transaction);

    }

    @Override
    public List<TransactionEntity> findAllTransactions(Long budgetId) {
        if(budgetId == null){
            return transactionRepository.findAll();
        }else{
            return transactionRepository.findByBudget_Id(budgetId);
        }
    }

    @Override
    public TransactionEntity findById(Long id) {
        Optional<TransactionEntity> optionalTransactionEntity = this.transactionRepository.findById(id);
        return optionalTransactionEntity.orElse(null);
    }

    @Override
    public void deleteTransaction(Long id) {
        this.transactionRepository.deleteById(id);
    }

    @Override
    public void editTransaction(Long id, TransactionEntity transaction) {
        TransactionEntity transactionEntityFromBDD = this.findById(id);

        if(transaction.getId() == transactionEntityFromBDD.getId()){
            transactionEntityFromBDD.setName(transaction.getName());
            transactionEntityFromBDD.setAmount(transaction.getAmount());
            transactionEntityFromBDD.setDetail(transaction.getDetail());
            transactionEntityFromBDD.setType(transaction.getType());

            // Ajout de la gestion du changement de budget
            if(transaction.getBudget() != null && transaction.getBudget().getId() != null) {
                Long budgetId = transaction.getBudget().getId();
                BudgetEntity budgetProxy = budgetService.getReferenceById(budgetId);
                transactionEntityFromBDD.setBudget(budgetProxy);
            }

            this.transactionRepository.save(transactionEntityFromBDD);
        }
    }
}
