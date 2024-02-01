package com.humanresourcemanagement.ResourceMangement.Repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.humanresourcemanagement.ResourceMangement.Entity.Designation;
import com.humanresourcemanagement.ResourceMangement.Entity.Promotion;
import com.humanresourcemanagement.ResourceMangement.Entity.User;

public interface PromotionRepo extends JpaRepository<Promotion, Long>, JpaSpecificationExecutor<Promotion> {
	
	Promotion findFirstByUserOrderByIdDesc(User user);

	List<Promotion> findByUser(User user);

	boolean existsByUserAndDesignation(User user, Designation designation);

	Promotion findByEndDateAndUser(LocalDate joinDate, User user);

}
