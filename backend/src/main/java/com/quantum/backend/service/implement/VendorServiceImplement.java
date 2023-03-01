package com.quantum.backend.service.implement;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.quantum.backend.global.CommonFunction;
import com.quantum.backend.model.User;
import com.quantum.backend.model.Vendor;
import com.quantum.backend.repository.VendorRepository;
import com.quantum.backend.service.VendorService;

@Service
public class VendorServiceImplement implements VendorService
{
	public VendorRepository vendorRepository;
	
	public VendorServiceImplement(VendorRepository vendorRepository)
	{
		this.vendorRepository = vendorRepository;
	}

	public Vendor createVendor(Vendor vendor)
	{
		CommonFunction.println(vendor.toString());
    	vendorRepository.save(vendor);
        return vendor;
	}
	
    public List<Vendor> getAllVendors()
    {
        return vendorRepository.findAll();
    }

    public Optional<Vendor> getVendorById(String vendorId)
    {
        return vendorRepository.findById(vendorId);
    }	
	
    public boolean deleteVendor(String vendorId)
    {
        Optional<Vendor> vendor = vendorRepository.findById(vendorId);
        if(vendor.isPresent())
        {
            Vendor vendorData = vendor.get();
            
            vendorRepository.delete(vendorData);
            
            return true;
        }
        return false;
    }	
}
