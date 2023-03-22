package com.quantum.backend.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.quantum.backend.model.UserResponse;

@Repository
public interface UserResponseRepository extends MongoRepository<UserResponse, String>{

}
