package com.humanresourcemanagement.ResourceMangement.Payload.responseDto;

import java.time.LocalDate;

public class EducationResponseDto {
	private Long education_id;
	
	private String educational_institue_name;

	private String board;
	
	private String level;
	
	private double gpa;
	
	private LocalDate start_date;
	
	private LocalDate end_date;
	
	private Long user_id;

	public EducationResponseDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EducationResponseDto(Long education_id, String educational_institue_name, String board, double gpa,
			LocalDate start_date, LocalDate end_date, Long user_id) {
		super();
		this.education_id = education_id;
		this.educational_institue_name = educational_institue_name;
		this.board = board;
		this.gpa = gpa;
		this.start_date = start_date;
		this.end_date = end_date;
		this.user_id = user_id;
	}

	public Long getEducation_id() {
		return education_id;
	}

	public void setEducation_id(Long education_id) {
		this.education_id = education_id;
	}

	public String getEducational_institue_name() {
		return educational_institue_name;
	}

	public void setEducational_institue_name(String educational_institue_name) {
		this.educational_institue_name = educational_institue_name;
	}

	public String getBoard() {
		return board;
	}

	public void setBoard(String board) {
		this.board = board;
	}

	public double getGpa() {
		return gpa;
	}

	public void setGpa(double gpa) {
		this.gpa = gpa;
	}

	public LocalDate getStart_date() {
		return start_date;
	}

	public void setStart_date(LocalDate start_date) {
		this.start_date = start_date;
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

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}
	
}
