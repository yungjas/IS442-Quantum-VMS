package com.quantum.backend.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quantum.backend.global.CommonFunction;
import com.quantum.backend.model.User;
import com.quantum.backend.model.Vendor;
import com.quantum.backend.service.VendorService;

@RestController
@CrossOrigin
@RequestMapping(path="api/vendors")
public class VendorController 
{
    private final VendorService vendorService;

    public VendorController(VendorService vendorService)
    {
        this.vendorService = vendorService;
    }

    @PostMapping("create")
    public Vendor createVendor(@RequestBody Vendor vendor)
    {
    	CommonFunction.printlnTime("Creating vendor");
        return vendorService.createVendor(vendor);
    }
    
    @GetMapping("all")
    public List<Vendor> getAllUsers()
    {
    	CommonFunction.printlnTime("Getting all vendor");
        return vendorService.getAllVendors();
    }

    @GetMapping("{vendorId}")
    public Optional<Vendor> getUserById(@PathVariable String vendorId)
    {
    	CommonFunction.printlnTime("Getting vendor: " + vendorId);
        return vendorService.getVendorById(vendorId);
    }    
        
    @DeleteMapping("delete/{vendorId}")
    public void deleteVendor(@PathVariable String vendorId)
    {
    	CommonFunction.printlnTime("Deleting vendor: " + vendorId);
    	
    	if(vendorService.deleteVendor(vendorId))
    	{
    		CommonFunction.printlnTime("Vendor deleted successful");
    	}
    	else
    	{
    		CommonFunction.printlnTime("Vendor deleted fail");
    	}
    }    
}
