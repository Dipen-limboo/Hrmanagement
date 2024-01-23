package com.humanresourcemanagement.ResourceMangement.Entity;

import jakarta.persistence.*;

@Entity
@Table(name="departments")
public class Department {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="branch")
	private String branch;
	
	@Column(name="name")
	private String name;
	
	@Column(name="address")
	private String address;
	
	@Column(name="tel")
	private String phone;

	public Department() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Department(Long id, String branch, String name, String address, String phone) {
		super();
		this.id = id;
		this.branch = branch;
		this.name = name;
		this.address = address;
		this.phone = phone;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	
}
