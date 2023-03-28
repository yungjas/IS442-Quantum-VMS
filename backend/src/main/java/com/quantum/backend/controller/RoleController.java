package com.quantum.backend.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quantum.backend.exception.ResourceNotFoundException;
import com.quantum.backend.model.User;
import com.quantum.backend.model.Vendor;
import com.quantum.backend.service.RoleService;
import com.quantum.backend.service.UserService;

@RestController
@CrossOrigin
@RequestMapping(path="api/roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService){
        this.roleService = roleService;
    }

    @GetMapping("user")
    public ResponseEntity<List<String>> getApproverAttribute()
    {    	
        List<String> userAttribute = getUserAttribute();
        if(userAttribute.size() == 0){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(userAttribute, HttpStatus.OK);
    }
    
    @GetMapping("vendor")
    public ResponseEntity<List<String>> getAdminAttribute()
    {
    	List<String> vendorAttribute = getUserAttribute();
        vendorAttribute.addAll(roleService.getFields(new Vendor(null, null, null, null, null, null, null, null, null)));
        
        if(vendorAttribute.size() == 0){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(vendorAttribute, HttpStatus.OK);
    }
    
    public List<String> getUserAttribute()
    {
    	return roleService.getFields(new User(null, null, null, null));
    }
}
