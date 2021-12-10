package com.example.evoting.controller;

import com.example.evoting.model.Transactions;
import com.example.evoting.repository.TransactionsRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins =  "http://localhost:4200")
public class TransactionsController {

    private final TransactionsRepository transactionsRepository;

    public TransactionsController(TransactionsRepository transactionsRepository) {
        this.transactionsRepository = transactionsRepository;
    }

    @GetMapping("/transactions/{id}")
    public List<Transactions> getAllTransactionsForBlock(@PathVariable String id) {
        return transactionsRepository.findTransactionsByBlockId(Long.parseLong(id));
    }

    @PostMapping("/transactions/create")
    void addTransactions(@RequestBody Transactions transactions) {
        transactionsRepository.save(transactions);
    }



}
