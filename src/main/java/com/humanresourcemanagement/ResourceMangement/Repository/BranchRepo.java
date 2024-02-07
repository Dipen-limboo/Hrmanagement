package com.humanresourcemanagement.ResourceMangement.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.humanresourcemanagement.ResourceMangement.Entity.Branch;

public interface BranchRepo extends JpaRepository<Branch, Long>, JpaSpecificationExecutor<Branch>{

}
