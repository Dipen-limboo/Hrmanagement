package com.humanresourcemanagement.ResourceMangement.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.humanresourcemanagement.ResourceMangement.Entity.Designation;
import com.humanresourcemanagement.ResourceMangement.Entity.Promotion;
import com.humanresourcemanagement.ResourceMangement.Entity.SubDepartment;
import com.humanresourcemanagement.ResourceMangement.Entity.User;

public interface PromotionRepo extends JpaRepository<Promotion, Long>, JpaSpecificationExecutor<Promotion> {
	
	Promotion findFirstByUserOrderByIdDesc(User user);
 
	List<Promotion> findByUser(User user);

	boolean existsByUserAndDesignation(User user, Designation designation);

	Promotion findByEndDateAndUser(LocalDate joinDate, User user);

	Optional<Promotion> findByUserAndSubDepartmentAndDesignation(User user, SubDepartment subDepartment,
			Designation designation);

	Optional<Promotion> findByUserAndStatus(User user, boolean b);

	Optional<Promotion> findByUserAndJoinDate(User user, LocalDate joinDate);

	Optional<Promotion> findByUserAndEndDate(User user, LocalDate joinDate);

	boolean existsByUserAndStatus(User user, boolean b);

}
