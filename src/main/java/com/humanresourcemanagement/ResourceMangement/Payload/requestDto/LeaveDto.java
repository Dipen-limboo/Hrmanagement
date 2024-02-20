package com.humanresourcemanagement.ResourceMangement.Payload.requestDto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class LeaveDto {
	@NotEmpty
	private String leave_name;
	
	@NotNull
	private Boolean is_cashable;
	
	@NotNull
	private int max_days;
	
	private boolean status = false;
	
	@NotNull
	private Boolean is_leave_forwareded;

	public LeaveDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LeaveDto(String leave_name, @NotNull  boolean is_cashable, @NotNull int max_days,
			@NotNull  boolean is_leave_forwareded) {
		super();
		this.leave_name = leave_name;
		this.is_cashable = is_cashable;
		this.max_days = max_days;
		this.is_leave_forwareded = is_leave_forwareded;
	}

	public String getLeave_name() {
		return leave_name;
	}

	public void setLeave_name(String leave_name) {
		this.leave_name = leave_name;
	}

	public boolean isIs_cashable() {
		return is_cashable;
	}

	public void setIs_cashable(boolean is_cashable) {
		this.is_cashable = is_cashable;
	}

	public int getMax_days() {
		return max_days;
	}

	public void setMax_days(int max_days) {
		this.max_days = max_days;
	}

	public boolean isIs_leave_forwareded() {
		return is_leave_forwareded;
	}

	public void setIs_leave_forwareded(boolean is_leave_forwareded) {
		this.is_leave_forwareded = is_leave_forwareded;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
	
	
}
