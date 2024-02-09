package com.humanresourcemanagement.ResourceMangement.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.humanresourcemanagement.ResourceMangement.Entity.Education;
import com.humanresourcemanagement.ResourceMangement.Entity.User;

public interface EducationRepo extends JpaRepository<Education, Long>, JpaSpecificationExecutor<Education> {

	Optional<Education> findByIdAndUser(Long id, User user);

	List<Education> findByUser(User user);

}
