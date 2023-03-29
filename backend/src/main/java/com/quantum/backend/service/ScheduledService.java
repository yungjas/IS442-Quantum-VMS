package com.quantum.backend.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
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

            if(diffDays <= 3 && wf.getAssignedUsers() != null && wf.getAssignedAdmins() != null && wf.getAssignedVendors() != null){
                String text = String.format("Reminder to complete %s by %s if you have not done so. Please ignore this email if you have already responded.", wf.getWorkflowName(), wf.getDeadline());
                HashSet<String> emailList = processEmailList(wf.getAssignedUsers(), wf.getAssignedAdmins(), wf.getAssignedVendors());
                processEmail(emailList, subject, text);
                emailList.clear();
            }
        }
    }

    public void sendApprovedEmail(){
        List<Workflow> allWorkflows = workflowService.getAllWorkflows();
        String subject = "Form Approved";

        for(Workflow wf: allWorkflows){
            if(wf.getForm().getApprovedBy() != null){
                String text = String.format("%s has been approved", wf.getForm().getFormName());
                if(wf.getAssignedUsers() != null && wf.getAssignedAdmins() != null && wf.getAssignedVendors() != null){
                    HashSet<String> emailList = processEmailList(wf.getAssignedUsers(), wf.getAssignedAdmins(), wf.getAssignedVendors());
                    processEmail(emailList, subject, text);
                    emailList.clear();
                }
            }
        }
    }

    private HashSet<String> processEmailList(List<User> users, List<User> assignedAdmins, List<User> assignedVendors){
        HashSet<String> emails = new HashSet<>();
        for(User user: users){
            emails.add(user.getEmail());
        }
        for(User user: assignedAdmins){
            emails.add(user.getEmail());
        }
        for(User user: assignedVendors){
            emails.add(user.getEmail());
        }
        return emails;
    }

    private void processEmail(HashSet<String> emailList, String subject, String text){
        for(String email: emailList){
            SendEmailRequest emailReminder = new SendEmailRequest(email, subject, text);
            emailService.sendSimpleEmail(emailReminder);
        }
    }
}
