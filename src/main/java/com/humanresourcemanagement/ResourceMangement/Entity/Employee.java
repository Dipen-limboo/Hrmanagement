package com.humanresourcemanagement.ResourceMangement.Entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="employees")
public class Employee {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="employee_name")
	private User username; 
	
	@Column(name="join_date")
	private LocalDate joinDate;
	
	@Column(name="leave_date")
	private LocalDate leaveDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="department")
	private Department department;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="designation")
	private Designation designation;
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="approvedBy")
	private User approver; 

	public Employee() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Employee(Long id, User username, LocalDate joinDate, LocalDate leaveDate, Department department,
			Designation designation, User approver) {
		super();
		this.id = id;
		this.username = username;
		this.joinDate = joinDate;
		this.leaveDate = leaveDate;
		this.department = department;
		this.designation = designation;
		this.approver = approver;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUsername() {
		return username;
	}

	public void setUsername(User username) {
		this.username = username;
	}

	public LocalDate getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(LocalDate joinDate) {
		this.joinDate = joinDate;
	}

	public LocalDate getLeaveDate() {
		return leaveDate;
	}

	public void setLeaveDate(LocalDate leaveDate) {
		this.leaveDate = leaveDate;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Designation getDesignation() {
		return designation;
	}

	public void setDesignation(Designation designation) {
		this.designation = designation;
	}
		
}
