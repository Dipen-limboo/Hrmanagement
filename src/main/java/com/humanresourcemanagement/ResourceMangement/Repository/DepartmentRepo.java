package com.humanresourcemanagement.ResourceMangement.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.humanresourcemanagement.ResourceMangement.Entity.Department;

public interface DepartmentRepo extends JpaRepository<Department, Long>, JpaSpecificationExecutor<Department>{

	boolean existsByName(String name);

	boolean existsByPhone(String tel);

}
