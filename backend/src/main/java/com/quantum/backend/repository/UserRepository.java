package com.quantum.backend.repository;

import com.quantum.backend.model.*;
import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.MongoRepository;

@Repository
public interface UserRepository extends MongoRepository<User, String>{
    
}
