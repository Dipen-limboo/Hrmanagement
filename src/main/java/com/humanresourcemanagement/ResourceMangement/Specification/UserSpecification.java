package com.humanresourcemanagement.ResourceMangement.Specification;

import org.springframework.data.jpa.domain.Specification;

import com.humanresourcemanagement.ResourceMangement.Entity.User;

public class UserSpecification {

	public UserSpecification() {
		super();
	
	}
	
	public static Specification<User> getByRole(String userRole){
		return (root, query, builder) -> builder.equal(root.get("role"), userRole);
	}
}
