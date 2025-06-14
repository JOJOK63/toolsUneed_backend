package com.app.toolsUneedBack.service;

import com.app.toolsUneedBack.entity.BudgetEntity;
import com.app.toolsUneedBack.entity.CustomerEntity;
import com.app.toolsUneedBack.repository.BudgetRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

//TODO gestion des erreurs

@Service
public class BudgetServiceImpl implements BudgetService{

    private BudgetRepository budgetRepository;
    private CustomerService customerService;

    public BudgetServiceImpl(BudgetRepository budgetRepository,CustomerService customerService) {
        this.budgetRepository = budgetRepository;
        this.customerService = customerService;
    }

    @Override
    public void newBudget(BudgetEntity budget) {
        Long customerId =budget.getCustomer().getId();
        CustomerEntity customer = this.customerService.getCustomerById(customerId);
        budget.setCustomer(customer);
        this.budgetRepository.save(budget);
    }

    @Override
    public List<BudgetEntity> findAllBudgets(Long customerId) {
        if(customerId ==null){
            return budgetRepository.findAll();
        }else{
            return budgetRepository.findByCustomer_Id(customerId);
        }
    }

    @Override
    public BudgetEntity findById(Long id) {
        Optional<BudgetEntity> optionalBudget = this.budgetRepository.findById(id);
        return optionalBudget.orElse(null);
    }

    @Override
    public void deleteBudget(Long id) {
        this.budgetRepository.deleteById(id);
    }


}
