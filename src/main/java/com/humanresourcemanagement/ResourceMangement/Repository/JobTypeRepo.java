package com.humanresourcemanagement.ResourceMangement.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.humanresourcemanagement.ResourceMangement.Entity.JobType;

public interface JobTypeRepo extends JpaRepository<JobType, Long>, JpaSpecificationExecutor<JobType>{

	boolean existsByJobType(String jobType);

}
