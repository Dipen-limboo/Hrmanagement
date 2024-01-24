package com.humanresourcemanagement.ResourceMangement.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.humanresourcemanagement.ResourceMangement.Entity.AdditionalInfo;

public interface AdditionalRepo extends JpaRepository<AdditionalInfo, Long>, JpaSpecificationExecutor<AdditionalInfo>{
	
}
