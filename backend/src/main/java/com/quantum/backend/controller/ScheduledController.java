package com.quantum.backend.controller;

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

    @Scheduled(cron = "0 23 23 * * ?") // executes a send remainder email every day at 11.23pm
    void emailReminder(){
        try{
            scheduledService.sendReminderEmail();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}
