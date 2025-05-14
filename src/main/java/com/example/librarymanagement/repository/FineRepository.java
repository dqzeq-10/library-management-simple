package com.example.librarymanagement.repository;

import com.example.librarymanagement.model.Fine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FineRepository extends JpaRepository<Fine, String> {
}