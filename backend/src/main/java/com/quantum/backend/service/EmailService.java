package com.quantum.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.quantum.backend.model.SendEmailRequest;

@Service
public class EmailService {
    @Value("${spring.mail.username}")
    private String mailServerSender;

    @Autowired
    private JavaMailSender emailSender;

    public void sendSimpleMessage(SendEmailRequest sendEmail) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(mailServerSender);
            message.setTo(sendEmail.getTo());
            message.setSubject(sendEmail.getSubject());
            message.setText(sendEmail.getText());
            emailSender.send(message);
        } catch (MailException exception) {
            exception.printStackTrace();
        }
    }
}
