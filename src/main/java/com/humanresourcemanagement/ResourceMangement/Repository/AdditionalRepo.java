package com.humanresourcemanagement.ResourceMangement.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.humanresourcemanagement.ResourceMangement.Entity.AdditionalInfo;
import com.humanresourcemanagement.ResourceMangement.Entity.User;

public interface AdditionalRepo extends JpaRepository<AdditionalInfo, Long>, JpaSpecificationExecutor<AdditionalInfo>{

	Optional<AdditionalInfo> findByUser(User user);

	void deleteByIdAndUser(Long id, User user);


	
}
