package com.humanresourcemanagement.ResourceMangement.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="banks")
public class Bank {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Size(max=90)
	@Column(name="name")
	private String name;
	
	@Column(name="branch")
	private String branch;
	
	@Column(name="account")
	private String accountNumber;

	@Column(name="holder_name")
	private String holderName;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="user_id")
	private User user;
	
	public Bank() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Bank(Long id, @Size(max = 90) String name, String branch, String accountNumber, String holderName) {
		super();
		this.id = id;
		this.name = name;
		this.branch = branch;
		this.accountNumber = accountNumber;
		this.holderName = holderName;
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

	public String getHolderName() {
		return holderName;
	}

	public void setHolderName(String holderName) {
		this.holderName = holderName;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	} 

}
