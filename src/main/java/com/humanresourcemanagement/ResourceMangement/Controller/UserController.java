package com.humanresourcemanagement.ResourceMangement.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
