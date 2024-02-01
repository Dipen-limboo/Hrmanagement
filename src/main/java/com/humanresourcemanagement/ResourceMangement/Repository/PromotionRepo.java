package com.humanresourcemanagement.ResourceMangement.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.humanresourcemanagement.ResourceMangement.Entity.Promotion;

public interface PromotionRepo extends JpaRepository<Promotion, Long>, JpaSpecificationExecutor<Promotion> {

}
