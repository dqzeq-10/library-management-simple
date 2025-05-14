package com.example.librarymanagement.service;

import com.example.librarymanagement.model.Fine;
import com.example.librarymanagement.repository.FineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FineService {
    @Autowired
    private FineRepository fineRepository;

    public List<Fine> findAll() {
        return fineRepository.findAll();
    }

    public void save(Fine fine) {
        fineRepository.save(fine);
    }

    public Fine findById(String id) {
        return fineRepository.findById(id).orElse(null);
    }

    public void deleteById(String id) {
        fineRepository.deleteById(id);
    }

    public boolean existsById(String id) {
        return fineRepository.existsById(id);
    }
}