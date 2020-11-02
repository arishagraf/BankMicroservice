package com.app.repositories;

import com.app.entities.Credit;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * repository annotation indicates that the underlying interface is a repository.
 * CRUDrepository has delete, save, findByID(or All) operations
 */
@Repository
public interface CreditRepository extends CrudRepository<Credit, Long> {
    Credit findCreditById(Long id);
    List<Credit> findCreditsByRemainAmountIsGreaterThanAndEndBefore(Double d, Date date);
}