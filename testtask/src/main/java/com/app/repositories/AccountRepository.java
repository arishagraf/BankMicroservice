package com.app.repositories;

import com.app.entities.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * repository annotation indicates that the underlying interface is a repository.
 * CRUDrepository has delete, save, findByID(or All) operations
 */
@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {
    public Account findAccountById(Long id);
}
