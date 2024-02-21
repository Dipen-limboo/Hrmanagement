package com.humanresourcemanagement.ResourceMangement.Payload.responseDto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotEmpty;

public class ShiftAssignResponseDto {
	
	private Long shift_assign_id;
	
	private String day;
	
	private LocalDate shift_date;
	
	private String employee_id;

	public ShiftAssignResponseDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ShiftAssignResponseDto(Long shift_assign_id, String day, LocalDate shift_date, String employee_id) {
		super();
		this.shift_assign_id = shift_assign_id;
		this.day = day;
		this.shift_date = shift_date;
		this.employee_id = employee_id;
	}

	public Long getShift_assign_id() {
		return shift_assign_id;
	}

	public void setShift_assign_id(Long shift_assign_id) {
		this.shift_assign_id = shift_assign_id;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public LocalDate getShift_date() {
		return shift_date;
	}

	public void setShift_date(LocalDate shift_date) {
		this.shift_date = shift_date;
	}

	public String getEmployee_id() {
		return employee_id;
	}

	public void setEmployee_id(String employee_id) {
		this.employee_id = employee_id;
	}
	
	
}
