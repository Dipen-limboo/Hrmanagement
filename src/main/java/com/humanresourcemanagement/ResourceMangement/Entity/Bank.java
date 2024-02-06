package com.humanresourcemanagement.ResourceMangement.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="Tbl_Bank_Setup")
public class Bank {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="bank_id")
	private Long id;
	
	@Size(max=90)
	@Column(name="bank_name")
	private String name;
	
	@Column(name="bank_branch")
	private String branch;
	
	@Column(name="bank_account")
	private String accountNumber;

	@Column(name="bank_address")
	private String address;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="user_id")
	private User user;
	
	public Bank() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Bank(Long id, @Size(max = 90) String name, String branch, String accountNumber, String address) {
		super();
		this.id = id;
		this.name = name;
		this.branch = branch;
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

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	} 

}