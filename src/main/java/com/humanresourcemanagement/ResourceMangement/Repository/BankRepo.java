package com.humanresourcemanagement.ResourceMangement.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.humanresourcemanagement.ResourceMangement.Entity.Bank;
import com.humanresourcemanagement.ResourceMangement.Entity.User;

public interface BankRepo extends JpaRepository<Bank, Long>, JpaSpecificationExecutor<Bank>{

	boolean existsByNameAndHolderName(String name, String holderName);

	Optional<Bank> findByUser(User user);



}
