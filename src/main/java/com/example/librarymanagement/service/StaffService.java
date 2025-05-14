package com.example.librarymanagement.service;

import com.example.librarymanagement.model.Staff;
import com.example.librarymanagement.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StaffService {
    @Autowired
    private StaffRepository staffRepository;

    public List<Staff> findAll() {
        return staffRepository.findAll();
    }

    public void save(Staff staff) {
        staffRepository.save(staff);
    }

    public Staff findById(String id) {
        return staffRepository.findById(id).orElse(null);
    }

    public void deleteById(String id) {
        staffRepository.deleteById(id);
    }
    public boolean existsById(String id) {
        return staffRepository.existsById(id);
    }
}