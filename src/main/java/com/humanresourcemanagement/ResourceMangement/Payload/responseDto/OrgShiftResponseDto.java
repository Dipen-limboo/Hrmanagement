package com.humanresourcemanagement.ResourceMangement.Payload.responseDto;

import java.time.LocalTime;

public class OrgShiftResponseDto {
	private Long org_shift_id;
	
	private LocalTime in_start;
	
	private LocalTime in_end;
	
	private LocalTime out_start;
	
	private LocalTime out_end;
	
	private boolean defaults;
	
	private boolean night;

	public OrgShiftResponseDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OrgShiftResponseDto(Long org_shift_id, LocalTime in_start, LocalTime in_end, LocalTime out_start,
			LocalTime out_end, boolean defaults, boolean night) {
		super();
		this.org_shift_id = org_shift_id;
		this.in_start = in_start;
		this.in_end = in_end;
		this.out_start = out_start;
		this.out_end = out_end;
		this.defaults = defaults;
		this.night = night;
	}

	public Long getOrg_shift_id() {
		return org_shift_id;
	}

	public void setOrg_shift_id(Long org_shift_id) {
		this.org_shift_id = org_shift_id;
	}

	public LocalTime getIn_start() {
		return in_start;
	}

	public void setIn_start(LocalTime in_start) {
		this.in_start = in_start;
	}

	public LocalTime getIn_end() {
		return in_end;
	}

	public void setIn_end(LocalTime in_end) {
		this.in_end = in_end;
	}

	public LocalTime getOut_start() {
		return out_start;
	}

	public void setOut_start(LocalTime out_start) {
		this.out_start = out_start;
	}

	public LocalTime getOut_end() {
		return out_end;
	}

	public void setOut_end(LocalTime out_end) {
		this.out_end = out_end;
	}

	public boolean isDefaults() {
		return defaults;
	}

	public void setDefaults(boolean defaults) {
		this.defaults = defaults;
	}

	public boolean isNight() {
		return night;
	}

	public void setNight(boolean night) {
		this.night = night;
	}
	
	
}
