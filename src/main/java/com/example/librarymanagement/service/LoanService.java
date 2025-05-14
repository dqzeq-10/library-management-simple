package com.example.librarymanagement.service;

import com.example.librarymanagement.model.Loan;
import com.example.librarymanagement.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoanService {
    @Autowired
    private LoanRepository loanRepository;

    public List<Loan> findAll() {
        return loanRepository.findAll();
    }

    public void save(Loan loan) {
        loanRepository.save(loan);
    }

    public Loan findById(String id) {
        return loanRepository.findById(id).orElse(null);
    }

    public void deleteById(String id) {
        loanRepository.deleteById(id);
    }

    public boolean existsById(String id) {
        return loanRepository.existsById(id);
    }
}