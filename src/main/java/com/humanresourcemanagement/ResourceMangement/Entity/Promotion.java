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
import jakarta.persistence.Table;

@Entity
@Table(name="promotions")
public class Promotion {
	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="joinDate")
	private LocalDate joinDate;
	
	@Column(name="EndDate")
	private LocalDate endDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="user_id")
	private User user;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="approver_id")
	private User approver;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="sub_department")
	private SubDepartment subDepartment;
		
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="designation")
	private Designation designation;

	public Promotion() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Promotion(Long id, LocalDate joinDate, LocalDate endDate, User user, User approver,
			SubDepartment subDepartment, Designation designation) {
		super();
		this.id = id;
		this.joinDate = joinDate;
		this.endDate = endDate;
		this.user = user;
		this.approver = approver;
		this.subDepartment = subDepartment;
		this.designation = designation;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(LocalDate joinDate) {
		this.joinDate = joinDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getApprover() {
		return approver;
	}

	public void setApprover(User approver) {
		this.approver = approver;
	}

	public SubDepartment getSubDepartment() {
		return subDepartment;
	}

	public void setSubDepartment(SubDepartment subDepartment) {
		this.subDepartment = subDepartment;
	}

	public Designation getDesignation() {
		return designation;
	}

	public void setDesignation(Designation designation) {
		this.designation = designation;
	}
		
}