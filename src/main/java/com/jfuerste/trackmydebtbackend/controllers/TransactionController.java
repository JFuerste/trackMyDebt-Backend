package com.jfuerste.trackmydebtbackend.controllers;


import com.jfuerste.trackmydebtbackend.domain.User;
import com.jfuerste.trackmydebtbackend.dto.TransactionDTO;
import com.jfuerste.trackmydebtbackend.errors.JsonErrorObject;
import com.jfuerste.trackmydebtbackend.services.TransactionService;
import com.jfuerste.trackmydebtbackend.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/api/v1/transactions")
@Slf4j
public class TransactionController {


    private final TransactionService transactionService;
    private final UserService userService;

    public TransactionController(TransactionService transactionService, UserService userService) {
        this.transactionService = transactionService;
        this.userService = userService;
    }

    @GetMapping("/sent")
    public ResponseEntity listSentTransactions(OAuth2Authentication authentication){
        User user = userService.getUser(authentication);
        return new ResponseEntity(transactionService.findAllSent(user), HttpStatus.OK);
    }

    @GetMapping("/received")
    public ResponseEntity listReceivedTransactions(OAuth2Authentication authentication){
        User user = userService.getUser(authentication);
        return new ResponseEntity(transactionService.findAllReceived(user), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity listTransactions(OAuth2Authentication authentication){
        User user = userService.getUser(authentication);
        return new ResponseEntity(transactionService.findAllInvolved(user), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity createTransaction(@RequestBody @Valid TransactionDTO dto, OAuth2Authentication auth2Authentication){
        User user = userService.getUser(auth2Authentication);
        String email = user.getEmail();


        if (email.equals(dto.getSender()) || email.equals(dto.getReceiver())){
            return new ResponseEntity(transactionService.createNewTransaction(dto), HttpStatus.OK);
        } else {
            return new ResponseEntity(
                    new JsonErrorObject("Cannot create transaction for strangers!", 403),
                    HttpStatus.FORBIDDEN);
        }
    }
}
