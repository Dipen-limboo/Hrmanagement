package com.humanresourcemanagement.ResourceMangement.Payload.requestDto;

import java.time.LocalDate;
import java.util.Set;

import jakarta.validation.constraints.NotEmpty;

public class TrainingDto {
	@NotEmpty
	private Set<String> type;
	
	@NotEmpty
	private String name;
	
	@NotEmpty
	private String position;
	
	private LocalDate joinDate;
	
	private LocalDate endDate;
	
	public TrainingDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TrainingDto(@NotEmpty Set<String> type, @NotEmpty String name, @NotEmpty String position, LocalDate joinDate,
			LocalDate endDate) {
		super();
		this.type = type;
		this.name = name;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
