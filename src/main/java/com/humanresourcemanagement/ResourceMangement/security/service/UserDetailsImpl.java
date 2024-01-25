package com.humanresourcemanagement.ResourceMangement.security.service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.humanresourcemanagement.ResourceMangement.Entity.User;
import com.humanresourcemanagement.ResourceMangement.Enum.Gender;
import com.humanresourcemanagement.ResourceMangement.Enum.Martial;
import com.humanresourcemanagement.ResourceMangement.Enum.Status;

public class UserDetailsImpl implements UserDetails {
	  private static final long serialVersionUID = 1L;
	  private Long id;
	  private String username;
	  private String email;
	  @JsonIgnore
	  private String password;
	  private boolean isVerified;
	  private boolean isChanged;
	  private Status status;
	  private String firstname;
	  private String middlename;
	  private String lastname;
	  private LocalDate dateOfbirth;
	  private String phone;
	  private Gender gender;
	  private Martial martial;
	  private Collection<? extends GrantedAuthority> authorities;
	  

	  

	  public UserDetailsImpl(Long id, String username, String email, String password, boolean isVerified, boolean isChanged, Status status,
			String firstname, String middlename, String lastname, LocalDate dateOfbirth, String phone, Gender gender,
			Martial martial, Collection<? extends GrantedAuthority> authorities) {
		super();
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
		this.isVerified = isVerified;
		this.isChanged = isChanged;
		this.status = status;
		this.firstname = firstname;
		this.middlename = middlename;
		this.lastname = lastname;
		this.dateOfbirth = dateOfbirth;
		this.phone = phone;
		this.gender = gender;
		this.martial = martial;
		this.authorities = authorities;
	}

	public static UserDetailsImpl build(User user) {
		  List<GrantedAuthority> authorities = Collections.singletonList(
			        new SimpleGrantedAuthority(user.getRole().name())
			    );

	    return new UserDetailsImpl(
	        user.getId(), 
	        user.getUsername(), 
	        user.getEmail(),
	        user.getPassword(), 
	        user.isVerified(),
	        user.isPasswordChange(),
	        user.getStatus(),
	        user.getFirstName(),
	        user.getMiddleName(),
	        user.getLastName(),
	        user.getBirthDate(),
	        user.getPhone(),
	        user.getGender(),
	        user.getMartialStatus(),
	        authorities);
	  }

	  @Override
	  public Collection<? extends GrantedAuthority> getAuthorities() {
	    return authorities;
	  }

	  public Long getId() {
	    return id;
	  }

	  public String getEmail() {
	    return email;
	  }
	  
	  
	  public String getFirstname() {
		  return firstname;
	  }
	  
	  public Status getStatus() {
		  return status;
	  }
	
	  public String getMiddlename() {
		  return middlename;
	  }
	
	  public String getLastname() {
		  return lastname;
	  }
	
	  public LocalDate getDateOfbirth() {
		  return dateOfbirth;
	  }
	
	  public String getPhone() {
		  return phone;
	  }
	
	  public boolean isVerified() {
		  return true;
	  }
	  
	  public boolean isChanged() {
		return isChanged;
	  }

	  public Gender getGender() {
		  return gender;
	  }

	  public Martial getMartial() {
		  return martial;
	  }

	  @Override
	  public String getPassword() {
	    return password;
	  }

	  @Override
	  public String getUsername() {
	    return email;
	  }

	  @Override
	  public boolean isAccountNonExpired() {
	    return true;
	  }

	  @Override
	  public boolean isAccountNonLocked() {
	    return true;
	  }

	  @Override
	  public boolean isCredentialsNonExpired() {
	    return true;
	  }

	  @Override
	  public boolean isEnabled() {
	    return true;
	  }

	  @Override
	  public boolean equals(Object o) {
	    if (this == o)
	      return true;
	    if (o == null || getClass() != o.getClass())
	      return false;
	    UserDetailsImpl user = (UserDetailsImpl) o;
	    return Objects.equals(id, user.id);
	  }
	
}
