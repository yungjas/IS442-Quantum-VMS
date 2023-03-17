package com.quantum.backend.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quantum.backend.model.SendEmailRequest;

@Service
public class ScheduledService {
    @Autowired
    private EmailService emailService;

    // need to autowire workflow, user and form probably

    public void sendReminderEmail() throws ParseException{
        String userEmail = "jasminelim509@gmail.com";
        String workflowName = "workflow 1";
        String deadlineStr = "16/03/2023";
        String formNo = "form123";
        
        DateFormat sourceFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date deadline = sourceFormat.parse(deadlineStr);

        Date currDate = new Date();  

        long diff = deadline.getTime() - currDate.getTime();
        long diffDays = diff / (24 * 60 * 60 * 1000);

        String subject = "Reminder";
        String text = String.format("Reminder to complete %s by %s", workflowName, deadlineStr);

        if(diffDays <= 3){
            SendEmailRequest emailReminder = new SendEmailRequest(userEmail, subject, text);
            emailService.sendSimpleEmail(emailReminder);
        }

    }
}
