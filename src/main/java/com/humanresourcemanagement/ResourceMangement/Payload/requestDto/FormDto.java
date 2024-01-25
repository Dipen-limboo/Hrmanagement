package com.humanresourcemanagement.ResourceMangement.Payload.requestDto;

import java.time.LocalDate;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class FormDto {
		@NotBlank
	    private String firstName;

	    private String middleName;

	    @NotBlank
	    private String lastName;

	    @NotBlank
	    @Size(min = 3, max = 20)
	    private String username;
	    
	    @NotNull
	    @JsonFormat(pattern="yyyy/mm/dd")
	    private LocalDate dateOfBirth;

	    @NotBlank
	    @Pattern(regexp = "^\\d{10}$",
	            message = "{The phone number must contain 10 numbers}")
	    private String phone;

	    @NotBlank
	    @Size(max = 50)
	    @Email
	    private String email;

	    private Set<String> role;
	    
	    private Set<String> gender;
	    
	    private Set<String> marital;
	    
	    private String address;

	    @NotBlank
	    @Size(min = 6, max = 40, message = "Password length should be between 6 and 50 ")
	    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$")
	    private String password;

	    @NotBlank
	    private String confirmPassword;


	    @AssertTrue(message = "Passwords do not match")
	    private boolean isPasswordMatching() {
	        return password.equals(confirmPassword);
	    }

		public FormDto() {
			super();
			// TODO Auto-generated constructor stub
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

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public LocalDate getDateOfBirth() {
			return dateOfBirth;
		}

		public void setDateOfBirth(LocalDate dateOfBirth) {
			this.dateOfBirth = dateOfBirth;
		}

		public String getPhone() {
			return phone;
		}

		public void setPhone(String phone) {
			this.phone = phone;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public Set<String> getRole() {
			return role;
		}

		public void setRole(Set<String> role) {
			this.role = role;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public String getConfirmPassword() {
			return confirmPassword;
		}

		public void setConfirmPassword(String confirmPassword) {
			this.confirmPassword = confirmPassword;
		}

		public Set<String> getGender() {
			return gender;
		}

		public void setGender(Set<String> gender) {
			this.gender = gender;
		}

		public Set<String> getMaritalStatus() {
			return marital;
		}

		public void setMaritalStatus(Set<String> maritalStatus) {
			this.marital = maritalStatus;
		}

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}
		
}
