package com.humanresourcemanagement.ResourceMangement.Payload.requestDto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class WeekendUpdateDto {
	@NotNull
	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDate weekend_date;
	
	@NotEmpty
	private String employee_id;

	public WeekendUpdateDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public WeekendUpdateDto(LocalDate weekend_date, String employee_id) {
		super();
		this.weekend_date = weekend_date;
		this.employee_id = employee_id;
	}

	public LocalDate getWeekend_date() {
		return weekend_date;
	}

	public void setWeekend_date(LocalDate weekend_date) {
		this.weekend_date = weekend_date;
	}

	public String getEmployee_id() {
		return employee_id;
	}

	public void setEmployee_id(String employee_id) {
		this.employee_id = employee_id;
	}
	
	
}
