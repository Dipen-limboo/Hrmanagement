package com.humanresourcemanagement.ResourceMangement.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.humanresourcemanagement.ResourceMangement.Entity.Bank;

public interface BankRepo extends JpaRepository<Bank, Long>, JpaSpecificationExecutor<Bank>{

	boolean existsByNameAndHolderName(String name, String holderName);

}
