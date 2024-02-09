package com.humanresourcemanagement.ResourceMangement.Payload.requestDto;

import java.time.LocalDate;
import java.util.Set;

public class TrainingUpdateDto {
	private Set<String> type;
	
	private String organization;
	
	private String position;
	
	private LocalDate joinDate;
	
	private LocalDate endDate;

	public TrainingUpdateDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TrainingUpdateDto(Set<String> type, String organization, String position, LocalDate joinDate,
			LocalDate endDate) {
		super();
		this.type = type;
		this.organization = organization;
		this.position = position;
		this.joinDate = joinDate;
		this.endDate = endDate;
	}

	public Set<String> getType() {
		return type;
	}

	public void setType(Set<String> type) {
		this.type = type;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public LocalDate getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(LocalDate joinDate) {
		this.joinDate = joinDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	
	
}
