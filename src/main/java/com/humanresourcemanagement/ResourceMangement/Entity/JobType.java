package com.humanresourcemanagement.ResourceMangement.Entity;

import jakarta.persistence.*;


@Entity
@Table(name="Tbl_Job_Type")
public class JobType {

	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY)
	@Column(name="job_id")
	private Long id;
	
	@Column(name="job_type")
	private String jobType;

	public JobType() {
		super();
		// TODO Auto-generated constructor stub
	}

	public JobType(Long id, String jobType) {
		super();
		this.id = id;
		this.jobType = jobType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getJobType() {
		return jobType;
	}

	public void setJobType(String jobType) {
		this.jobType = jobType;
	}
	
	
}
