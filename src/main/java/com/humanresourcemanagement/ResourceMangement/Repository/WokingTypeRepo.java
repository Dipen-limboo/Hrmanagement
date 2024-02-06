package com.humanresourcemanagement.ResourceMangement.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.humanresourcemanagement.ResourceMangement.Entity.WorkingType;

public interface WokingTypeRepo extends JpaRepository<WorkingType, Long>, JpaSpecificationExecutor<WorkingType> {

	boolean existsByWorkingType(String workType);
	
}
