package com.humanresourcemanagement.ResourceMangement.Payload.requestDto;

import java.time.LocalDate;
import java.util.Set;

import jakarta.validation.constraints.NotEmpty;

public class AdditionalDto {
	@NotEmpty
	private Set<String> type;
	
	@NotEmpty
	private String name;
	
	@NotEmpty
	private String level;
	
	
	private LocalDate joinDate;
	
	
	private LocalDate endDate;

	private Long userId;
	
	public AdditionalDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AdditionalDto(@NotEmpty Set<String> type, @NotEmpty String name, @NotEmpty String level,
			@NotEmpty LocalDate joinDate, @NotEmpty LocalDate endDate) {
		super();
		this.type = type;
		this.name = name;
		this.level = level;
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

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
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

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	
}
