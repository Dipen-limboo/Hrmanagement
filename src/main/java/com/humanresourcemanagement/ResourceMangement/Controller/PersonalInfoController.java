package com.humanresourcemanagement.ResourceMangement.Controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.humanresourcemanagement.ResourceMangement.Payload.requestDto.AdditionalDto;
import com.humanresourcemanagement.ResourceMangement.Payload.requestDto.BankDto;
import com.humanresourcemanagement.ResourceMangement.Service.InfoService;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/employee")
public class PersonalInfoController {
	
	@Autowired
	InfoService infoService;
	
	//get list
	@GetMapping("/familyInfoLists")
	@PreAuthorize("hasRole('ADMIN') or hasRole('SUPERADMIN')")
	public ResponseEntity<?> familyInfoLists(@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "10") int size,
			@RequestParam(name = "sort", required = false, defaultValue = "id") String id,
			@RequestParam(name = "order", required = false, defaultValue = "desc") String sortDir
		) {
			Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(id).ascending() : Sort.by(id).descending();
			Pageable pageable = PageRequest.of(page -1, size, sort);
		return infoService.findAllfamilyInfoListofUser(pageable);
	}
	
	@GetMapping("/documentsList")
	@PreAuthorize("hasRole('ADMIN') or hasRole('SUPERADMIN')")
	public ResponseEntity<?> getDocumentList(@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "10") int size,
			@RequestParam(name = "sort", required = false, defaultValue = "id") String id,
			@RequestParam(name = "order", required = false, defaultValue = "desc") String sortDir
		) {
			Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(id).ascending() : Sort.by(id).descending();
			Pageable pageable = PageRequest.of(page -1, size, sort);
		return infoService.findAllDocumentListOfUser(pageable);
	}
	
	@GetMapping("/accountLists")
	@PreAuthorize("hasRole('ADMIN') or hasRole('SUPERADMIN') or hasRole('EMPLOYEE')")
	public ResponseEntity<?> getAccountListOfUser(Authentication auth) {
		return infoService.findAllAccountOfUser(auth);
	}
	
	//delete
	@DeleteMapping("/deleteDocument/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('SUPERADMIN') or hasRole('EMPLOYEE')")
	@Transactional
	public ResponseEntity<?> deleteDocument(@PathVariable Long id, Authentication auth) throws IOException{
		return infoService.deleteDocument(id, auth);
	}
	
	@DeleteMapping("/deleteEmpBankAccount/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('SUPERADMIN') or hasRole('EMPLOYEE')")
	@Transactional
	public ResponseEntity<?> deleteEmpbankAccount(@PathVariable Long id, Authentication auth) throws IOException{
		return infoService.deleteEmpbankById(id, auth);
	}
	
	@DeleteMapping("/deleteFamilyInfo/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('SUPERADMIN') or hasRole('EMPLOYEE')")
	@Transactional
	public ResponseEntity<?> deleteFamilyInfo(@PathVariable Long id, Authentication auth) throws IOException{
		return infoService.deleteFamilyInfo(id, auth);
	}
	
	//get
	@GetMapping("/getDocuments")
	@PreAuthorize("hasRole('ADMIN') or hasRole('SUPERADMIN') or hasRole('EMPLOYEE')")
	@Transactional
	public ResponseEntity<?> getDocumentsByAuth(Authentication auth){
		return infoService.getDocuments(auth);
	}
	
	@GetMapping("/getFamilyInfos")
	@PreAuthorize("hasRole('ADMIN') or hasRole('SUPERADMIN') or hasRole('EMPLOYEE')")
	@Transactional
	public ResponseEntity<?> getFamilyInfosByAuth(Authentication auth){
		return infoService.getFamily(auth);
	}
	
	@GetMapping("/getEmpBank/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('SUPERADMIN') or hasRole('EMPLOYEE')")
	@Transactional
	public ResponseEntity<?> getEmpBankByAuth(@PathVariable Long id, Authentication auth){
		return infoService.getEmpBank(id, auth);
	}
	
	
	
	//update

	@PutMapping("/getDocuments/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('SUPERADMIN') or hasRole('EMPLOYEE')")
	public ResponseEntity<?> updateDocumentsById(@PathVariable Long id,
			@RequestParam(name="citizenship", required=false) String citizenship,
			@RequestParam(name="pan", required=false)String pan,
			@RequestParam(name="nationalityId", required=false) String nationalityId,
			@RequestParam(name="issuedDate", required=false) LocalDate issuedDate,
			@RequestParam(name="issuedPlace", required=false) String issuedPlace,
		    @RequestPart("file") MultipartFile file, Authentication auth){
		return infoService.updateDocuments(id,citizenship, pan, nationalityId, issuedDate, issuedPlace, file, auth);
	}
	
	@PutMapping("/getFamily/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('SUPERADMIN') or hasRole('EMPLOYEE')")
	public ResponseEntity<?> updateFamilyById(@PathVariable Long id, 
			@RequestParam(required=false) String firstName,
			@RequestParam(required=false) String middleName,
			@RequestParam(required=false) String lastName,
			@RequestParam(required=false) Set<String> relation,
			@RequestParam(required=false) String phone,
			@RequestParam(required=false) MultipartFile file,
			Authentication auth) throws IOException{
		return infoService.updateFamily(id, firstName, middleName, lastName, relation, phone, file, auth);
	}
	
	@PutMapping("/getEmpBank/{id}")
	@PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN') or hasRole('SUPERADMIN')")
	public ResponseEntity<?> getEmpBank(@Valid @RequestParam(required=false) String emp_account_number,
			@RequestParam(required=false) String emp_bank_branch, 
			@RequestParam(required=false) MultipartFile qrPath,
			@RequestParam(required=false) Long bank_id,
			@PathVariable Long id,
			Authentication auth) throws IOException{
		return infoService.updateEmpBank(emp_account_number, emp_bank_branch, qrPath,  bank_id, id, auth);
	}
}
