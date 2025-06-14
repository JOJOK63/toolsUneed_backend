package com.app.toolsUneedBack.controller;

import com.app.toolsUneedBack.entity.BudgetEntity;
import com.app.toolsUneedBack.service.BudgetService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping (path = "budget")
public class BudgetController {

    private BudgetService budgetService;

    public BudgetController(BudgetService budgetService) {
        this.budgetService = budgetService;
    }

    @ResponseStatus(value = HttpStatus.CREATED) // permet de définir un code response
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public void newBudget(@RequestBody BudgetEntity budget){
        budgetService.newBudget(budget);
    }

    @GetMapping
    public List<BudgetEntity> findAllBudgets(@RequestParam(required = false) Long customerId){
        return this.budgetService.findAllBudgets(customerId);
    }

    @GetMapping(path = "{id}", produces = APPLICATION_JSON_VALUE)
    public BudgetEntity getBudgetById(@PathVariable Long id){
        return this.budgetService.findById(id);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @DeleteMapping(path = "{id}")
    public void deleteBudget(@PathVariable Long id){
        this.budgetService.deleteBudget(id);
    }

}
