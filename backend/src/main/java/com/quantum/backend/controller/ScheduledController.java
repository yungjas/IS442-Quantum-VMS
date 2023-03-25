package com.quantum.backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quantum.backend.service.ScheduledService;

@RestController
@CrossOrigin
@RequestMapping(path="api/scheduled")
public class ScheduledController {
    private final ScheduledService scheduledService;

    public ScheduledController(ScheduledService scheduledService){
        this.scheduledService = scheduledService;
    }

    //@GetMapping("test")
    @Scheduled(cron = "0 0 0 * * *") // executes a send remainder email every day at midnight
    public ResponseEntity<Object> emailReminder(){
        try{
            scheduledService.sendReminderEmail();
            scheduledService.sendApprovedEmail();
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
