package com.humanresourcemanagement.ResourceMangement.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.humanresourcemanagement.ResourceMangement.Entity.Training;
import com.humanresourcemanagement.ResourceMangement.Entity.User;

public interface TrainingRepo extends JpaRepository<Training, Long>, JpaSpecificationExecutor<Training>{

	List<Training> findByUser(User user);

	Optional<Training> findByIdAndUser(Long id, User user);
	
}
