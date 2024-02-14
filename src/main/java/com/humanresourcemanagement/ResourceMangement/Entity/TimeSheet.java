package com.humanresourcemanagement.ResourceMangement.Entity;

import java.time.LocalTime;

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
@Table(name="Tbl_timesheet")
public class TimeSheet {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="timesheet_id")
	private Long id;
	
	@Column(name="starting_time")
	private LocalTime startTime;
	
	@Column(name="ending_time")
	private LocalTime endTime;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="organization_id")
	private Organization organization;

	public TimeSheet() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TimeSheet(Long id, LocalTime startTime, LocalTime endTime, Organization organization) {
		super();
		this.id = id;
		this.startTime = startTime;
		this.endTime = endTime;
		this.organization = organization;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}

	public LocalTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

}
