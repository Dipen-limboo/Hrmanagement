package com.humanresourcemanagement.ResourceMangement.Payload.requestDto;

import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotNull;

public class OrgShiftDto {
	@NotNull
	@JsonFormat(pattern="HH:mm:ss")
	private LocalTime in_start;
	
	@NotNull
	@JsonFormat(pattern="HH:mm:ss")
	private LocalTime in_end;
	
	@NotNull
	@JsonFormat(pattern="HH:mm:ss")
	private LocalTime out_start;
	
	@NotNull
	@JsonFormat(pattern="HH:mm:ss")
	private LocalTime out_end;
	
	@NotNull
	private boolean defaults = true;
	
	private boolean night = false;

	public OrgShiftDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OrgShiftDto(@NotNull LocalTime in_start, @NotNull LocalTime in_end, @NotNull LocalTime out_start,
			@NotNull LocalTime out_end, boolean defaults, boolean night) {
		super();
		this.in_start = in_start;
		this.in_end = in_end;
		this.out_start = out_start;
		this.out_end = out_end;
		this.defaults = defaults;
		this.night = night;
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
