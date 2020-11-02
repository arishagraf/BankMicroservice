package com.app.repositories;

import com.app.entities.Account;
import com.app.entities.Transfer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * repository annotation indicates that the underlying interface is a repository.
 * CRUDrepository has delete, save, findByID(or All) operations
 */
@Repository
public interface TransferRepository extends CrudRepository<Transfer, Long> {
    List<Transfer> findAllByDate(Date date);
}