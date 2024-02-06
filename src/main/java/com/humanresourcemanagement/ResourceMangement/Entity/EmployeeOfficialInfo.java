package com.humanresourcemanagement.ResourceMangement.Entity;

import jakarta.persistence.*;

@Entity
@Table(name="Tbl_Emp_offi_info")
public class EmployeeOfficialInfo {
	
	@Id
	@Column(name="Emp_off_id")
	private String id;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="Section_id")
	private SubDepartment section;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="designation_id")
	private Designation designation;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="working_type_id")
	private WorkingType workingType;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="job_type_id")
	private JobType jobType;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user_id")
	private User user;

	public EmployeeOfficialInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EmployeeOfficialInfo(String id, SubDepartment section, Designation designation, WorkingType workingType,
			JobType jobType, User emp) {
		super();
		this.id = id;
		this.section = section;
		this.designation = designation;
		this.workingType = workingType;
		this.jobType = jobType;
		this.user = emp;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
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

	public WorkingType getWorkingType() {
		return workingType;
	}

	public void setWorkingType(WorkingType workingType) {
		this.workingType = workingType;
	}

	public JobType getJobType() {
		return jobType;
	}

	public void setJobType(JobType jobType) {
		this.jobType = jobType;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User emp) {
		this.user = emp;
	}
	
	
	
}
