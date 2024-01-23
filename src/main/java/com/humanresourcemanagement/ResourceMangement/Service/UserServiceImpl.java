package com.humanresourcemanagement.ResourceMangement.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.humanresourcemanagement.ResourceMangement.Entity.User;
import com.humanresourcemanagement.ResourceMangement.Enum.ERole;
import com.humanresourcemanagement.ResourceMangement.Enum.Gender;
import com.humanresourcemanagement.ResourceMangement.Enum.Martial;
import com.humanresourcemanagement.ResourceMangement.Enum.Status;
import com.humanresourcemanagement.ResourceMangement.Payload.requestDto.FormDto;
import com.humanresourcemanagement.ResourceMangement.Payload.requestDto.VerifiedTokenDto;
import com.humanresourcemanagement.ResourceMangement.Payload.responseDto.JwtResponse;
import com.humanresourcemanagement.ResourceMangement.Payload.responseDto.MessageResponse;
import com.humanresourcemanagement.ResourceMangement.Payload.responseDto.ResponseFormDto;
import com.humanresourcemanagement.ResourceMangement.Repository.UserRepository;
import com.humanresourcemanagement.ResourceMangement.security.jwt.JwtUtils;
import com.humanresourcemanagement.ResourceMangement.security.service.UserDetailsImpl;

import jakarta.validation.Valid;

@Service
public class UserServiceImpl {
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	UserRepository userRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;
	
	@Autowired
	JavaMailSender mailSender;
	
	public ResponseEntity<?> signIn(@Valid ResponseFormDto loginRequest) {
		Authentication authentication = authenticationManager
			       .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

					SecurityContextHolder.getContext().setAuthentication(authentication);
					
					UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
					if(!userDetails.isVerified()) {
						return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse("ERROR: Verified your email to singup first"));
					}
					if (userDetails.getStatus().equals(Status.DEACTIVE)) {
					    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse("Error: You are deactivated by admin"));
					}
					String token = jwtUtils.generateJwtToken(authentication);
					List<String> roles = userDetails.getAuthorities().stream()
							.map(item -> item.getAuthority())
							.collect(Collectors.toList());
					return ResponseEntity.ok(new JwtResponse(token, 
							userDetails.getId(),
							userDetails.getFirstname(),
							userDetails.getMiddlename(),
							userDetails.getLastname(),
							userDetails.getDateOfbirth(),
							userDetails.getPhone(),
							userDetails.getUsername(),
							userDetails.getEmail(),
							roles,
							userDetails.getStatus().toString(),
							userDetails.getGender().toString(),
							userDetails.getMartial().toString())
							);
	}

	public ResponseEntity<?> signup(@Valid FormDto signUpRequest) {
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
		}

		User user = new User();
		user.setFirstName(signUpRequest.getFirstName());
		user.setMiddleName(signUpRequest.getMiddleName());
		user.setLastName(signUpRequest.getLastName());
		user.setUsername(signUpRequest.getUsername());
		user.setBirthDate(signUpRequest.getDateOfBirth());
		user.setPhone(signUpRequest.getPhone());
		user.setEmail(signUpRequest.getEmail());
		
		Set<String> genderStr = signUpRequest.getGender();
		if(genderStr == null) {
			user.setGender(null);
		} else {
			genderStr.forEach(gender ->{
				switch(gender) {
				case "male":
					user.setGender(Gender.MALE);
					break;
				case "female":
					user.setGender(Gender.FEMALE);
					break;
				default:
					user.setGender(Gender.OTHER);
				}
			});
		}
		
		Set<String> martialStr = signUpRequest.getMaritalStatus();
		if(martialStr == null) {
			user.setMartialStatus(null);
		} else {
			martialStr.forEach(martial -> {
				switch (martial) {
				case "single":
					user.setMartialStatus(Martial.SINGLE);	
					break;
				default:
					user.setMartialStatus(Martial.MARRIED);
				}
			});
		}
		user.setPassword(encoder.encode(signUpRequest.getPassword()));
		user.setVerifiedDate(new Date());
		//verifying the singup by generating token 
		String token = UUID.randomUUID().toString();
		
		String email = signUpRequest.getEmail();
		sendTokenToEmail(token, email);
		user.setVerifiedToken(token);
		
		Set<String> strRoles = signUpRequest.getRole();
	

		if (strRoles == null) {
			user.setRole(ERole.ROLE_USER);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "admin":
					user.setRole(ERole.ROLE_ADMIN);
					break;
				case "mod":
					user.setRole(ERole.ROLE_EMPLOYEE);
					break;
				default:
					user.setRole(ERole.ROLE_USER);
				}
			});
		}
		userRepository.save(user);
		return ResponseEntity.ok(new MessageResponse("Check you mail and verified to register!"));
	}


	public ResponseEntity<?> verified(VerifiedTokenDto verifiedDto) {
		Optional<User> optionalUser = userRepository.findByVerifiedToken(verifiedDto.getToken());
		if(optionalUser.isPresent()) {
			User user = optionalUser.get();
			user.setVerifiedToken(null);
			user.setVerified(true);
			userRepository.save(user);
			return ResponseEntity.ok().body(new MessageResponse(user.getEmail() + " is verified!!"));
		} else {
			return ResponseEntity.badRequest().body(new MessageResponse("ERROR: Invalid token"));
		}
	}
	
	private void sendTokenToEmail(String token, String email) {
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setFrom("dipenlimboo564@gmail.com");
		mailMessage.setTo(email);
		String subject = "User Verification Request";
		String text = "To verified your register request, click the following link: "
                + "http://localhost:8081/api/user/verifiedToken?token=" + token;
		mailMessage.setSubject(subject);
		mailMessage.setText(text);
		mailSender.send(mailMessage);
	}

}
