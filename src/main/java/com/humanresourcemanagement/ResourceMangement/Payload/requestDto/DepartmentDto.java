package com.humanresourcemanagement.ResourceMangement.Payload.requestDto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public class DepartmentDto {
	@NotEmpty
	private String name;

	@NotEmpty
	private String address;
	
	@NotEmpty
	@Pattern(regexp = "^\\d{9}$",
    message = "{The telephone number must contain 9 numbers}")
	private String tel;

	public DepartmentDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DepartmentDto(@NotEmpty String name,  @NotEmpty String address,
			@NotEmpty @Pattern(regexp = "^\\d{9}$", message = "{The telephone number must contain 9 numbers}") String tel) {
		super();
		this.name = name;
		this.address = address;
		this.tel = tel;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

}
