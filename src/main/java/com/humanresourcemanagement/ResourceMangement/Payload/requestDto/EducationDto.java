package com.humanresourcemanagement.ResourceMangement.Payload.requestDto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class EducationDto {
	@NotEmpty
	private String educational_institue_name;
	
	@NotEmpty
	private String board;
	
	private double gpa = 0.00;
	
	@NotEmpty
	private String level;
	
	private LocalDate start_date;
	
	private LocalDate end_date;

	public EducationDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EducationDto(@NotEmpty String educational_institue_name, @NotEmpty String board, @NotNull double gpa,
			LocalDate start_date, LocalDate end_date) {
		super();
		this.educational_institue_name = educational_institue_name;
		this.board = board;
		this.gpa = gpa;
		this.start_date = start_date;
		this.end_date = end_date;
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

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}
	
	
}
