package com.quantum.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.quantum.model.User;

public interface UserRepo extends MongoRepository<User, String>
{
    @Query("{name:'?0'}")
    User findUserByName(String name);
    
    @Query(value="{id:'?0'}", fields="{'id' : 1, 'name' : 1}")
    List<User> findAll(String id);
    
    public long count();
}
