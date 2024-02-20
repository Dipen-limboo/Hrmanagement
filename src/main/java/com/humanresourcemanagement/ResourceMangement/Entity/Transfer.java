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
@Table(name="TBL_emp_transfer")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Transfer {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="Section_id")
	private SubDepartment section;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="designation_id")
	private Designation designation;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user_id")
	private User user;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="branch_id")
	private Branch branch;
	
	@Column(name="transfer_date")
	private LocalDate transferDate;
	
	@Column(name="remarks")
	private String remark;
	
	public Transfer() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Transfer(Long id, SubDepartment section, Designation designation, User user, Branch branch) {
		super();
		this.id = id;
		this.section = section;
		this.designation = designation;
		this.user = user;
		this.branch = branch;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public SubDepartment getSection() {
		return section;
	}

	public void setSection(SubDepartment section) {
		this.section = section;
	}

	public Designation getDesignation() {
		return designation;
	}

	public void setDesignation(Designation designation) {
		this.designation = designation;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	public LocalDate getTransferDate() {
		return transferDate;
	}

	public void setTransferDate(LocalDate transferDate) {
		this.transferDate = transferDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
