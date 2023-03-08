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
}
