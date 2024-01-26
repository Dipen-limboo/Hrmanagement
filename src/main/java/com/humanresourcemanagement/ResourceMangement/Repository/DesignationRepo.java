package com.humanresourcemanagement.ResourceMangement.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.humanresourcemanagement.ResourceMangement.Entity.Department;
import com.humanresourcemanagement.ResourceMangement.Entity.Designation;

public interface DesignationRepo extends JpaRepository<Designation, Long>, JpaSpecificationExecutor<Designation>{

	boolean existsByName(String name);

	Optional<Designation> findByDepartment(Department department);

}
