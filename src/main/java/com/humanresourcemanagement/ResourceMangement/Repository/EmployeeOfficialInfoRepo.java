package com.humanresourcemanagement.ResourceMangement.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.humanresourcemanagement.ResourceMangement.Entity.EmployeeOfficialInfo;
import com.humanresourcemanagement.ResourceMangement.Entity.User;

public interface EmployeeOfficialInfoRepo extends JpaRepository<EmployeeOfficialInfo, String>, JpaSpecificationExecutor<EmployeeOfficialInfo>{

	Optional<EmployeeOfficialInfo> findByUser(User user);

	boolean existsByUser(User user);

}
