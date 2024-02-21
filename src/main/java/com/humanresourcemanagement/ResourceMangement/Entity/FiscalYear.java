package com.humanresourcemanagement.ResourceMangement.Entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="tbl_fiscal_year")
public class FiscalYear {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="fiscal_id")
	private Long id;
	
	@Column(name="year")
	private String year;
	
	@Column(name="from_date")
	private LocalDate fromDate;
	
	@Column(name="to_date")
	private LocalDate toDate;
	
	@Column(name="status")
	private Boolean status;

	public FiscalYear() {
		super();
		// TODO Auto-generated constructor stub
	}

	public FiscalYear(Long id, String year, LocalDate fromDate, LocalDate toDate, Boolean status) {
		super();
		this.id = id;
		this.year = year;
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public LocalDate getFromDate() {
		return fromDate;
	}

	public void setFromDate(LocalDate fromDate) {
		this.fromDate = fromDate;
	}

	public LocalDate getToDate() {
		return toDate;
	}

	public void setToDate(LocalDate toDate) {
		this.toDate = toDate;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}
	
	
	
	
}
