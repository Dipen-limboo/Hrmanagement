package com.humanresourcemanagement.ResourceMangement.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.humanresourcemanagement.ResourceMangement.Entity.TimeSheet;

public interface TimeSheetRepo extends JpaRepository<TimeSheet, Long>, JpaSpecificationExecutor<TimeSheet>{

}