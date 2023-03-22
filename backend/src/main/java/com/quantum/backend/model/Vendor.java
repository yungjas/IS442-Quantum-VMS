package com.quantum.backend.model;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Document("users")
@Getter
@Setter
public class Vendor extends User
{
	private String companyName;
	private String registrationNo;
	private String address;
	private String gstRegistered;
	private String telephone;
	private String fax;
//	office_address
//	office_tel
//	office_tax
//	reg_no
//	business_type
//	business_nature
//	product_serbice
//	evaluation_id

	
	public Vendor(String username, String password, String email, String companyName, String registrationNo, String address, String gstRegistered, String telephone, String fax)
	{
		super(RoleType.ROLE_VENDOR, username, password, email);
		this.companyName = companyName;
		this.registrationNo = registrationNo;
		this.address = address;
		this.gstRegistered = gstRegistered;
		this.telephone = telephone;
		this.fax = fax;
	}


	// public String getCompanyName()
	// {
	// 	return companyName;
	// }


	// public void setCompanyName(String companyName) 
	// {
	// 	this.companyName = companyName;
	// }
	
	// public String getVendorId()
	// {
	// 	return getUserId();
	// }

	@Override
	public String toString() 
	{
		return "Vendor [companyName=" + companyName + ", getUserType()="
				+ getUserType() + ", getUsername()=" + getUsername() + ", getPassword()=" + getPassword()
				+ ", getEmail()=" + getEmail() + "]";
	}
	
}
