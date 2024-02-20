package com.humanresourcemanagement.ResourceMangement.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="tbl_org_leave_info")
public class LeaveInfo {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="leave_id")
	private Long id;
	
	@Column(name="leave_name")
	private String leaveName;
	
	@Column(name="is_cashable")
	private boolean isCasable;
	
	@Column(name="max_days")
	private int maxDays;
	
	@Column(name="status")
	private boolean status = true;
	
	@Column(name="is_leave_forward")
	private boolean accumulatable;

	public LeaveInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LeaveInfo(Long id, String leaveName, boolean isCasable, int maxDays, boolean status, boolean accumulatable) {
		super();
		this.id = id;
		this.leaveName = leaveName;
		this.isCasable = isCasable;
		this.maxDays = maxDays;
		this.status = status;
		this.accumulatable = accumulatable;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLeaveName() {
		return leaveName;
	}

	public void setLeaveName(String leaveName) {
		this.leaveName = leaveName;
	}

	public boolean isCasable() {
		return isCasable;
	}

	public void setCasable(boolean isCasable) {
		this.isCasable = isCasable;
	}

	public int getMaxDays() {
		return maxDays;
	}

	public void setMaxDays(int maxDays) {
		this.maxDays = maxDays;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public boolean isAccumulatable() {
		return accumulatable;
	}

	public void setAccumulatable(boolean accumulatable) {
		this.accumulatable = accumulatable;
	}
	
	
}
