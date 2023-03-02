package com.quantum.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.quantum.backend.model.Vendor;

@Service
public interface VendorService 
{
	public Vendor createVendor(Vendor vendor);
	public List<Vendor> getAllVendors();
    public Optional<Vendor> getVendorById(String vendorId);
    public Vendor updateVendor(String vendorId, Vendor tempVendor);
	public boolean deleteVendor(String vendorId);
}
