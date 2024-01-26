package com.humanresourcemanagement.ResourceMangement.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.humanresourcemanagement.ResourceMangement.Entity.FamilyInfo;
import com.humanresourcemanagement.ResourceMangement.Entity.User;

public interface FamilyRepo extends JpaRepository<FamilyInfo, Long>, JpaSpecificationExecutor<FamilyInfo>{

	Optional<FamilyInfo> findByUser(User user);

	boolean existsByFile(String string);

}