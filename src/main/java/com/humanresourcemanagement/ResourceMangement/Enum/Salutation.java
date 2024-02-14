package com.humanresourcemanagement.ResourceMangement.Enum;

public enum Salutation {
	MR("Mr."),
	MRS("Mrs."),
	DR("Dr."),
	ER("Er.")
	;

	public final String salutation;

	private Salutation(String salutation) {
		this.salutation = salutation;
	}

	public String getSalutation() {
		return salutation;
	}
	
	
}
