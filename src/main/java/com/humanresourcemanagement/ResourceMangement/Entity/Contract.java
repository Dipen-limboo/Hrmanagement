package com.humanresourcemanagement.ResourceMangement.Entity;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
@Table(name="tbl_emp_contract")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Contract {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="contract_id")
	private Long id;
	
	@Column(name="join_date")
	private LocalDate joinDate;
	
	@Column(name="end_date")
	private LocalDate endDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="user_id")
	private User user;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="designation_id")
	private Designation designation;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="section_id")
	private SubDepartment section;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="approver_id")
	private User approver;

	@Column(name="contract_status")
	private boolean contractStatus = true;
	
	public Contract() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Contract(Long id, LocalDate joinDate, LocalDate endDate, User user, Designation designation,
			SubDepartment section, User approver) {
		super();
		this.id = id;
		this.joinDate = joinDate;
		this.endDate = endDate;
		this.user = user;
		this.designation = designation;
		this.section = section;
		this.approver = approver;
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

	public Designation getDesignation() {
		return designation;
	}

	public void setDesignation(Designation designation) {
		this.designation = designation;
	}

	public SubDepartment getSection() {
		return section;
	}

	public void setSection(SubDepartment section) {
		this.section = section;
	}

	public User getApprover() {
		return approver;
	}

	public void setApprover(User approver) {
		this.approver = approver;
	}

	public boolean isContractStatus() {
		return contractStatus;
	}

	public void setContractStatus(boolean contractStatus) {
		this.contractStatus = contractStatus;
	}
	
	
}
