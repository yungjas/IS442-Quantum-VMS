package com.quantum.backend.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SendEmailRequest {
    @NotBlank
    private String to;

    @NotBlank
    private String subject;

    @NotBlank
    private String text;

    private String pathToAttachment;

    public SendEmailRequest(String to, String subject, String text){
        this.to = to;
        this.subject = subject;
        this.text = text;
    }
}
