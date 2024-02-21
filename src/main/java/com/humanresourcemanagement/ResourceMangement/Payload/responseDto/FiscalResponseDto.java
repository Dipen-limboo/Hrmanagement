package com.humanresourcemanagement.ResourceMangement.Payload.responseDto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;

public class FiscalResponseDto {
	private Long fiscal_id;
	
	private String year;
	
	private LocalDate from_date;
	
	private LocalDate to_date;
	
	private Boolean status;

	public FiscalResponseDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public FiscalResponseDto(Long fiscal_id, String year, LocalDate from_date, LocalDate to_date, Boolean status) {
		super();
		this.fiscal_id = fiscal_id;
		this.year = year;
		this.from_date = from_date;
		this.to_date = to_date;
		this.status = status;
	}

	public Long getFiscal_id() {
		return fiscal_id;
	}

	public void setFiscal_id(Long fiscal_id) {
		this.fiscal_id = fiscal_id;
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

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}
	
	

}
