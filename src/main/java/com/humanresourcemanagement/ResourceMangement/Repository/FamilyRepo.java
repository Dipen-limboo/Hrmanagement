package com.humanresourcemanagement.ResourceMangement.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.humanresourcemanagement.ResourceMangement.Entity.FamilyInfo;

public interface FamilyRepo extends JpaRepository<FamilyInfo, Long>, JpaSpecificationExecutor<FamilyInfo>{

}
