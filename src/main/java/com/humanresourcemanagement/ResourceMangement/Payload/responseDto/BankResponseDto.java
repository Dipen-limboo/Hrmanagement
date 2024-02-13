package com.humanresourcemanagement.ResourceMangement.Payload.responseDto;

public class BankResponseDto {
	private Long bank_id;
	
	private String bank_name;
	
	private String bank_address;
	
	private String account_number;

	public BankResponseDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BankResponseDto(Long bank_id, String bank_name, String bank_address, String account_number) {
		super();
		this.bank_id = bank_id;
		this.bank_name = bank_name;
		this.bank_address = bank_address;
		this.account_number = account_number;
	}

	public Long getBank_id() {
		return bank_id;
	}

	public void setBank_id(Long bank_id) {
		this.bank_id = bank_id;
	}

	public String getBank_name() {
		return bank_name;
	}

	public void setBank_name(String bank_name) {
		this.bank_name = bank_name;
	}

	public String getBank_address() {
		return bank_address;
	}

	public void setBank_address(String bank_address) {
		this.bank_address = bank_address;
	}

	public String getAccount_number() {
		return account_number;
	}

	public void setAccount_number(String account_number) {
		this.account_number = account_number;
	}
	
	
}
