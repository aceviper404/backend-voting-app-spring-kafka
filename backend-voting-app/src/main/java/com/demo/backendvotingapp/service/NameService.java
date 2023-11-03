package com.demo.backendvotingapp.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.backendvotingapp.model.Vote;
import com.demo.backendvotingapp.repository.VotesRepository;

@Service
public class NameService {

    @Autowired
    private VotesRepository votesRepository;

    // Use default isolation level (READ_COMMITTED) and propagation behavior (REQUIRED)
    @Transactional
    public void postNames(String[] names) {
        for (String name : names) {
            // Check if the name already exists in the votes table
            if (votesRepository.existsByName(name)) {
                // Increment the number of votes for the existing name
                Vote vote = votesRepository.findByName(name);
                vote.setNumberOfVotes(vote.getNumberOfVotes() + 1);
                votesRepository.save(vote);
            } else {
                // Create a new vote with one vote for the new name
                Vote vote = new Vote();
                vote.setName(name);
                vote.setNumberOfVotes(1);
                votesRepository.save(vote);
            }
        }
    }
    
}