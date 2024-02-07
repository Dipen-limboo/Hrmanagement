package com.humanresourcemanagement.ResourceMangement.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.humanresourcemanagement.ResourceMangement.Entity.EmpBank;
import com.humanresourcemanagement.ResourceMangement.Entity.User;

public interface EmpBankRepo extends JpaRepository<EmpBank, Long>, JpaSpecificationExecutor<EmpBank> {

	boolean existsByUser(User user);

	List<EmpBank> findByUser(User user);

	Optional<EmpBank> findByIdAndUser(Long id, User user);

	void deleteByIdAndUser(Long id, User user);

	

}
