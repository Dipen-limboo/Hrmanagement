package com.humanresourcemanagement.ResourceMangement.Entity;

import java.time.LocalDate;

import com.humanresourcemanagement.ResourceMangement.Enum.Type;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="Tbl_training_experience")
public class Training {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="train_expe_id")
	private Long id;
	
	@Enumerated(EnumType.STRING)
	@Column(name="type")
	private Type type;
	
	@Column(name="organization")
	private String name;
	
	@Column(name="Position")
	private String position;
	
	@Column(name="join_date")
	private LocalDate joinDate;
	
	@Column(name="end_date")
	private LocalDate endDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="user_id")
	private User user;

	
	public Training() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Training(Long id, Type type, String name, String position, LocalDate joinDate, LocalDate endDate, User user) {
		super();
		this.id = id;
		this.type = type;
		this.name = name;
		this.position = position;
		this.joinDate = joinDate;
		this.endDate = endDate;
		this.user = user;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Type getType() {
		return type;
	}


	public void setType(Type type) {
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


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}

														
}