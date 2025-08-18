package com.app.toolsUneedBack.service;

import com.app.toolsUneedBack.entity.CustomerEntity;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CustomerService {
    CustomerEntity newCustomer(CustomerEntity customer);
    List<CustomerEntity> findAllCustomers();
    CustomerEntity findById(Long id);
    void deleteCustomer(Long id);
    void editCustomer(Long id, CustomerEntity customer);
}
