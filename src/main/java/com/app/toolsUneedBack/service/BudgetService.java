package com.app.toolsUneedBack.service;

import com.app.toolsUneedBack.entity.BudgetEntity;

import java.util.List;

public interface BudgetService {
    void newBudget(BudgetEntity budget);
    List<BudgetEntity> findAllBudgets(Long customerId);
    BudgetEntity findById(Long id);
    void deleteBudget(Long id);
    void editBudget(Long id, BudgetEntity budget);

    BudgetEntity getReferenceById(Long id);
}
