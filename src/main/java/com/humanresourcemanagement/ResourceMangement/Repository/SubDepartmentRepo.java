package com.humanresourcemanagement.ResourceMangement.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.humanresourcemanagement.ResourceMangement.Entity.SubDepartment;

public interface SubDepartmentRepo extends JpaRepository<SubDepartment, Long>, JpaSpecificationExecutor<SubDepartment>{

	boolean existsByName(String name);

}
