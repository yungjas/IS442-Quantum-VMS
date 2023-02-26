package com.quantum.backend.repository;

import com.quantum.backend.model.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

@Repository
public interface UserRepository extends MongoRepository<User, String>{
    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
}
