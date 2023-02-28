package com.quantum.backend.service;

import org.springframework.stereotype.Service;

import com.quantum.backend.model.Vendor;

@Service
public interface VendorService 
{
	public Vendor createVendor(Vendor vendor);
}
