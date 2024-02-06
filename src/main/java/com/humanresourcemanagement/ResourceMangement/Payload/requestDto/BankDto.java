package com.humanresourcemanagement.ResourceMangement.Payload.requestDto;

import jakarta.validation.constraints.NotEmpty;

public class BankDto {
	@NotEmpty
	private String name;
	
	@NotEmpty
	private String branch;
	
	@NotEmpty
	private String account;
	
	@NotEmpty
	private String address;

	private long user_id;
	
	public BankDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BankDto(@NotEmpty String name, @NotEmpty String branch, @NotEmpty String account,
			@NotEmpty String address) {
		super();
		this.name = name;
		this.branch = branch;
		this.account = account;
		this.address = address;
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

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public long getUser_id() {
		return user_id;
	}

	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}
	
	
}
