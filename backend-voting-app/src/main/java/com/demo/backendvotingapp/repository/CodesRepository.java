package com.demo.backendvotingapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.backendvotingapp.model.Code;

@Repository
public interface CodesRepository extends JpaRepository<Code, Long> {

    // Check if a code exists in the table
    boolean existsByCode(int codeInt);
}