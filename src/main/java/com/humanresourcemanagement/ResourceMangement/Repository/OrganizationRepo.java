package com.humanresourcemanagement.ResourceMangement.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.humanresourcemanagement.ResourceMangement.Entity.Organization;

public interface OrganizationRepo extends JpaRepository<Organization, Long>, JpaSpecificationExecutor<Organization>{

	boolean existsByPath(String string);

}
