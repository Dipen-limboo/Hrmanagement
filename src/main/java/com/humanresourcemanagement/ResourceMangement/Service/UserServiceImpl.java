package com.humanresourcemanagement.ResourceMangement.Service;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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

import com.humanresourcemanagement.ResourceMangement.Entity.Department;
import com.humanresourcemanagement.ResourceMangement.Entity.Designation;
import com.humanresourcemanagement.ResourceMangement.Entity.Employee;
import com.humanresourcemanagement.ResourceMangement.Entity.User;
import com.humanresourcemanagement.ResourceMangement.Enum.ERole;
import com.humanresourcemanagement.ResourceMangement.Enum.Status;
import com.humanresourcemanagement.ResourceMangement.Payload.requestDto.ChangeRoleDto;
import com.humanresourcemanagement.ResourceMangement.Payload.requestDto.ChangedPasswordDto;
import com.humanresourcemanagement.ResourceMangement.Payload.requestDto.EmployeeRegisterDto;
import com.humanresourcemanagement.ResourceMangement.Payload.responseDto.JwtResponse;
import com.humanresourcemanagement.ResourceMangement.Payload.responseDto.MessageResponse;
import com.humanresourcemanagement.ResourceMangement.Payload.responseDto.ResponseFormDto;
import com.humanresourcemanagement.ResourceMangement.Payload.responseDto.UserInfoDto;
import com.humanresourcemanagement.ResourceMangement.Repository.DepartmentRepo;
import com.humanresourcemanagement.ResourceMangement.Repository.DesignationRepo;
import com.humanresourcemanagement.ResourceMangement.Repository.EmployeeRepo;
import com.humanresourcemanagement.ResourceMangement.Repository.UserRepository;
import com.humanresourcemanagement.ResourceMangement.Specification.UserSpecification;
import com.humanresourcemanagement.ResourceMangement.security.jwt.JwtUtils;
import com.humanresourcemanagement.ResourceMangement.security.service.UserDetailsImpl;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import net.bytebuddy.utility.RandomString;

@Service
public class UserServiceImpl {
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	EmployeeRepo employeeRepo;
	
	@Autowired
	DepartmentRepo departRepo;

	@Autowired
	DesignationRepo designationRepo;
	
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
					
					if(!userDetails.isChanged()) {
						return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse("ERROR: Changed your password to singIn first"));
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
							userDetails.getStatus().toString())
							);
	}

	public ResponseEntity<?> signup(@Valid EmployeeRegisterDto employeeDto, Authentication auth, HttpServletRequest request) throws UnsupportedEncodingException, MessagingException {
		
		if(userRepository.existsByEmail(employeeDto.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: "+employeeDto.getEmail() +" already exists."));
		}
		User user = new User();
		user.setFirstName(employeeDto.getFirstName());
		user.setMiddleName(employeeDto.getMiddleName());
		user.setLastName(employeeDto.getLastName());
		user.setEmail(employeeDto.getEmail());
		String randomPassword = RandomStringUtils.randomAlphanumeric(8);
	    user.setPassword(encoder.encode(randomPassword)); 
		String token = RandomString.make(64);
		user.setVerifiedToken(token);
		String email = employeeDto.getEmail();
		sendTokenToEmail(token, email, randomPassword);
		
		userRepository.save(user);
		return ResponseEntity.ok().body("Check your email");
	}

	public ResponseEntity<?> verified(String token, @Valid ChangedPasswordDto verifiedDto) {
		
		Optional<User> optionalUser = userRepository.findByVerifiedTokenAndEmail(token, verifiedDto.getEmail());
		if(optionalUser.isPresent()) {
			User user = optionalUser.get();
			user.setVerifiedToken(null);
			user.setVerified(true);
			user.setPasswordChange(true);
			String password = verifiedDto.getPassword();
			String confirmPassword = verifiedDto.getConfirmPassword();
			if(password.equals(confirmPassword)) {
				user.setPassword(encoder.encode(confirmPassword));
			} else {
				return ResponseEntity.badRequest().body(new MessageResponse("ERROR: Password and Confirm password must be same"));
			}
			userRepository.save(user);
			return ResponseEntity.ok().body(new MessageResponse(user.getEmail() + " has changed password succesfully"));
		} else {
			return ResponseEntity.badRequest().body(new MessageResponse("ERROR: Invalid token for the " + verifiedDto.getEmail()));
		}
	}
	
	private void sendTokenToEmail(String token, String email, String randomPassword) {
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setFrom("dipenlimboo564@gmail.com");
		mailMessage.setTo(email);
		String subject = "User Verification Request";
		String text = "Your password is " +randomPassword+ "   "
				+ " To verified your register request, click the following link: "
                + "http://localhost:8081/api/user/changePassword?token=" + token;
		mailMessage.setSubject(subject);
		mailMessage.setText(text);
		mailSender.send(mailMessage);
	}

	public ResponseEntity<?> getUserLists(String userRole, Pageable pageable) {
		Specification<User> filters = Specification.where(userRole!=null ? UserSpecification.getByRole(userRole) :null);

			Page<User> userLists = userRepository.findAll(filters, pageable);
			
			if(userLists.isEmpty()) {
				return ResponseEntity.badRequest().body(new MessageResponse("Error: User List is empty"));
			} else {
				List<UserInfoDto> userInfoDtolists = new ArrayList<>();
				for(User user: userLists) {
					UserInfoDto userdto = new UserInfoDto();
					userdto.setId(user.getId());
					userdto.setFirstName(user.getFirstName());
					userdto.setMiddleName(user.getMiddleName());
					userdto.setLastName(user.getLastName());
					userdto.setEmail(user.getEmail());
					userdto.setPhone(user.getPhone());
					userdto.setRole(user.getRole().toString());
					userInfoDtolists.add(userdto);
				}
				return ResponseEntity.ok().body(userInfoDtolists);
			}
	}

	
	
	public ResponseEntity<?> changeRole(Long id, ChangeRoleDto changeRoleDto, Authentication auth) {
		Optional<User> optionalUser = userRepository.findById(id);
		if(optionalUser.isPresent()) {
			User user = optionalUser.get();
			Set<String> strRole = changeRoleDto.getRole();
			if(strRole == null) {
				user.setRole(ERole.ROLE_USER);
			} else {
		        if (strRole.contains("admin")) {
		            user.setRole(ERole.ROLE_ADMIN);
		        } else if (strRole.contains("employee")) {
		            user.setRole(ERole.ROLE_EMPLOYEE);
		        } else if (strRole.contains("superadmin")){
		            // Handle other roles if needed
		            user.setRole(ERole.ROLE_SUPERADMIN);
		        } else {
		        	user.setRole(null);
		        }
		    }
			userRepository.save(user);
			
			return ResponseEntity.ok().body("Succesfully changed Role");

		} else {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: User not found by id " + id));
		}
	}
	
}
