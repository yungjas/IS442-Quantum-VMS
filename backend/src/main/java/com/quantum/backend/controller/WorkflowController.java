package com.quantum.backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import com.quantum.backend.model.Workflow;
import com.quantum.backend.service.WorkflowService;

@RestController
@CrossOrigin
@RequestMapping(path="api/workflow")
public class WorkflowController {
    private final WorkflowService workflowService;

    public WorkflowController(WorkflowService workflowService) {
        this.workflowService = workflowService;
    }

    @GetMapping("all")
    public ResponseEntity<List<Workflow>> getAllWorkflows() {
        List<Workflow> allWorkflows = workflowService.getAllWorkflows();
        if (allWorkflows.size() == 0) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(allWorkflows, HttpStatus.OK);
    }

    @PostMapping("create")
    public ResponseEntity<Workflow> createWorkflow(@RequestBody Workflow workflow) {
        try {
            Workflow createdWorkflow = workflowService.createWorkflow(workflow);
            if (createdWorkflow != null) {
                return new ResponseEntity<>(createdWorkflow, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("update/{workflowId}")
    public ResponseEntity<Workflow> updateWorkflow(@PathVariable String workflowId, @RequestBody Workflow workflow){
        Workflow workflowUpdate = workflowService.updateWorkflow(workflowId, workflow);
        if (workflowUpdate == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(workflowUpdate, HttpStatus.OK);
    }

    @DeleteMapping("delete/{workflowId}")
    public ResponseEntity<HttpStatus> deleteWorkflow(@PathVariable String workflowId) {
        try {
            if (workflowService.deleteWorkflow(workflowId) == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
