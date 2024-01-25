package com.humanresourcemanagement.ResourceMangement.Entity;

import java.time.LocalDate;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.humanresourcemanagement.ResourceMangement.Enum.ERole;
import com.humanresourcemanagement.ResourceMangement.Enum.Gender;
import com.humanresourcemanagement.ResourceMangement.Enum.Martial;
import com.humanresourcemanagement.ResourceMangement.Enum.Status;

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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "users",
       uniqueConstraints = {
           @UniqueConstraint(columnNames = "username"),
           @UniqueConstraint(columnNames = "email")
       })
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank
  @Column(name="first_name")
  private String firstName;
  
  @Column(name="middle_name")
  private String middleName;
  
  @NotBlank
  @Column(name="last_name")
  private String lastName;
  
  @NotBlank
  @Size(max = 20)
  private String username;
  
  @NotBlank
  @Column(name="birth_date")
  private Date birthDate;

  @NotBlank
  @Size(max = 50)
  @Email
  private String email;

  @NotBlank
  @Size(max = 120)
  @Pattern(regexp="^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$")
  private String password;
  
  @Transient
  private String ConfirmPassword;
  
  @Column(name= "phone")
  private String phone;
  
  @Column(name="address")
  private String address;
  
  @Enumerated(EnumType.STRING)
  @Column(name="role")
  private ERole role = ERole.ROLE_EMPLOYEE;
  
  @Column(name="join_date")
  private LocalDate joinDate;
	
  @Column(name="leave_date")
  private LocalDate leaveDate;
	
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name="department")
  private Department department;
	
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name="designation")
  private Designation designation;
	
  @Column(name="approvedBy")
  private Long approver; 
 
  @Column(name="is_verified")
  private boolean isVerified =false;
  
  @Column(name="verified_date")
  @JsonFormat(pattern = "yyyy/MM/dd")
  private Date verifiedDate;
  
  @Column(name="verified_token")
  private String verifiedToken;
  
  @Enumerated(EnumType.STRING)
  @Column(name="status")
  private Status status = Status.ACTIVE;  
  
  @Enumerated(EnumType.STRING)
  @Column(name="gender")
  private Gender gender;
  
  @Enumerated(EnumType.STRING)
  @Column(name="martial_status")
  private Martial martialStatus;
  
  @Column(name="is_password_change")
  private boolean passwordChange = false;
  
  public User() {
  }

  public User(String username, String email, String password) {
    this.username = username;
    this.email = email;
    this.password = password;
  }

  public Long getId() {
		return id;
  }
	
  public void setId(Long id) {
		this.id = id;
  }
	
  public String getUsername() {
	  return username;
  }

  public void setUsername(String username) {
	  this.username = username;
  }

  public String getEmail() {
	  return email;
  }

  public void setEmail(String email) {
	  this.email = email;
  } 

  public String getPassword() {
	  return password;
  }

  public void setPassword(String password) {
	  this.password = password;
  }


  public ERole getRole() {
	  return role;
  }

  public void setRole(ERole role) {
	  this.role = role;
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
	  return lastName;
  }

  public void setLastName(String lastName) {
	  this.lastName = lastName;
  }

  public String getConfirmPassword() {
	  return ConfirmPassword;
  }

  public void setConfirmPassword(String confirmPassword) {
	  ConfirmPassword = confirmPassword;
  }

  public String getPhone() {
	  return phone;
  }

  public void setPhone(String phone) {
	  this.phone = phone;
  }

  public Date getBirthDate() {
	  return birthDate;
  }

  public void setBirthDate(Date birthDate) {
	  this.birthDate = birthDate;
  }

  public boolean isVerified() {
	  return isVerified;
  }

  public void setVerified(boolean isVerified) {
	  this.isVerified = isVerified;
  }

  public Date getVerifiedDate() {
	  return verifiedDate;
  }

  public void setVerifiedDate(Date verifiedDate) {
	  this.verifiedDate = verifiedDate;
  }

  public String getVerifiedToken() {
	  return verifiedToken;
  }

  public void setVerifiedToken(String verifiedToken) {
	  this.verifiedToken = verifiedToken;
  }

  public Status getStatus() {
	  return status;
  }

  public void setStatus(Status status) {
	  this.status = status;
  }

  public Gender getGender() {
	  return gender;
  }
	
  public void setGender(Gender gender) {
	  this.gender = gender;
  }
	
  public Martial getMartialStatus() {
	  return martialStatus;
  }

  public void setMartialStatus(Martial martialStatus) {
	  this.martialStatus = martialStatus;
  }

  public String getAddress() {
	  return address;
  }

  public void setAddress(String address) {
	  this.address = address;
  }

  public LocalDate getJoinDate() {
 	  return joinDate;
  }

  public void setJoinDate(LocalDate joinDate) {
	  this.joinDate = joinDate;
  }

  public LocalDate getLeaveDate() {
	  return leaveDate;
  }

  public void setLeaveDate(LocalDate leaveDate) {
	  this.leaveDate = leaveDate;
  }

  public Department getDepartment() {
	  return department;
  }

  public void setDepartment(Department department) {
	  this.department = department;
  }

  public Designation getDesignation() {
	  return designation;
 } 

  public void setDesignation(Designation designation) {
	  this.designation = designation;
  }

  public Long getApprover() {
	  return approver;
  }

  public void setApprover(Long approver) {
	  this.approver = approver;
  }

  public boolean isPasswordChange() {
	  return passwordChange;
  }

  public void setPasswordChange(boolean passwordChange) {
	  this.passwordChange = passwordChange;
  }
    
}