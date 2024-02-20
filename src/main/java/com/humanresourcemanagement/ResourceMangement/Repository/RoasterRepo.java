package com.humanresourcemanagement.ResourceMangement.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.humanresourcemanagement.ResourceMangement.Entity.Roaster;

public interface RoasterRepo extends JpaRepository<Roaster, Long>, JpaSpecificationExecutor<Roaster>{

}
