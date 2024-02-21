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
@Table(name="tbl_shift_assign")
public class ShiftAssign {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="shift_assign_id")
	private Long id;
	
	@Column(name="day")
	private String day;
	
	@Column(name="shift_date")
	private LocalDate shiftDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="emp_id")
	private EmployeeOfficialInfo employee;

	public ShiftAssign() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ShiftAssign(Long id, String day, LocalDate shiftDate, EmployeeOfficialInfo employee) {
		super();
		this.id = id;
		this.day = day;
		this.shiftDate = shiftDate;
		this.employee = employee;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public LocalDate getShiftDate() {
		return shiftDate;
	}

	public void setShiftDate(LocalDate shiftDate) {
		this.shiftDate = shiftDate;
	}

	public EmployeeOfficialInfo getEmployee() {
		return employee;
	}

	public void setEmployee(EmployeeOfficialInfo employee) {
		this.employee = employee;
	}
	
	
}
