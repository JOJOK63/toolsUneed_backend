package com.app.toolsUneedBack.service;

import com.app.toolsUneedBack.entity.CustomerEntity;
import com.app.toolsUneedBack.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

//TODO gestion des erreurs ou du null ce qui va créeer des erreur coté angular
@Service
public class CustomerServiceImpl implements CustomerService {

//    @Autowired
//    private BCryptPasswordEncoder passwordEncoder;
    private CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }

    public CustomerEntity newCustomer(CustomerEntity customer){

//        customer.setPassword(passwordEncoder.encode(customer.getPassword()));

        CustomerEntity customerInBdd = this.customerRepository.findByEmail(customer.getEmail());
        if(customerInBdd == null){
            this.customerRepository.save(customer);
            return customer;
        }
        throw new ResponseStatusException(HttpStatus.CONFLICT, "Ce client existe déjà !");
    }

    public List<CustomerEntity> findAllCustomers(){
        return this.customerRepository.findAll();
    }

    public CustomerEntity findById(Long id) {
        Optional<CustomerEntity> optionalCustomer = this.customerRepository.findById(id);
        //optionnal permet de gérer le null, si optional existe alors on prend l'information à l'intérieur de  cette variable
        return optionalCustomer.orElse(null);
    }

    @Override
    public void deleteCustomer(Long id) {
        this.customerRepository.deleteById(id);
    }


    @Override
    public void editCustomer(Long id, CustomerEntity customer) {
        // Récupère l'entité existante depuis la base de données
        CustomerEntity customerFromBDD = this.findById(id);

        // Affiche l'entité pour le débogage
        System.out.println(customerFromBDD);

        if (customer.getId() == customerFromBDD.getId()) {
            // Si le mot de passe dans le nouveau customer est null, on garde celui de la base
            String passwordToSave = customer.getPassword() != null ? customer.getPassword() : customerFromBDD.getPassword();

            customerFromBDD.setFirstname(customer.getFirstname());
            customerFromBDD.setLastname(customer.getLastname());
            customerFromBDD.setEmail(customer.getEmail());
            customerFromBDD.setPassword(passwordToSave);  // Utilisation du mot de passe récupéré en base
            customerFromBDD.setRole(customer.getRole());
            customerFromBDD.setImage(customer.getImage());

            // Sauvegarde l'entité mise à jour
            this.customerRepository.save(customerFromBDD);
        }
    }


}
