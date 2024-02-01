package com.humanresourcemanagement.ResourceMangement.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.humanresourcemanagement.ResourceMangement.Payload.requestDto.EmployeeUpdateDto;
import com.humanresourcemanagement.ResourceMangement.Payload.requestDto.PromotionDto;
import com.humanresourcemanagement.ResourceMangement.Service.EmployeeService;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/employee")
public class EmployeeController {
	
	@Autowired
	EmployeeService empService;

	@PostMapping("/createEmployee/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('SUPERADMIN')")
	@Transactional
	public ResponseEntity<?> updateUser(@PathVariable Long id, @Valid @RequestBody EmployeeUpdateDto employeeDto, Authentication auth){
		return empService.saveEmployee(id, employeeDto, auth);
	}
	
	@PostMapping("/promoteEmployee/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('SUPERADMIN')")
	public ResponseEntity<?> promoteUser(@PathVariable Long id, @Valid @RequestBody PromotionDto promotionDto, Authentication auth){
		return empService.promoteEmployee(id, promotionDto, auth);
	}
}
