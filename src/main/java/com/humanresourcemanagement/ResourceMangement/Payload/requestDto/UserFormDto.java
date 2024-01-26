package com.humanresourcemanagement.ResourceMangement.Payload.requestDto;

import java.time.LocalDate;
import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UserFormDto {
		
	    private String firstName;

	    private String middleName;

	    
	    private String lastName;

	    
	    @Size(min = 3, max = 20)
	    private String username;
	    
	    private MultipartFile imagePath;
	    
	    private LocalDate dateOfBirth;


	    @Pattern(regexp = "^\\d{10}$",
	            message = "{The phone number must contain 10 numbers}")
	    private String phone;

	    @Size(max = 50)
	    @Email
	    private String email;

    
	    private Set<String> gender;
	    
	    private Set<String> martial;
	    
	    private String address;

		public UserFormDto() {
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

		public Set<String> getGender() {
			return gender;
		}

		public void setGender(Set<String> gender) {
			this.gender = gender;
		}

		

		public Set<String> getMartial() {
			return martial;
		}

		public void setMartial(Set<String> martial) {
			this.martial = martial;
		}

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public MultipartFile getImagePath() {
			return imagePath;
		}

		public void setImagePath(MultipartFile imagePath) {
			this.imagePath = imagePath;
		}
		
		
		
}
