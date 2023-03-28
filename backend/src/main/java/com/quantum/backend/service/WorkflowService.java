package com.quantum.backend.service;

import java.util.*;
import java.util.Optional;

import com.quantum.backend.exception.RequestErrorException;
import com.quantum.backend.exception.ResourceNotFoundException;
import com.quantum.backend.model.*;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.quantum.backend.repository.UserRepository;
import com.quantum.backend.repository.WorkflowRepository;

@Service
public class WorkflowService {
//    private final WorkflowRepository workflowRepo;
//
//    public WorkflowService(WorkflowRepository workflowRepo){
//        this.workflowRepo = workflowRepo;
//    }
//
//    public List<Workflow> getAllWorkflows(){
//        return workflowRepo.findAll();
//    }
//
//    public Workflow createWorkflow(Workflow workflow){
//        workflowRepo.save(workflow);
//        return workflow;
//    }
//
//    public Workflow updateWorkflow(String workflowId, Workflow workflowUpdate){
//        Optional<Workflow> workflow = workflowRepo.findById(workflowId);
//        if(workflow.isPresent()){
//            Workflow workflowOriginal = workflow.get();
////            workflowOriginal.setQuestionnaires(workflowUpdate.getQuestionnaires());
//            workflowOriginal.setVendorId(workflowUpdate.getWorkflowId());
//            workflowOriginal.setAdminId(workflowUpdate.getAdminId());
//            workflowOriginal.setVendorId(workflowUpdate.getVendorId());
//            workflowRepo.save(workflowOriginal);
//            return workflowOriginal;
//        }
//        return null;
//    }
//    
//    public Workflow deleteWorkflow(String workflowId){
//        Optional<Workflow> workflow = workflowRepo.findByWorkflowID(workflowId);
//        if(workflow.isPresent()){
//            Workflow workflowData = workflow.get();
//            workflowRepo.delete(workflowData);
//            return workflowData;
//        }
//        return null;
//    }
    
    private final WorkflowRepository workflowRepo;
    private final UserRepository userRepo;
    private final FormService formService;
    
    public WorkflowService(WorkflowRepository workflowRepo, UserRepository userRepo, FormService formService){
        this.workflowRepo = workflowRepo;
        this.userRepo = userRepo;
        this.formService = formService;
    }

    public List<Workflow> getAllWorkflows(){
        return workflowRepo.findAll();
    }

    public Optional<Workflow> getWorkflowById(String workflowId){
        Optional<Workflow> workflowData = workflowRepo.findById(workflowId);
        if(! workflowData.isPresent()){
            throw new ResourceNotFoundException("Workflow", "workflowId", workflowId);
        }
        return workflowData;
    }

    public List<Workflow> getWorkflowsForUser(){
        // get current logged in user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        Optional<User> user = userRepo.findByUsername(name);
        List<Workflow> userWorkflows = new ArrayList<>();

        if(user.isPresent()){
            List<Workflow> allWorkflows = workflowRepo.findAll();
            for(Workflow wf: allWorkflows){
                if(wf.getAssignedUsers() != null){
                    for(User eachUser: wf.getAssignedUsers()){
                        if(eachUser.getUserId().equals(user.get().getUserId())){
                            userWorkflows.add(wf);
                        }
                    }
                }
            }
        }
        return userWorkflows;
    }

    public Workflow createWorkflow(Workflow workflow) throws RequestErrorException {
        try {
            Form form = workflow.getForm();
            Form actualForm = formService.createForm(form);
            workflow.setForm(actualForm);
            workflowRepo.save(workflow);
        } catch (Exception e) {
            throw new RequestErrorException("create", "workflow", e.getMessage());
        }

        return workflow;
    }

    public Workflow updateWorkflow(String workflowId, Workflow workflowUpdate){
        Optional<Workflow> workflow = workflowRepo.findById(workflowId);
        if(workflow.isPresent()){
            Workflow workflowOriginal = workflow.get();
            
            workflowOriginal.setWorkflowId(workflowUpdate.getWorkflowId());
            workflowOriginal.setWorkflowName(workflowUpdate.getWorkflowName());
            workflowOriginal.setDeadline(workflowUpdate.getDeadline());
            workflowOriginal.setAssignedUsers(workflowUpdate.getAssignedUsers());
            workflowOriginal.setAssignedAdmins(workflowUpdate.getAssignedAdmins());
            workflowOriginal.setAssignedVendors(workflowUpdate.getAssignedVendors());
            workflowOriginal.setForm(workflowUpdate.getForm());


            
            // workflowOriginal.setAdminId(workflowUpdate.getAdminId());
            // workflowOriginal.setVendorId(workflowUpdate.getVendorId());
            workflowRepo.save(workflowOriginal);
            return workflowOriginal;
        }
        return null;
    }
    
    public Workflow deleteWorkflow(String workflowId){
        Optional<Workflow> workflow = workflowRepo.findByWorkflowId(workflowId);
        if(workflow.isPresent()){
            Workflow workflowData = workflow.get();
            workflowRepo.delete(workflowData);
            return workflowData;
        }
        return null;
    }

//    private final WorkflowRepository workflowRepo;
//
//    public WorkflowService(WorkflowRepository workflowRepo){
//        this.workflowRepo = workflowRepo;
//    }
//
//    public List<Workflow> getAllWorkflows(){
//        return workflowRepo.findAll();
//    }
//
//    public Workflow createWorkflow(Workflow workflow){
//        workflowRepo.save(workflow);
//        return workflow;
//    }
//
//    public Workflow updateWorkflow(String workflowId, Workflow workflowUpdate){
//        Optional<Workflow> workflow = workflowRepo.findById(workflowId);
//        if(workflow.isPresent()){
//            Workflow workflowOriginal = workflow.get();
////            workflowOriginal.setQuestionnaires(workflowUpdate.getQuestionnaires());
//            workflowOriginal.setVendorId(workflowUpdate.getWorkflowId());
//            workflowOriginal.setAdminId(workflowUpdate.getAdminId());
//            workflowOriginal.setVendorId(workflowUpdate.getVendorId());
//            workflowRepo.save(workflowOriginal);
//            return workflowOriginal;
//        }
//        return null;
//    }
//    
//    public Workflow deleteWorkflow(String workflowId){
//        Optional<Workflow> workflow = workflowRepo.findByWorkflowID(workflowId);
//        if(workflow.isPresent()){
//            Workflow workflowData = workflow.get();
//            workflowRepo.delete(workflowData);
//            return workflowData;
//        }
//        return null;
//    }
}
