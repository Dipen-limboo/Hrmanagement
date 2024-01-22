package com.humanresourcemanagement.ResourceMangement.Payload.requestDto;

import jakarta.validation.constraints.NotEmpty;

public class VerifiedTokenDto {
	@NotEmpty
	private String token;

	public VerifiedTokenDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public VerifiedTokenDto(@NotEmpty String token) {
		super();
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
