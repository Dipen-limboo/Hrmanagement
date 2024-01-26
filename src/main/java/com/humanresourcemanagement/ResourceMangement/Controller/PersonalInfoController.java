package com.humanresourcemanagement.ResourceMangement.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.humanresourcemanagement.ResourceMangement.Service.InfoService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/employee")
public class PersonalInfoController {
	
	@Autowired
	InfoService infoService;
	
	@GetMapping("/departmentLists")
	@PreAuthorize("hasRole('ADMIN') or hasRole('SUPERADMIN')")
	public ResponseEntity<?> listOfDepartment(){
		return infoService.findDepartment();
	}
	
	@GetMapping("/designationLists")
	@PreAuthorize("hasRole('ADMIN') or hasRole('SUPERADMIN')")
	public ResponseEntity<?> listOfDesignation(){
		return infoService.findAllDesgination();
	}
	
	@GetMapping("/accountLists")
	@PreAuthorize("hasRole('ADMIN') or hasRole('SUPERADMIN')")
	public ResponseEntity<?> listOfaccounts(){
		return infoService.findAllaccountsOfStaff();
	}
	
	@GetMapping("/informationLists")
	@PreAuthorize("hasRole('ADMIN') or hasRole('SUPERADMIN')")
	public ResponseEntity<?> additionalListInfo(){
		return infoService.findAlladditionalListofUser();
	}
	
	@GetMapping("/familyInfoLists")
	@PreAuthorize("hasRole('ADMIN') or hasRole('SUPERADMIN')")
	public ResponseEntity<?> familyInfoLists(){
		return infoService.findAllfamilyInfoListofUser();
	}
	
}
