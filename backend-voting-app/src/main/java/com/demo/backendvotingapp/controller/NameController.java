package com.demo.backendvotingapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.backendvotingapp.model.Code;
import com.demo.backendvotingapp.model.NamesRequest;
import com.demo.backendvotingapp.repository.CodesRepository;
import com.demo.backendvotingapp.service.NameService;

@RestController
@RequestMapping("/")
public class NameController {

    @Autowired
    private NameService nameService;
    
    @Autowired
    private CodesRepository codesRepository;
    
    @Autowired
    private KafkaTemplate<String, NamesRequest> kafkaTemplate;

    //test
    @GetMapping
    public String test() {
    	return "backend voting app works!";
    }
    
    // Post a list of names and store them in the database
    @PostMapping("/vote")
    public ResponseEntity<String> postNames(@RequestBody NamesRequest request) {
    	
    	ListenableFuture<SendResult<String, NamesRequest>> result = kafkaTemplate.send("votes", request);
    	
    	return new ResponseEntity<>("Names stored successfully", HttpStatus.ACCEPTED);
    	
//        // Acquire the lock before calling the service method
//        lock.lock();
//        try {
//        	
//            nameService.postNames(request.getNames());
//            return new ResponseEntity<>("Names stored successfully", HttpStatus.OK);
//        } catch (OptimisticLockException e) {
//            return new ResponseEntity<>("Names updated by another request", HttpStatus.CONFLICT);
//        } catch (Exception e) {
//            return new ResponseEntity<>("An error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
//        } finally {
//            // Release the lock after the service method returns
//            lock.unlock();
//        }
    }
    
    @GetMapping("/codeExists/{code}")
    public ResponseEntity<Boolean> getCode(@PathVariable String code) {
        // Call the repository method to check if the code exists
    	int codeInt = Integer.parseInt(code);
        boolean exists = false;
        		codesRepository.existsByCode(codeInt);
        if(!exists) {
        	Code codeToSave = Code.builder().code(codeInt).build();
        	codesRepository.save(codeToSave);
        }
        //System.out.println("CODE EXISTS: "+exists);
        // Return a response entity with status 200 and the boolean value
        return new ResponseEntity<>(exists, HttpStatus.OK);
    }
    
    // kafka listener
    @KafkaListener(topics="votes", groupId = "groupId", containerFactory = "namesRequestListener")
    void listener(NamesRequest data) {
    	nameService.postNames(data.getNames());
    }
    
    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<String> handleDataAccessException(DataAccessException e) {
        // Log the exception and return a response entity with status 500 and an error message
        e.printStackTrace();
        return new ResponseEntity<>("An error occurred while accessing the database", HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        // Log the exception and return a response entity with status 500 and an error message
        e.printStackTrace();
        return new ResponseEntity<>("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
    }

}