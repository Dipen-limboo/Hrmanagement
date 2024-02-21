package com.humanresourcemanagement.ResourceMangement.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.humanresourcemanagement.ResourceMangement.Entity.FiscalYear;

public interface FiscalYearRepo extends JpaRepository<FiscalYear, Long>, JpaSpecificationExecutor<FiscalYear>{

	boolean existsByYear(String year);

}
