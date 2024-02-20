package com.humanresourcemanagement.ResourceMangement.Payload.requestDto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class WeekendDto {
	
	@NotNull
	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDate weekend_date;
	
	@NotEmpty
	private String emp_id;

	public WeekendDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public WeekendDto(LocalDate weekend_date, String emp_id) {
		super();
		this.weekend_date = weekend_date;
		this.emp_id = emp_id;
	}

	public LocalDate getWeekend_date() {
		return weekend_date;
	}

	public void setWeekend_date(LocalDate weekend_date) {
		this.weekend_date = weekend_date;
	}

	public String getEmp_id() {
		return emp_id;
	}

	public void setEmp_id(String emp_id) {
		this.emp_id = emp_id;
	}
	
	
}
