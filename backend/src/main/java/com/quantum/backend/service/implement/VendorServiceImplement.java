package com.quantum.backend.service.implement;

import org.springframework.stereotype.Service;

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
		System.out.println(vendor.toString());
    	vendorRepository.save(vendor);
        return vendor;
	}
}
