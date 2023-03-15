package com.quantum.backend.repository;

import com.quantum.backend.model.*;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkflowRepository extends MongoRepository<Workflow, String>{
    Optional<Workflow> findByWorkflowId(String workflowId);

}
