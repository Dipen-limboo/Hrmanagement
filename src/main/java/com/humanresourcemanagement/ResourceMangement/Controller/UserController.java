package com.humanresourcemanagement.ResourceMangement.Controller;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.humanresourcemanagement.ResourceMangement.Payload.requestDto.ChangeRoleDto;
import com.humanresourcemanagement.ResourceMangement.Payload.requestDto.ChangedPasswordDto;
import com.humanresourcemanagement.ResourceMangement.Payload.requestDto.EmployeeRegisterDto;
import com.humanresourcemanagement.ResourceMangement.Payload.requestDto.NewPasswordDto;
import com.humanresourcemanagement.ResourceMangement.Payload.requestDto.RestPasswordDto;
import com.humanresourcemanagement.ResourceMangement.Payload.responseDto.ResponseFormDto;
import com.humanresourcemanagement.ResourceMangement.Service.ResetPasswordService;
import com.humanresourcemanagement.ResourceMangement.Service.UserServiceImpl;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
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
//	@PreAuthorize("hasRole('SUPERADMIN')")
	public ResponseEntity<?> registerUser(@Valid @RequestBody EmployeeRegisterDto employeeDto, Authentication auth, HttpServletRequest request) throws UnsupportedEncodingException, MessagingException {
		return userService.signup(employeeDto, auth, request);
	}
	
		
	@PostMapping("/changePassword")
	public ResponseEntity<?> verifyingSignUpRequest(@RequestParam(required = true) String token,@Valid @RequestBody ChangedPasswordDto verifiedDto){
		return userService.verified(token, verifiedDto);
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
	@Transactional
	public ResponseEntity<?> changeUserRole(@PathVariable Long id, @RequestBody ChangeRoleDto changeRoleDto, Authentication auth){
		return userService.changeRole(id, changeRoleDto, auth);
	}
}
