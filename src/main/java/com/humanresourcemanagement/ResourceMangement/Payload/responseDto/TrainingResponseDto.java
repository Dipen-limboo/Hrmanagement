package com.humanresourcemanagement.ResourceMangement.Payload.responseDto;

import java.time.LocalDate;

public class TrainingResponseDto {
	private Long train_exp_id;
	
	private String type;
	
	private String organization;
	
	private String position;
	
	private LocalDate join_date;
	
	private LocalDate end_date;
	
	private Long user_id;

	public TrainingResponseDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TrainingResponseDto(Long train_exp_id, String type, String organization, String position,
			LocalDate join_date, LocalDate end_date, Long user_id) {
		super();
		this.train_exp_id = train_exp_id;
		this.type = type;
		this.organization = organization;
		this.position = position;
		this.join_date = join_date;
		this.end_date = end_date;
		this.user_id = user_id;
	}

	public Long getTrain_exp_id() {
		return train_exp_id;
	}

	public void setTrain_exp_id(Long train_exp_id) {
		this.train_exp_id = train_exp_id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
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

	public LocalDate getJoin_date() {
		return join_date;
	}

	public void setJoin_date(LocalDate join_date) {
		this.join_date = join_date;
	}

	public LocalDate getEnd_date() {
		return end_date;
	}

	public void setEnd_date(LocalDate end_date) {
		this.end_date = end_date;
	}

	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}
	
	
}
