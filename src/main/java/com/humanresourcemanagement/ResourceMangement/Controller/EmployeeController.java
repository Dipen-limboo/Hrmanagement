package com.humanresourcemanagement.ResourceMangement.Controller;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.humanresourcemanagement.ResourceMangement.Payload.requestDto.EmployeeRegisterDto;
import com.humanresourcemanagement.ResourceMangement.Service.EmployeeService;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/employee")
public class EmployeeController {
	
	@Autowired
	EmployeeService empService;

	@PostMapping("/register")
	@PreAuthorize("hasRole('SUPERADMIN')")
	public ResponseEntity<?> createEmployee(@Valid @RequestBody EmployeeRegisterDto employeeDto, Authentication auth, HttpServletRequest request) throws UnsupportedEncodingException, MessagingException{
		return empService.saveEmployee(employeeDto, auth, request);
	}
}
