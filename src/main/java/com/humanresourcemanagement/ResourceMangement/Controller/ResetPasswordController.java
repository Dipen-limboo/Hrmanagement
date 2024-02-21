package com.humanresourcemanagement.ResourceMangement.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.humanresourcemanagement.ResourceMangement.Payload.requestDto.NewPasswordDto;
import com.humanresourcemanagement.ResourceMangement.Payload.requestDto.RestPasswordDto;
import com.humanresourcemanagement.ResourceMangement.Service.ResetPasswordService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/users")
@SecurityRequirement(name="Bearer Authentication")
public class ResetPasswordController {
	
	@Autowired
	ResetPasswordService passwordService;
	

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
