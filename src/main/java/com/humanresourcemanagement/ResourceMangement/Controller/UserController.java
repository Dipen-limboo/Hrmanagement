package com.humanresourcemanagement.ResourceMangement.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.humanresourcemanagement.ResourceMangement.Payload.requestDto.DepartmentDto;
import com.humanresourcemanagement.ResourceMangement.Payload.requestDto.DesignationDto;
import com.humanresourcemanagement.ResourceMangement.Payload.requestDto.FormDto;
import com.humanresourcemanagement.ResourceMangement.Payload.requestDto.NewPasswordDto;
import com.humanresourcemanagement.ResourceMangement.Payload.requestDto.RestPasswordDto;
import com.humanresourcemanagement.ResourceMangement.Payload.requestDto.VerifiedTokenDto;
import com.humanresourcemanagement.ResourceMangement.Payload.responseDto.ResponseFormDto;
import com.humanresourcemanagement.ResourceMangement.Service.ResetPasswordService;
import com.humanresourcemanagement.ResourceMangement.Service.UserServiceImpl;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/users")
public class UserController {
	@Autowired
	UserServiceImpl userService;
	
	@Autowired
	ResetPasswordService passwordService;
	
	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody ResponseFormDto loginRequest) {
		return userService.signIn(loginRequest);
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody FormDto signUpRequest) {
		return userService.signup(signUpRequest);
	}
	
	@PostMapping("/verifiedToken")
	public ResponseEntity<?> verifyingSignUpRequest(@RequestBody VerifiedTokenDto verifiedDto){
		return userService.verified(verifiedDto);
	}
	
	@PostMapping("/generate-token")
	@Transactional
	public ResponseEntity<?> generateTokenByMail(@Valid @RequestBody RestPasswordDto resetPasswordDto){
		return passwordService.generateToken(resetPasswordDto);
	}

	@PostMapping("/reset-password")
	public ResponseEntity<?> resetPassword(@Valid @RequestBody NewPasswordDto passwordDto){
		return passwordService.reset(passwordDto);
	}
	
	@GetMapping("/listOfuser")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> listOfUsers(@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "10") int size,
			@RequestParam(name = "sort", required = false, defaultValue = "id") String id,
			@RequestParam(name = "order", required = false, defaultValue = "desc") String sortDir,
			@RequestParam(required=false) String userRole
		) {
			Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(id).ascending() : Sort.by(id).descending();
			Pageable pageable = PageRequest.of(page -1, size, sort);
			return userService.getUserLists(userRole, pageable);
	}
	
	@PutMapping("/userRole/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> changeUserRole(@PathVariable Long id){
		return userService.changeRole(id);
	}
	
	@PostMapping("/add_department")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> saveDepartment(@Valid @RequestBody DepartmentDto departmentDto){
		return userService.addDepartment(departmentDto);
	}
	
	@PostMapping("/add_designation")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> saveDesignation(@Valid @RequestBody DesignationDto designationDto){
		return userService.addDesgination(designationDto);
	}
}
