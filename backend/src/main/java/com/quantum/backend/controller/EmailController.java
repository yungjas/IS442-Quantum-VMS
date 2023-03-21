package com.quantum.backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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

    @PostMapping("send")
    public ResponseEntity<Object> sendSimpleEmail(@RequestBody SendEmailRequest sendEmail){
        try{
            emailService.sendSimpleEmail(sendEmail);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); 
        }
    }

    @PostMapping("send_attachment_path")
    public ResponseEntity<Object> sendEmailWithAttachmentPath(@RequestBody SendEmailRequest sendEmail){
        try{
            emailService.sendEmailWithAttachmentPath(sendEmail);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); 
        }
    }

    @PostMapping("send_attachment_upload")
    public ResponseEntity<Object> sendEmailWithAttachmentUpload(@RequestParam("file") MultipartFile file, @RequestParam String to, @RequestParam String subject, @RequestParam String text){
        try{
            emailService.sendEmailWithAttachmentUpload(file, to, subject, text);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); 
        }
    }
}
