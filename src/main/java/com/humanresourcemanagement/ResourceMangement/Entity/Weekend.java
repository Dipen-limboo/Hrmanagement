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
@Table(name="tbl_weekend")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Weekend {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="weekend_id")
	private Long id;
	
	@Column(name="weekend_date")
	private LocalDate date;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="emp_id")
	private EmployeeOfficialInfo employee;

	public Weekend() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Weekend(Long id, LocalDate date, EmployeeOfficialInfo employee) {
		super();
		this.id = id;
		this.date = date;
		this.employee = employee;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public EmployeeOfficialInfo getEmployee() {
		return employee;
	}

	public void setEmployee(EmployeeOfficialInfo employee) {
		this.employee = employee;
	}
	
}
