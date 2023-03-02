package com.quantum.backend.model;

import org.springframework.data.annotation.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssignedWorkflow {
    @Id
    private String assignedWorkFlowID;

    private String workflowID;
    
    private Workflow workflow;

    private String vendorID;

    // DD/MM/YYYY
    private String dataAssigned;

    private String deadline;
}
