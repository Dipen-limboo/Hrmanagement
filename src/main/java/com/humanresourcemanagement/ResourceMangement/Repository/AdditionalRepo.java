package com.humanresourcemanagement.ResourceMangement.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.humanresourcemanagement.ResourceMangement.Entity.AdditionalInfo;
import com.humanresourcemanagement.ResourceMangement.Entity.User;

public interface AdditionalRepo extends JpaRepository<AdditionalInfo, Long>, JpaSpecificationExecutor<AdditionalInfo>{

	List<AdditionalInfo> findByUser(User user);

	void deleteByIdAndUser(Long id, User user);

	List<AdditionalInfo> findByIdAndUser(Long id, User user);

	boolean existsByUser(User user);

	void deleteByUser(User user);

	Optional<AdditionalInfo> findByUserAndId(User user, Long id);


	
}
