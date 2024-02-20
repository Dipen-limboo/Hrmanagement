package com.humanresourcemanagement.ResourceMangement.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.humanresourcemanagement.ResourceMangement.Entity.LeaveInfo;

public interface LeaveRepo extends JpaRepository<LeaveInfo, Long> , JpaSpecificationExecutor<LeaveInfo>{

	boolean existsByLeaveName(String leave_name);

}
