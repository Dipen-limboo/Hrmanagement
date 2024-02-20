package com.humanresourcemanagement.ResourceMangement.Entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.humanresourcemanagement.ResourceMangement.Enum.Relation;

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
@Table(name="Tbl_Emp_Family_info")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class FamilyInfo {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="family_infos_id")
	private Long id;
	
	@Column(name="family_first_name")
	private String firstName;
	
	@Column(name="family_middle_name")
	private String middleName;
	
	@Column(name="family_last_name")
	private String LastName;
	
	@Enumerated(EnumType.STRING)
	@Column(name= "relationship")
	private Relation relation;
	
	@Column(name="family_phone")
	private String phone;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="user_id")
	private User user;
	
	@Column(name="family_image_path")
	private String file;

	public FamilyInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public FamilyInfo(Long id, String firstName, String middleName, String lastName, Relation relation, String phone,
			User user) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.middleName = middleName;
		LastName = lastName;
		this.relation = relation;
		this.phone = phone;
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return LastName;
	}

	public void setLastName(String lastName) {
		LastName = lastName;
	}

	public Relation getRelation() {
		return relation;
	}

	public void setRelation(Relation relation) {
		this.relation = relation;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}
	

}