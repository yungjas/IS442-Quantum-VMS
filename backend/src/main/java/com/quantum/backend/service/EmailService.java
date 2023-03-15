package com.quantum.backend.service;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.quantum.backend.exception.EmailException;
import com.quantum.backend.model.SendEmailRequest;

import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {
    @Value("${spring.mail.username}")
    private String mailServerSender;

    @Autowired
    private JavaMailSender emailSender;

    public void sendSimpleEmail(SendEmailRequest sendEmail) throws EmailException{
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(mailServerSender);
            message.setTo(sendEmail.getTo());
            message.setSubject(sendEmail.getSubject());
            message.setText(sendEmail.getText());
            emailSender.send(message);
        } 
        catch (Exception e) {
            throw new EmailException(e);
        }
    }

    public void sendEmailWithAttachmentPath(SendEmailRequest sendEmail) throws EmailException{
        try{
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(mailServerSender);
            helper.setTo(sendEmail.getTo());
            helper.setSubject(sendEmail.getSubject());
            helper.setText(sendEmail.getText());

            FileSystemResource file = new FileSystemResource(new File(sendEmail.getPathToAttachment()));
            helper.addAttachment(file.getFilename(), file);

            emailSender.send(message);
        }
        catch (Exception e) {
            throw new EmailException(e);
        }
    }

    public void sendEmailWithAttachmentUpload(MultipartFile file, String to, String subject, String text) throws EmailException{
        try{
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            SendEmailRequest emailRequest = new SendEmailRequest(to, subject, text);

            helper.setFrom(mailServerSender);
            helper.setTo(emailRequest.getTo());
            helper.setSubject(emailRequest.getSubject());
            helper.setText(emailRequest.getText());

            ByteArrayResource bar = new ByteArrayResource(file.getBytes());
            
            helper.addAttachment(file.getOriginalFilename(), bar);

            emailSender.send(message);
        }
        catch (Exception e) {
            throw new EmailException(e);
        }
    }
}
