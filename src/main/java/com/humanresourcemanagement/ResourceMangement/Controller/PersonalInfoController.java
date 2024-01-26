package com.humanresourcemanagement.ResourceMangement.Controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.humanresourcemanagement.ResourceMangement.Service.InfoService;

import jakarta.transaction.Transactional;

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
	public ResponseEntity<?> listOfDesignation(@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "10") int size,
			@RequestParam(name = "sort", required = false, defaultValue = "id") String id,
			@RequestParam(name = "order", required = false, defaultValue = "desc") String sortDir
		) {
			Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(id).ascending() : Sort.by(id).descending();
			Pageable pageable = PageRequest.of(page -1, size, sort);
		return infoService.findAllDesgination(pageable);
	}
	
	@GetMapping("/accountLists")
	@PreAuthorize("hasRole('ADMIN') or hasRole('SUPERADMIN')")
	public ResponseEntity<?> listOfaccounts(@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "10") int size,
			@RequestParam(name = "sort", required = false, defaultValue = "id") String id,
			@RequestParam(name = "order", required = false, defaultValue = "desc") String sortDir
		) {
			Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(id).ascending() : Sort.by(id).descending();
			Pageable pageable = PageRequest.of(page -1, size, sort);
		return infoService.findAllaccountsOfStaff(pageable);
	}
	
	@GetMapping("/informationLists")
	@PreAuthorize("hasRole('ADMIN') or hasRole('SUPERADMIN')")
	public ResponseEntity<?> additionalListInfo(@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "10") int size,
			@RequestParam(name = "sort", required = false, defaultValue = "id") String id,
			@RequestParam(name = "order", required = false, defaultValue = "desc") String sortDir
		) {
			Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(id).ascending() : Sort.by(id).descending();
			Pageable pageable = PageRequest.of(page -1, size, sort);
		return infoService.findAlladditionalListofUser(pageable);
	}
	
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
	
	@DeleteMapping("/deleteAccount/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('SUPERADMIN') or hasRole('EMPLOYEE')")
	@Transactional
	public ResponseEntity<?> deleteAccount(@PathVariable Long id, Authentication auth){
		return infoService.delete(id, auth);
	}
	
}
