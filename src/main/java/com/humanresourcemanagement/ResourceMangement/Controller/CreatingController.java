package com.humanresourcemanagement.ResourceMangement.Controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Set;

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

import com.humanresourcemanagement.ResourceMangement.Payload.requestDto.BankDto;
import com.humanresourcemanagement.ResourceMangement.Payload.requestDto.DepartmentDto;
import com.humanresourcemanagement.ResourceMangement.Payload.requestDto.DesignationDto;
import com.humanresourcemanagement.ResourceMangement.Payload.requestDto.GradeDto;
import com.humanresourcemanagement.ResourceMangement.Payload.requestDto.JobTypeDto;
import com.humanresourcemanagement.ResourceMangement.Payload.requestDto.SubDepartmentDto;
import com.humanresourcemanagement.ResourceMangement.Payload.requestDto.WorkTypeDto;
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
	
	@PostMapping("/add_section")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> saveSubDepartment(@Valid @RequestBody SubDepartmentDto subDepartmentDto){
		return createService.addSubDepartment(subDepartmentDto);
	}
	
	@PostMapping("/add_designation")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> saveDesignation(@Valid @RequestBody DesignationDto designationDto){
		return createService.addDesgination(designationDto);
	}
	
	@PostMapping("/add_bank")
	@PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN') or hasRole('SUPERADMIN')")
	public ResponseEntity<?> saveBank(@Valid @RequestBody BankDto bankDto){
		return createService.addBank(bankDto);
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
	public ResponseEntity<?> saveFamilyInfo(@Valid @RequestParam(required=false) String firstName,
			@RequestParam(required=false) String middleName,
			@RequestParam(required=false) String lastName,
			@RequestParam(required=false) Set<String> relation,
			@RequestParam(required=false) String phone,
			@RequestParam(required=false) MultipartFile file, Authentication auth) throws IOException{
		return createService.addFamily(firstName, middleName, lastName, relation, phone, file, auth);
	}
	
	@PostMapping("/add_working_type")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> saveWorkingType (@Valid @RequestBody WorkTypeDto workDto){
		return createService.saveWork(workDto);
	}
	
	@PostMapping("/add_job_type")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> saveJobType (@Valid @RequestBody JobTypeDto jobDto){
		return createService.saveJob(jobDto);
	}
	
	@PostMapping("/addOrganization")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> saveOrganization(@Valid @RequestParam(required=true) String org_name,
			@RequestParam(required=true) String org_address,
			@RequestParam(required=true) String org_email,
			@RequestParam(required=false) String org_phone,
			@RequestParam(required=false) String org_website,
			@RequestParam(required=false) MultipartFile org_logo_path) throws IOException{
		return createService.saveOrg(org_name, org_address, org_email, org_phone, org_website, org_logo_path);
	}
	
	@PostMapping("/addGrade")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> saveGrade(@Valid @RequestBody GradeDto gradeDto){
		return createService.saveGrad(gradeDto);
	}
	
	@PostMapping("/add_emp_bank")
	@PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN') or hasRole('SUPERADMIN')")
	public ResponseEntity<?> saveEmpBank(@Valid @RequestParam(required=false) String emp_account_number,
			@RequestParam(required=false) String emp_bank_branch, 
			@RequestParam(required=false) MultipartFile qrPath,
			@RequestParam(required=false) Long bank_id,
			Authentication auth) throws IOException{
		return createService.addEmpBank(emp_account_number, emp_bank_branch, qrPath, bank_id, auth);
	}
}
