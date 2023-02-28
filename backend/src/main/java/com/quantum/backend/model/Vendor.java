package com.quantum.backend.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document("vendor")
public class Vendor extends User
{
	private String companyName;
//	office_address
//	office_tel
//	office_tax
//	reg_no
//	business_type
//	business_nature
//	product_serbice
//	evaluation_id

	
	public Vendor(RoleType userType, String username, String password, String email, String companyName)
	{
		super(userType, username, password, email);
		this.companyName = companyName;
	}


	public String getCompanyName()
	{
		return companyName;
	}


	public void setCompanyName(String companyName) 
	{
		this.companyName = companyName;
	}
	
	public String getVendorId()
	{
		return getUserId();
	}
	
}
