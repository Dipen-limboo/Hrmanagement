package com.humanresourcemanagement.ResourceMangement.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.humanresourcemanagement.ResourceMangement.Entity.Contract;
import com.humanresourcemanagement.ResourceMangement.Entity.User;

public interface ContractRepo extends JpaRepository<Contract, Long>, JpaSpecificationExecutor<Contract>{

	Optional<Contract> findByUserAndContractStatus(User user, boolean b);

	List<Contract> findByUser(User user);

	Optional<Contract> findByIdAndUser(Long id, User user);

	boolean existsByUser(User user);

}
