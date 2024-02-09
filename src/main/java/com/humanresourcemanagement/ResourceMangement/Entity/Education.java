package com.humanresourcemanagement.ResourceMangement.Entity;

import java.time.LocalDate;

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
@Table(name="Tbl_education")
public class Education {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="education_id")
	private Long id;
	
	@Column(name="educational_institution")
	private String name;
	
	@Column(name="educational_level")
	private String level;
	
	@Column(name="educational_board")
	private String board;
	
	@Column(name="start_date")
	private LocalDate startDate;
	
	@Column(name="end_date")
	private LocalDate endDate;
	
	@Column(name="GPA")
	private double gpa;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="user_id")
	private User user;

	public Education() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Education(Long id, String name, String board, LocalDate startDate, LocalDate endDate, double gpa,
			User user) {
		super();
		this.id = id;
		this.name = name;
		this.board = board;
		this.startDate = startDate;
		this.endDate = endDate;
		this.gpa = gpa;
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBoard() {
		return board;
	}

	public void setBoard(String board) {
		this.board = board;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public double getGpa() {
		return gpa;
	}

	public void setGpa(double gpa) {
		this.gpa = gpa;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}
	
	
}
