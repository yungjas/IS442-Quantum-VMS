package com.quantum.backend.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quantum.backend.model.SendEmailRequest;
import com.quantum.backend.model.User;
import com.quantum.backend.model.Workflow;

@Service
public class ScheduledService {
    @Autowired
    private EmailService emailService;

    @Autowired 
    private WorkflowService workflowService;

    public void sendReminderEmail() throws ParseException{
        List<Workflow> allWorkflows = workflowService.getAllWorkflows();
        Date currDate = new Date();
        String subject = "Reminder";

        for(Workflow wf: allWorkflows){
            DateFormat sourceFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date deadline = sourceFormat.parse(wf.getDeadline());

            long diff = deadline.getTime() - currDate.getTime();
            long diffDays = diff / (24 * 60 * 60 * 1000);

            if(diffDays <= 3 && wf.getAssignedUsers() != null){
                String text = String.format("Reminder to complete %s by %s", wf.getWorkflowName(), wf.getDeadline());
                processEmail(wf.getAssignedUsers(), subject, text);
            }
        }
    }

    public void sendApprovedEmail(){
        List<Workflow> allWorkflows = workflowService.getAllWorkflows();
        String subject = "Form Approved";

        for(Workflow wf: allWorkflows){
            if(wf.getForm().getApprovedBy() != null){
                String text = String.format("%s has been approved", wf.getForm().getFormName());
                if(wf.getAssignedUsers() != null){
                    processEmail(wf.getAssignedUsers(), subject, text);
                }
            }
        }
    }

    private void processEmail(List<User> users, String subject, String text){
        for(User user: users){
            SendEmailRequest emailReminder = new SendEmailRequest(user.getEmail(), subject, text);
            emailService.sendSimpleEmail(emailReminder);
        }
    }
}
