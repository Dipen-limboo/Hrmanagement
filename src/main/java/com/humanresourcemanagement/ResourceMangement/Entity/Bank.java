package com.humanresourcemanagement.ResourceMangement.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="Tbl_Bank_Setup")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Bank {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="bank_id")
	private Long id;
	
	@Size(max=90)
	@Column(name="bank_name")
	private String name;
	
	@Column(name="bank_account")
	private String accountNumber;

	@Column(name="bank_address")
	private String address;
	
	public Bank() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Bank(Long id, @Size(max = 90) String name, String accountNumber, String address) {
		super();
		this.id = id;
		this.name = name;
		this.accountNumber = accountNumber;
		this.address = address;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String holderName) {
		this.address = holderName;
	}

}