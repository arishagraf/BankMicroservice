package com.app.repositories;

import com.app.entities.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * repository annotation indicates that the underlying interface is a repository.
 * CRUDrepository has delete, save, findByID(or All) operations
 */
@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {
    public List<Customer> findCustomerByFullNameEndsWith(String lastName);
    public Customer findCustomerById(Long id);
}
