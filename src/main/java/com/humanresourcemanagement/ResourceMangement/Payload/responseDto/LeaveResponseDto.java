package com.humanresourcemanagement.ResourceMangement.Payload.responseDto;

public class LeaveResponseDto {
	private Long leave_id;
	
	private String leave_name;

	private boolean is_cashable;
	
	private int max_days;
	
	private boolean status;
	
	private boolean is_leave_forwareded;

	public LeaveResponseDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LeaveResponseDto(Long leave_id, String leave_name, boolean is_cashable, int max_days,
			boolean is_leave_forwareded) {
		super();
		this.leave_id = leave_id;
		this.leave_name = leave_name;
		this.is_cashable = is_cashable;
		this.max_days = max_days;
		this.is_leave_forwareded = is_leave_forwareded;
	}

	public Long getLeave_id() {
		return leave_id;
	}

	public void setLeave_id(Long leave_id) {
		this.leave_id = leave_id;
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