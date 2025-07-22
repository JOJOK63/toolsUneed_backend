package com.app.toolsUneedBack.controller;

import com.app.toolsUneedBack.entity.CustomerEntity;
import com.app.toolsUneedBack.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "customer")
public class CustomerController {

    private CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @ResponseStatus(value = HttpStatus.CREATED) // permet de définir un code response
    @PostMapping(consumes = APPLICATION_JSON_VALUE) // permet de définir le type de données échangé
    public CustomerEntity newCustomer(@RequestBody CustomerEntity customer){
       return this.customerService.newCustomer(customer);
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public List<CustomerEntity> findAllCustomers(){
        return this.customerService.findAllCustomers();
    }

    @GetMapping(path = "{id}", produces = APPLICATION_JSON_VALUE)
    public CustomerEntity getCustomerById(@PathVariable Long id){
        return this.customerService.findById(id);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @DeleteMapping(path = "{id}")
    public void deleteCustomer(@PathVariable Long id){
        this.customerService.deleteCustomer(id);
    }

    @PutMapping(path="{id}",consumes = APPLICATION_JSON_VALUE)
    public void editCustomer(@PathVariable Long id, @RequestBody CustomerEntity customer){
            this.customerService.editCustomer(id,customer);
    }

}
