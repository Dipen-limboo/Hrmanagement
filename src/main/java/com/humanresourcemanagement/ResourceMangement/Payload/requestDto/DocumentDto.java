package com.humanresourcemanagement.ResourceMangement.Payload.requestDto;

import java.time.LocalDate;

import org.springframework.web.multipart.MultipartFile;

public class DocumentDto {
	private String citizenship;
	
	private String pan;
	
	private String nationalityId;
	
	private LocalDate issuedDate;
	
	private String issuedPlace;
	
	private MultipartFile file;

	public DocumentDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DocumentDto(String citizenship, String pan, String nationalityId, LocalDate issuedDate, String issuedPlace,
			MultipartFile file) {
		super();
		this.citizenship = citizenship;
		this.pan = pan;
		this.nationalityId = nationalityId;
		this.issuedDate = issuedDate;
		this.issuedPlace = issuedPlace;
		this.file = file;
	}

	public String getCitizenship() {
		return citizenship;
	}

	public void setCitizenship(String citizenship) {
		this.citizenship = citizenship;
	}

	public String getPan() {
		return pan;
	}

	public void setPan(String pan) {
		this.pan = pan;
	}

	public String getNationalityId() {
		return nationalityId;
	}

	public void setNationalityId(String nationalityId) {
		this.nationalityId = nationalityId;
	}

	public LocalDate getIssuedDate() {
		return issuedDate;
	}

	public void setIssuedDate(LocalDate issuedDate) {
		this.issuedDate = issuedDate;
	}

	public String getIssuedPlace() {
		return issuedPlace;
	}

	public void setIssuedPlace(String issuedPlace) {
		this.issuedPlace = issuedPlace;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}
	
	
}
