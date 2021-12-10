package com.example.evoting.repository;

import com.example.evoting.model.Transactions;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionsRepository extends CrudRepository<Transactions, Long> {
    List<Transactions> findTransactionsByBlockId(Long id);
}
