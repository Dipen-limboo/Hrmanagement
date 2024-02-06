package com.humanresourcemanagement.ResourceMangement.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="Tbl_working_type")
public class WorkingType {
	
	
	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY)
	@Column(name="working_id")
	private Long id;
	
	@Column(name="working_type")
	private String workingType;

	public WorkingType() {
		super();
		// TODO Auto-generated constructor stub
	}

	public WorkingType(Long id, String workingType) {
		super();
		this.id = id;
		this.workingType = workingType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getWorkingType() {
		return workingType;
	}

	public void setWorkingType(String workingType) {
		this.workingType = workingType;
	}
	
	
}
