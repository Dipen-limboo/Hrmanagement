package com.humanresourcemanagement.ResourceMangement.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.humanresourcemanagement.ResourceMangement.Entity.Employee;
import com.humanresourcemanagement.ResourceMangement.Entity.User;

public interface EmployeeRepo extends JpaRepository<Employee, Long>, JpaSpecificationExecutor<Employee>{



	Optional<Employee> findByUsername(User user);

	boolean existsByEmail(String email);

}
