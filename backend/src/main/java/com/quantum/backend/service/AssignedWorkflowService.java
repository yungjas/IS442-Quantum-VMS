package com.quantum.backend.service;

import java.util.List;
import java.util.Optional;

import com.quantum.backend.model.*;
import org.springframework.stereotype.Service;
import com.quantum.backend.repository.AssignedWorkflowRepository;

@Service
public class AssignedWorkflowService {
    private final AssignedWorkflowRepository assignedWorkflowRepo;

    public AssignedWorkflowService(AssignedWorkflowRepository assignedWorkflowRepo) {
        this.assignedWorkflowRepo = assignedWorkflowRepo;
    }

    public List<AssignedWorkflow> getAllAssignedWorkflows() {
        return assignedWorkflowRepo.findAll();
    }

    public AssignedWorkflow createAssignedWorkflow(AssignedWorkflow assignedWorkflow) {
        assignedWorkflowRepo.save(assignedWorkflow);
        return assignedWorkflow;
    }

    // update
    public AssignedWorkflow updateAssignedWorkflow(String assignedWorkflowId, AssignedWorkflow assignedWorkflowUpdate) {
        Optional<AssignedWorkflow> assignedWorkflow = assignedWorkflowRepo.findById(assignedWorkflowId);
        if (assignedWorkflow.isPresent()) {
            AssignedWorkflow assignedWorkflowOriginal = assignedWorkflow.get();
            assignedWorkflowOriginal.setDeadline(assignedWorkflowUpdate.getDeadline());
            assignedWorkflowOriginal.setWorkflowId(assignedWorkflowUpdate.getWorkflowId());
            assignedWorkflowOriginal.setWorkflow(assignedWorkflowUpdate.getWorkflow());
            assignedWorkflowOriginal.setVendorId(assignedWorkflowUpdate.getVendorId());
            assignedWorkflowOriginal.setAssignedWorkflowId(assignedWorkflowUpdate.getAssignedWorkflowId());
            assignedWorkflowOriginal.setDateAssigned(assignedWorkflowUpdate.getDateAssigned());
        }
        return null;
    }

    // delete
    public AssignedWorkflow deleteAssignedWorkflow(String assignedWorkflowId) {
        Optional<AssignedWorkflow> assignedWorkflow = assignedWorkflowRepo.findByAssignedWorkflowId(assignedWorkflowId);
        if (assignedWorkflow.isPresent()) {
            AssignedWorkflow assignedWorkflowData = assignedWorkflow.get();
            assignedWorkflowRepo.delete(assignedWorkflowData);
            return assignedWorkflowData;
        }
        return null;
    }

}
