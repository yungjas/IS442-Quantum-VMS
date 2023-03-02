package com.quantum.backend.repository;
import com.quantum.backend.model.*;
import java.util.*;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FormRepository extends MongoRepository<Form, String>{
    Optional<Form> findByFormId(String formId);
}
