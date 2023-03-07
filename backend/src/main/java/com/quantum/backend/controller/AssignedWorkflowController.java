package com.quantum.backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import com.quantum.backend.model.AssignedWorkflow;
import com.quantum.backend.service.AssignedWorkflowService;

@RestController
@CrossOrigin
public class AssignedWorkflowController {
    
    private final AssignedWorkflowService assignedWorkflowService;
    public AssignedWorkflowController(AssignedWorkflowService assignedWorkflowService){
        this.assignedWorkflowService = assignedWorkflowService;
    }

    @GetMapping("all")
    public ResponseEntity<List<AssignedWorkflow>> getAllAssignedWorkflows() {
        List<AssignedWorkflow> allAssignedWorkflows = assignedWorkflowService.getAllAssignedWorkflows();
        if (allAssignedWorkflows.size() == 0){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(allAssignedWorkflows, HttpStatus.OK);
    }

    @PostMapping("create")
    public ResponseEntity<AssignedWorkflow> createAssignedWorkflow(@RequestBody AssignedWorkflow assignedWorkflow) {
        try {
            AssignedWorkflow createdAssignedWorkflow = assignedWorkflowService.createAssignedWorkflow(assignedWorkflow);
            if (createdAssignedWorkflow != null) {
                return new ResponseEntity<>(createdAssignedWorkflow, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //update
    @PutMapping("update/{assignedWorkflowId}")
    public ResponseEntity<AssignedWorkflow> updateAssignedWorkflow(@PathVariable String assignedWorkflowId, @RequestBody AssignedWorkflow assignedWorkflow){
        AssignedWorkflow assignedWorkflowUpdate = assignedWorkflowService.updateAssignedWorkflow(assignedWorkflowId, assignedWorkflow);
        if (assignedWorkflowUpdate == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(assignedWorkflowUpdate, HttpStatus.OK);
    }

    //delete 
    @DeleteMapping("delete/{workflowId}")
    public ResponseEntity<HttpStatus> deleteAssignedWorkflow(@PathVariable String assignedWorkflowId) {
        try {
            if (assignedWorkflowService.deleteAssignedWorkflow(assignedWorkflowId) == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
