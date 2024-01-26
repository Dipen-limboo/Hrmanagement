package com.humanresourcemanagement.ResourceMangement.Payload.responseDto;

import java.time.LocalDate;

public class DocumentResponseDto {
	private String citizenship;
	
	private String pan;
	
	private String nationalityId;
	
	private LocalDate issuedDate;
	
	private String issuedPlace;
	
	private String path;
	
	private Long userId;

	public DocumentResponseDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DocumentResponseDto(String citizenship, String pan, String nationalityId, LocalDate issuedDate,
			String issuedPlace, String path, Long userId) {
		super();
		this.citizenship = citizenship;
		this.pan = pan;
		this.nationalityId = nationalityId;
		this.issuedDate = issuedDate;
		this.issuedPlace = issuedPlace;
		this.path = path;
		this.userId = userId;
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

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	
}
