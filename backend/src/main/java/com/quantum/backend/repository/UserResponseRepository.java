package com.quantum.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.quantum.backend.model.UserResponse;

@Repository
public interface UserResponseRepository extends MongoRepository<UserResponse, String>{
    @Query("{'userId':?0}")
    List<UserResponse> findResponsesByUserId(String userId);

    @Query("{'userId':?0, 'formId':?1}")
    List<UserResponse> findFormResponse(String userId, String formId);
}
