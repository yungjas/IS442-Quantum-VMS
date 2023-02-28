package com.quantum.backend.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    	System.out.println("Creating vendor");
        return vendorService.createVendor(vendor);
    }
}
