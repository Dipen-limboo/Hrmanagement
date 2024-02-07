package com.humanresourcemanagement.ResourceMangement.Payload.responseDto;

public class EmpBankResponseDto {
	private Long emp_bank_id;
	private String emp_account_number;
	private String emp_bank_branch;
	private String qrPath;
	private Long user_id;
	private Long bank_id;
	private String holdername;
	public EmpBankResponseDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public EmpBankResponseDto(Long emp_bank_id, String emp_account_number, String emp_bank_branch, String qrPath,
			Long user_id, Long bank_id) {
		super();
		this.emp_bank_id = emp_bank_id;
		this.emp_account_number = emp_account_number;
		this.emp_bank_branch = emp_bank_branch;
		this.qrPath = qrPath;
		this.user_id = user_id;
		this.bank_id = bank_id;
	}
	public Long getEmp_bank_id() {
		return emp_bank_id;
	}
	public void setEmp_bank_id(Long emp_bank_id) {
		this.emp_bank_id = emp_bank_id;
	}
	public String getEmp_account_number() {
		return emp_account_number;
	}
	public void setEmp_account_number(String emp_account_number) {
		this.emp_account_number = emp_account_number;
	}
	public String getEmp_bank_branch() {
		return emp_bank_branch;
	}
	public void setEmp_bank_branch(String emp_bank_branch) {
		this.emp_bank_branch = emp_bank_branch;
	}
	public String getQrPath() {
		return qrPath;
	}
	public void setQrPath(String qrPath) {
		this.qrPath = qrPath;
	}
	public Long getUser_id() {
		return user_id;
	}
	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}
	public Long getBank_id() {
		return bank_id;
	}
	public void setBank_id(Long bank_id) {
		this.bank_id = bank_id;
	}
	public String getHoldername() {
		return holdername;
	}
	public void setHoldername(String holdername) {
		this.holdername = holdername;
	}
	
	
}
