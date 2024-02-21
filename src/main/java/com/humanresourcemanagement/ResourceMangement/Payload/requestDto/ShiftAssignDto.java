package com.humanresourcemanagement.ResourceMangement.Payload.requestDto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class ShiftAssignDto {
	
	@NotNull
	private LocalDate shift_date;
	
	@NotEmpty
	private String employee_id;

	public ShiftAssignDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ShiftAssignDto(@NotNull LocalDate shift_date, @NotEmpty String employee_id) {
		super();
		this.shift_date = shift_date;
		this.employee_id = employee_id;
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
