package com.humanresourcemanagement.ResourceMangement.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

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
import com.humanresourcemanagement.ResourceMangement.Enum.Gender;
import com.humanresourcemanagement.ResourceMangement.Enum.Martial;
import com.humanresourcemanagement.ResourceMangement.Enum.Status;
import com.humanresourcemanagement.ResourceMangement.Payload.requestDto.ChangeRoleDto;
import com.humanresourcemanagement.ResourceMangement.Payload.requestDto.DepartmentDto;
import com.humanresourcemanagement.ResourceMangement.Payload.requestDto.DesignationDto;
import com.humanresourcemanagement.ResourceMangement.Payload.requestDto.FormDto;
import com.humanresourcemanagement.ResourceMangement.Payload.requestDto.VerifiedTokenDto;
import com.humanresourcemanagement.ResourceMangement.Payload.responseDto.EmployeeInfoDto;
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

import jakarta.validation.Valid;

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

	public ResponseEntity<?> getUserLists(String userRole, Pageable pageable) {
		Specification<User> filters = Specification.where(userRole!=null ? UserSpecification.getByRole(userRole) :null);
		if(userRole == null  || !userRole.equals("ROLE_EMPLOYEE") ) {
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
		} else {
			Page<User> userLists = userRepository.findAll(filters, pageable);
			if(userLists.isEmpty()) {
				return ResponseEntity.badRequest().body(new MessageResponse("Error: User List of employee is empty"));
			} else {
				List<EmployeeInfoDto> employeeInfoDtoLists = new ArrayList<>();
				for(User user: userLists) {
					Long id = user.getId();
					Optional<Employee> optionalEmployee = employeeRepo.findById(id);
					if(optionalEmployee.isPresent()) {
						Employee employee = optionalEmployee.get();
						EmployeeInfoDto employeeInfoDto = new EmployeeInfoDto();
						employeeInfoDto.setId(employee.getId());
						employeeInfoDto.setUsername(employee.getUsername().getUsername());
						employeeInfoDto.setEmail(employee.getUsername().getEmail());
						employeeInfoDto.setDepartment(employee.getDepartment().getName());
						employeeInfoDto.setDesignation(employee.getDesignation().getName());
						employeeInfoDto.setJoinDate(employee.getJoinDate());
						employeeInfoDto.setLeaveDate(employee.getLeaveDate());
						employeeInfoDto.setApprovedBy(employee.getUsername().getUsername());
						employeeInfoDtoLists.add(employeeInfoDto);
					} else {
						return ResponseEntity.badRequest().body(new MessageResponse("Error: Employee not found by id" + id));
					}
				}
				return ResponseEntity.ok().body(employeeInfoDtoLists);
			}
		}
	}

	public ResponseEntity<?> addDepartment(@Valid DepartmentDto departmentDto) {
		Department department = new Department();
		department.setName(departmentDto.getName());
		department.setBranch(departmentDto.getBranch());
		department.setAddress(departmentDto.getAddress());
		department.setPhone(departmentDto.getTel());
		departRepo.save(department);
		return ResponseEntity.ok().body(departmentDto);
	}

	public ResponseEntity<?> addDesgination(@Valid DesignationDto designationDto) {
		Designation designation = new Designation();
		designation.setName(designationDto.getName());
		Long id = designationDto.getDepartment();
		Optional<Department> optionalDepartment = departRepo.findById(id);
		if (optionalDepartment.isPresent()) {
			Department department = optionalDepartment.get();
			designation.setDepartment(department);
		}
		designationRepo.save(designation);
		return ResponseEntity.ok().body(designation);
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
		        } else {
		            // Handle other roles if needed
		            user.setRole(ERole.ROLE_USER);
		        }
		    }
			userRepository.save(user);
			UserDetailsImpl userDetailsImpl = (UserDetailsImpl) auth.getPrincipal();
			if(strRole.contains("employee")) {
				Employee employee = new Employee();
				employee.setUsername(user);
				employee.setJoinDate(changeRoleDto.getJoinDate());
				employee.setLeaveDate(changeRoleDto.getEndDate());
				
				Optional<Department> optionalDepartment = departRepo.findById(changeRoleDto.getDepartment());
				if(optionalDepartment.isPresent()) {
					employee.setDepartment(optionalDepartment.get());
					Optional<Designation> optionalDesignation= designationRepo.findById(changeRoleDto.getDesignation());
					if(optionalDesignation.isPresent()) {
						employee.setDesignation(optionalDesignation.get());
					} else {
						return ResponseEntity.badRequest().body(new MessageResponse("Error: designation not found by id " + changeRoleDto.getDesignation()));
					}
				} else {
					return ResponseEntity.badRequest().body(new MessageResponse("Error: department not found by id " + changeRoleDto.getDepartment()));
				}
				Long userId = userDetailsImpl.getId();
				Optional<User> optionUser = userRepository.findById(userId);
				if(optionUser.isPresent()) {
					User authUser = optionUser.get();
					employee.setApprover(authUser);
				} 
				employeeRepo.save(employee);
			}
			return ResponseEntity.ok().body("Succesfully changed Role");

		} else {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: User not found by id " + id));
		}
	}
	
}
