package com.humanresourcemanagement.ResourceMangement.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="Tbl_emp_bank")
public class EmpBank {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="emp_bank_id")
	private Long id;
	
	@Column(name="emp_account_number")
	private String accountNumber;
	
	@Column(name="emp_bank_branch")
	private String branch;
	
	@Column(name="emp_bank_qr_path")
	private String qrPath;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user_id")
	private User user;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="bank_id")
	private Bank bank;

	public EmpBank() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EmpBank(Long id, String accountNumber, String branch, String qrPath, User user, Bank bank) {
		super();
		this.id = id;
		this.accountNumber = accountNumber;
		this.branch = branch;
		this.qrPath = qrPath;
		this.user = user;
		this.bank = bank;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getQrPath() {
		return qrPath;
	}

	public void setQrPath(String qrPath) {
		this.qrPath = qrPath;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Bank getBank() {
		return bank;
	}

	public void setBank(Bank bank) {
		this.bank = bank;
	}
	
	
}
