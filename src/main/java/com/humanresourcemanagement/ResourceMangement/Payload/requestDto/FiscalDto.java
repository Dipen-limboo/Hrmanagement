package com.humanresourcemanagement.ResourceMangement.Payload.requestDto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class FiscalDto {
	@NotEmpty
	private String year;
	
	@NotNull
	private LocalDate from_date;
	
	@NotNull
	private LocalDate to_date;
	
	@NotNull
	private boolean status;

	public FiscalDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public FiscalDto(@NotEmpty String year, @NotNull LocalDate from_date, @NotNull LocalDate to_date,
			@NotNull boolean status) {
		super();
		this.year = year;
		this.from_date = from_date;
		this.to_date = to_date;
		this.status = status;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public LocalDate getFrom_date() {
		return from_date;
	}

	public void setFrom_date(LocalDate from_date) {
		this.from_date = from_date;
	}

	public LocalDate getTo_date() {
		return to_date;
	}

	public void setTo_date(LocalDate to_date) {
		this.to_date = to_date;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	
}
