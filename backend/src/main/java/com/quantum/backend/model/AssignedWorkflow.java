package com.quantum.backend.model;

import org.springframework.data.annotation.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssignedWorkflow {
    @Id
    private String assignedWorkflowId;

    private String workflowId;

    private Workflow workflow;

    private String vendorId;

    // DD/MM/YYYY
    private String dateAssigned;

    private String deadline;
}
