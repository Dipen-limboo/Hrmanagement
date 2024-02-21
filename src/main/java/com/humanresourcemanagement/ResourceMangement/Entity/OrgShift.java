package com.humanresourcemanagement.ResourceMangement.Entity;

import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="Tbl_org_shift")
public class OrgShift {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="org_shift_id")
	private Long id;
	
	@Column(name="in_start")
	private LocalTime inStart;
	
	@Column(name="in_end")
	private LocalTime inEnd;
	
	@Column(name="out_start")
	private LocalTime outStart;
	
	@Column(name="out_end")
	private LocalTime outEnd;
	
	@Column(name="isDefault")
	private boolean defaults;
	
	@Column(name="isNight_shift")
	private boolean night;

	public OrgShift() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OrgShift(Long id, LocalTime inStart, LocalTime inEnd, LocalTime outStart, LocalTime outEnd, boolean defaults,
			boolean night) {
		super();
		this.id = id;
		this.inStart = inStart;
		this.inEnd = inEnd;
		this.outStart = outStart;
		this.outEnd = outEnd;
		this.defaults = defaults;
		this.night = night;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalTime getInStart() {
		return inStart;
	}

	public void setInStart(LocalTime inStart) {
		this.inStart = inStart;
	}

	public LocalTime getInEnd() {
		return inEnd;
	}

	public void setInEnd(LocalTime inEnd) {
		this.inEnd = inEnd;
	}

	public LocalTime getOutStart() {
		return outStart;
	}

	public void setOutStart(LocalTime outStart) {
		this.outStart = outStart;
	}

	public LocalTime getOutEnd() {
		return outEnd;
	}

	public void setOutEnd(LocalTime outEnd) {
		this.outEnd = outEnd;
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
