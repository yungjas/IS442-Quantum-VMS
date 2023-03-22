package com.quantum.backend.model;
import java.util.*;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "userResponse")
public class UserResponse {
    @Id
    private String userResponseId;
    private String userId;
    private String formId;
    private String qnId;
    private Map<String, String> questionResponse;
}
