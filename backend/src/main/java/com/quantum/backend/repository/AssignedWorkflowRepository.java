package com.quantum.backend.repository;

import com.quantum.backend.model.*;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AssignedWorkflowRepository extends MongoRepository<AssignedWorkflow, String>{
    Optional<AssignedWorkflow> findByAssignedWorkflowId(String assignedWorkflowId);
}

