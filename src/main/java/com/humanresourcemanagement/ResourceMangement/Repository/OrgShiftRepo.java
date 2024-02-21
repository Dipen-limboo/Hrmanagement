package com.humanresourcemanagement.ResourceMangement.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.humanresourcemanagement.ResourceMangement.Entity.OrgShift;

public interface OrgShiftRepo extends JpaRepository<OrgShift, Long>, JpaSpecificationExecutor<OrgShift>	{

}
