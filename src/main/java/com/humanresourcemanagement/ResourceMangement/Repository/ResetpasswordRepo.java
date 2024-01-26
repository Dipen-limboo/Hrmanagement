package com.humanresourcemanagement.ResourceMangement.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.humanresourcemanagement.ResourceMangement.Entity.ResetPassword;
import com.humanresourcemanagement.ResourceMangement.Entity.User;

public interface ResetpasswordRepo extends JpaRepository<ResetPassword, Long>{
	ResetPassword findByToken(String token);

	Optional<ResetPassword> findByUser(User user); 
}