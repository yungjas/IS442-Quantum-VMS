package com.quantum.backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quantum.backend.model.SendEmailRequest;
import com.quantum.backend.service.EmailService;


@RestController
@CrossOrigin
@RequestMapping(path="api/email")
public class EmailController {
    private final EmailService emailService;

    public EmailController(EmailService emailService){
        this.emailService = emailService;
    }

    @GetMapping("send")
    public ResponseEntity<HttpStatus> sendSimpleMessage(@RequestBody SendEmailRequest sendEmail){
        try{
            emailService.sendSimpleMessage(sendEmail);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); 
        }
    }
}