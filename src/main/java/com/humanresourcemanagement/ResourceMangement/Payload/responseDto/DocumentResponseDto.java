package com.humanresourcemanagement.ResourceMangement.Payload.responseDto;

import java.time.LocalDate;
import java.util.Set;

public class DocumentResponseDto {
	private Long document_id;
	
	private String type;
	
	private String id_number;
	
	private LocalDate issued_date;
	
	private LocalDate exprity_date;
	
	private String path;
	
	private Long userId;

	public DocumentResponseDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DocumentResponseDto(Long document_id, String type, String id_number, LocalDate issued_date,
			LocalDate exprity_date, String path, Long userId) {
		super();
		this.document_id = document_id;
		this.type = type;
		this.id_number = id_number;
		this.issued_date = issued_date;
		this.exprity_date = exprity_date;
		this.path = path;
		this.userId = userId;
	}

	public Long getDocument_id() {
		return document_id;
	}

	public void setDocument_id(Long document_id) {
		this.document_id = document_id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getId_number() {
		return id_number;
	}

	public void setId_number(String id_number) {
		this.id_number = id_number;
	}

	public LocalDate getIssued_date() {
		return issued_date;
	}

	public void setIssued_date(LocalDate issued_date) {
		this.issued_date = issued_date;
	}

	public LocalDate getExprity_date() {
		return exprity_date;
	}

	public void setExprity_date(LocalDate exprity_date) {
		this.exprity_date = exprity_date;
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
