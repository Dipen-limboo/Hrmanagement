package com.humanresourcemanagement.ResourceMangement.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.humanresourcemanagement.ResourceMangement.Entity.FamilyInfo;
import com.humanresourcemanagement.ResourceMangement.Entity.User;

public interface FamilyRepo extends JpaRepository<FamilyInfo, Long>, JpaSpecificationExecutor<FamilyInfo>{

	List<FamilyInfo> findByUser(User user);

	boolean existsByFile(String string);

	void deleteByIdAndUser(Long id, User user);

	Optional<FamilyInfo> findByIdAndUser(Long id, User user);

	boolean existsByUser(User user);


}
