package com.quantum.backend.repository;

import com.quantum.backend.model.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

@Repository
public interface UserRepository extends MongoRepository<User, String>{
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);
    
    Boolean existsByUsername(String username);
    
    Boolean existsByEmail(String email);
    
    @Query("{'userType':?0}")
    List<User> findByUserType(String role);
}
