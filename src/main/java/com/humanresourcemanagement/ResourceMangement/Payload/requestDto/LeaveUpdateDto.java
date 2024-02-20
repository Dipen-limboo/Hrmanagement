package com.humanresourcemanagement.ResourceMangement.Payload.requestDto;

import jakarta.validation.constraints.NotNull;

public class LeaveUpdateDto {
	private String leave_name;

	@NotNull
	private Boolean is_cashable;
	
	private int max_days =0;
	
	@NotNull
	private Boolean status;
	
	@NotNull
	private Boolean is_leave_forwareded;

	public LeaveUpdateDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LeaveUpdateDto(String leave_name, boolean is_cashable, int max_days, boolean status,
			boolean is_leave_forwareded) {
		super();
		this.leave_name = leave_name;
		this.is_cashable = is_cashable;
		this.max_days = max_days;
		this.status = status;
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

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public boolean isIs_leave_forwareded() {
		return is_leave_forwareded;
	}

	public void setIs_leave_forwareded(boolean is_leave_forwareded) {
		this.is_leave_forwareded = is_leave_forwareded;
	}

}
