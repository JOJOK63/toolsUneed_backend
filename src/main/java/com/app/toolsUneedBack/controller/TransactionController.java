package com.app.toolsUneedBack.controller;

import com.app.toolsUneedBack.entity.TransactionEntity;
import com.app.toolsUneedBack.entity.TransactionType;
import com.app.toolsUneedBack.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@RestController
@RequestMapping(path = "transaction")
public class TransactionController {


    private TransactionService transactionService;

    public TransactionController(TransactionService transactionService){
        this.transactionService = transactionService;
    }

    @ResponseStatus( value = HttpStatus.CREATED)
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public void newTransaction(@RequestBody TransactionEntity transaction){
        transactionService.newTransaction(transaction);
    }

    @GetMapping
    public List<TransactionEntity> findAllTransactions(@RequestParam(required = false) Long budgetId){
        return this.transactionService.findAllTransactions(budgetId);
    }

    @GetMapping(path = "{id}", produces = APPLICATION_JSON_VALUE)
    public TransactionEntity getTransactionById(@PathVariable Long id){
        return this.transactionService.findById(id);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @DeleteMapping(path= "{id}")
    public void deleteTransaction(@PathVariable Long id){this.transactionService.deleteTransaction(id);}

    @PutMapping(path = "{id}", consumes = APPLICATION_JSON_VALUE)
    public void editTransaction(@PathVariable Long id, @RequestBody TransactionEntity transaction){
        this.transactionService.editTransaction(id,transaction);
    }

}
