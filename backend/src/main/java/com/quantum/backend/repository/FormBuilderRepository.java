package com.quantum.backend.repository;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.quantum.backend.model.Question;

@Repository
public interface FormBuilderRepository extends MongoRepository<Question, String>{
    Optional<Question> findByQuestionId(String questionId);
}

