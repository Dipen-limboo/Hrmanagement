package com.humanresourcemanagement.ResourceMangement.Entity;

import java.time.LocalDate;

import com.humanresourcemanagement.ResourceMangement.Enum.DocumentType;

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
@Table(name="Tbl_Document")
public class Document {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="document_id")
	private Long id;
	
	@Enumerated(EnumType.STRING)
	@Column(name="document_type")
	private DocumentType type;
	
	@Column(name="id_number")
	private String number;
		
	@Column(name="issued_date")
	private LocalDate issuedDate;
	
	@Column(name="expiry_date")
	private LocalDate expiryDate;
	
	@Column(name="document_filePath")
	private String filePath;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user_id")
	private User user;
	
	public Document() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Document(Long id, DocumentType type, String number, LocalDate issuedDate, LocalDate expiryDate,
			String filePath, User user) {
		super();
		this.id = id;
		this.type = type;
		this.number = number;
		this.issuedDate = issuedDate;
		this.expiryDate = expiryDate;
		this.filePath = filePath;
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public DocumentType getType() {
		return type;
	}

	public void setType(DocumentType type) {
		this.type = type;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public LocalDate getIssuedDate() {
		return issuedDate;
	}

	public void setIssuedDate(LocalDate issuedDate) {
		this.issuedDate = issuedDate;
	}

	public LocalDate getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(LocalDate expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	
}	
	