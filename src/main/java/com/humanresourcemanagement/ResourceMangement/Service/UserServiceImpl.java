package com.humanresourcemanagement.ResourceMangement.Service;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
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

import com.humanresourcemanagement.ResourceMangement.Entity.User;
import com.humanresourcemanagement.ResourceMangement.Enum.ERole;
import com.humanresourcemanagement.ResourceMangement.Enum.Gender;
import com.humanresourcemanagement.ResourceMangement.Enum.Martial;
import com.humanresourcemanagement.ResourceMangement.Enum.Status;
import com.humanresourcemanagement.ResourceMangement.Payload.requestDto.ChangeRoleDto;
import com.humanresourcemanagement.ResourceMangement.Payload.requestDto.ChangedPasswordDto;
import com.humanresourcemanagement.ResourceMangement.Payload.requestDto.EmployeeRegisterDto;
import com.humanresourcemanagement.ResourceMangement.Payload.requestDto.UserFormDto;
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

	
	public ResponseEntity<?> getUser(Authentication auth) {
		UserDetailsImpl user= (UserDetailsImpl) auth.getPrincipal();
		UserInfoDto userInfoDto = new UserInfoDto();
		userInfoDto.setId(user.getId());
		userInfoDto.setFirstName(user.getFirstname());
		userInfoDto.setMiddleName(user.getMiddlename());
		userInfoDto.setLastName(user.getLastname());
		userInfoDto.setEmail(user.getEmail());
		userInfoDto.setPhone(user.getPhone());
		if(user.getGender() == null) {
			userInfoDto.setGender(null);
		} else {
			userInfoDto.setGender(user.getGender().toString());
		} 
		if(user.getMartial()==null) {
			userInfoDto.setMartial(null);
		} else {
			userInfoDto.setMartial(user.getMartial().toString());
		}
		
		userInfoDto.setRole(user.getAuthorities().toString());
		userInfoDto.setStatus(user.getStatus().toString());
		return ResponseEntity.ok().body(userInfoDto);
	}
	
	public ResponseEntity<?> updateUser(Authentication auth, @Valid UserFormDto formDto) {
		UserDetailsImpl userDetails= (UserDetailsImpl) auth.getPrincipal();
		Long id = userDetails.getId();
		Optional<User> optionalUser = userRepository.findById(id);
		if(optionalUser.isPresent()) {
			User user = optionalUser.get();
			user.setFirstName(formDto.getFirstName());
			user.setMiddleName(user.getMiddleName());
			user.setLastName(formDto.getLastName());
			LocalDate currentDate = LocalDate.now();
			if(currentDate.isAfter(formDto.getDateOfBirth())) {
				user.setBirthDate(formDto.getDateOfBirth());
				user.setAddress(formDto.getAddress());
				user.setEmail(formDto.getEmail());
				Set<String> strGender= formDto.getGender();
				if(strGender == null) {
					user.setGender(null);
				} else {
					strGender.forEach(gender -> {
						switch(gender) {
						case "male":
							user.setGender(Gender.MALE);
							break;
						case "female":
							user.setGender(Gender.FEMALE);
							break;
						case "other":
							user.setGender(Gender.OTHER);
							break;
						default:
							user.setGender(null);
						}
					});
				}
				
				Set<String> strMartial = formDto.getMartial();
				if(strMartial == null) {
					user.setMartialStatus(null);
				} else {
					strMartial.forEach(martial -> {
						switch(martial) {
						case "single": 
							user.setMartialStatus(Martial.SINGLE);
							break;
						case "married":
							user.setMartialStatus(Martial.MARRIED);
							break;
						default:
							user.setMartialStatus(null);
						}
					});
				}
				user.setPhone(formDto.getPhone());
				user.setUsername(formDto.getUsername());
				userRepository.save(user);
			} else {
				return ResponseEntity.badRequest().body(new MessageResponse("Error: Date of birth should be in AD. Check if you have set the year more than current date "));
			}
			return ResponseEntity.ok().body(user); 
		} else {
			return ResponseEntity.badRequest().body(new MessageResponse("Error:User not found by id"+ id));
		}
		
	}

	
}
