package com.humanresourcemanagement.ResourceMangement.Entity;

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
@Table(name="family_infos")
public class FamilyInfo {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="first_name")
	private String firstName;
	
	@Column(name="middle_name")
	private String middleName;
	
	@Column(name="last_name")
	private String LastName;
	
	@Enumerated(EnumType.STRING)
	@Column(name= "relationship")
	private Relation relation;
	
	@Column(name="phone")
	private String phone;

	public FamilyInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public FamilyInfo(Long id, String firstName, String middleName, String lastName, Relation relation, String phone) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.middleName = middleName;
		LastName = lastName;
		this.relation = relation;
		this.phone = phone;
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
}
