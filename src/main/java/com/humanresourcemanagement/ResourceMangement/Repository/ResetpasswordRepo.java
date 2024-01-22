package com.humanresourcemanagement.ResourceMangement.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.humanresourcemanagement.ResourceMangement.Entity.ResetPassword;

public interface ResetpasswordRepo extends JpaRepository<ResetPassword, Long>{
	ResetPassword findByToken(String token); 
}