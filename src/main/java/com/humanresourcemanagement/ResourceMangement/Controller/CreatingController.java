package com.humanresourcemanagement.ResourceMangement.Controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.humanresourcemanagement.ResourceMangement.Payload.requestDto.AdditionalDto;
import com.humanresourcemanagement.ResourceMangement.Payload.requestDto.BankDto;
import com.humanresourcemanagement.ResourceMangement.Payload.requestDto.DepartmentDto;
import com.humanresourcemanagement.ResourceMangement.Payload.requestDto.DesignationDto;
import com.humanresourcemanagement.ResourceMangement.Payload.requestDto.FamilyInfoDto;
import com.humanresourcemanagement.ResourceMangement.Service.CreatingService;

import jakarta.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/create")
public class CreatingController {
	@Autowired
	CreatingService createService;

	@PostMapping("/add_department")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> saveDepartment(@Valid @RequestBody DepartmentDto departmentDto){
		return createService.addDepartment(departmentDto);
	}
	
	@PostMapping("/add_designation")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> saveDesignation(@Valid @RequestBody DesignationDto designationDto){
		return createService.addDesgination(designationDto);
	}
	
	@PostMapping("/add_bank")
	@PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN') or hasRole('SUPERADMIN')")
	public ResponseEntity<?> saveBank(@Valid @RequestBody BankDto bankDto, Authentication auth){
		return createService.addBank(bankDto, auth);
	}
	
	@PostMapping("/add_documents")
	@PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN') or hasRole('SUPERADMIN')")
	public ResponseEntity<?> saveDocuments(@RequestParam(name="citizenship", required=false) String citizenship,
			@RequestParam(name="pan", required=false)String pan,
			@RequestParam(name="nationalityId", required=false) String nationalityId,
			@RequestParam(name="issuedDate", required=false) LocalDate issuedDate,
			@RequestParam(name="issuedPlace", required=false) String issuedPlace,
		    @RequestPart("file") MultipartFile file,
		    Authentication auth){
		return createService.addDocument(citizenship, pan, nationalityId, issuedDate, issuedPlace, file, auth);
	}
	
	@PostMapping("/add_familyInfo")
	@PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN') or hasRole('SUPERADMIN')")
	public ResponseEntity<?> saveFamilyInfo(@Valid @RequestBody FamilyInfoDto familyInfoDto, Authentication auth){
		return createService.addFamily(familyInfoDto, auth);
	}
	
	@PostMapping("/add_additionalInfo")
	@PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN') or hasRole('SUPERADMIN')")
	public ResponseEntity<?> saveAdditionalInfo(@Valid @RequestBody AdditionalDto additionalDto, Authentication auth){
		return createService.addAdditional(additionalDto, auth);
	}
}
