package com.app.toolsUneedBack.service;

import com.app.toolsUneedBack.entity.CustomerEntity;
import com.app.toolsUneedBack.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

//TODO gestion des erreurs ou du null ce qui va créeer des erreur coté angular
@Service
public class CustomerServiceImpl implements CustomerService {

    private CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }

    public void newCustomer(CustomerEntity customer){
        CustomerEntity customerInBdd = this.customerRepository.findByEmail(customer.getEmail());

        if(customerInBdd == null){
            this.customerRepository.save(customer);
        }
    }

    public List<CustomerEntity> findAllCustomers(){
        return this.customerRepository.findAll();
    }

    public CustomerEntity getCustomerById(Long id) {
        Optional<CustomerEntity> optionalCustomer = this.customerRepository.findById(id);
        //optionnal permet de gérer le null, si optional existe alors on prend l'information à l'intérieur de  cette variable
        return optionalCustomer.orElse(null);
    }

    @Override
    public void deleteCustomer(Long id) {
        this.customerRepository.deleteById(id);
    }

    @Override
    public void editCustomer(Long id,CustomerEntity customer) {
        CustomerEntity customerFromBDD = this.getCustomerById(id);

        if(customer.getId() == customerFromBDD.getId()){
            customerFromBDD.setFirstname(customer.getFirstname());
            customerFromBDD.setLastname(customer.getLastname());
            customerFromBDD.setEmail(customer.getEmail());
            customerFromBDD.setRole(customer.getRole());
            this.customerRepository.save(customerFromBDD);
        }
    }
}
