package com.humanresourcemanagement.ResourceMangement.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.humanresourcemanagement.ResourceMangement.Entity.Bank;
import com.humanresourcemanagement.ResourceMangement.Entity.User;

public interface BankRepo extends JpaRepository<Bank, Long>, JpaSpecificationExecutor<Bank>{

	boolean existsByNameAndAddress(String name, String address);

}
