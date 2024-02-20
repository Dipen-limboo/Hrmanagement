package com.humanresourcemanagement.ResourceMangement.Entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="Tbl_Reset_password")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ResetPassword {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="reset_id")
	private Long id;
	
	@Column(name="reset_token")
	private String token;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;
	
	@Column(name="reset_expire_date")
	private Date expireDate;

	public ResetPassword() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ResetPassword(Long id, String token, User user, Date expireDate) {
		super();
		this.id = id;
		this.token = token;
		this.user = user;
		this.expireDate = expireDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}
	
	public boolean isExpired() {
	     return expireDate.before(new Date());
	}
}