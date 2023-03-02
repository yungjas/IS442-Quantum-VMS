package com.quantum.backend.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.quantum.backend.model.Vendor;

@Repository
public interface VendorRepository extends MongoRepository<Vendor, String>
{
    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}
