package com.demo.backendvotingapp.repository;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.demo.backendvotingapp.model.Vote;

@Repository
public interface VotesRepository extends JpaRepository<Vote, Long> {

    // Find a vote by name
    Vote findByName(String name);

    // Check if a name exists in the table
    boolean existsByName(String name);

    // Find a vote by name and lock it for update
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select v from Vote v where v.name = :name")
    Vote findAndLockByName(@Param("name") String name);
}