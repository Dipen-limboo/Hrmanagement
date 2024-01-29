package com.humanresourcemanagement.ResourceMangement.Service;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.humanresourcemanagement.ResourceMangement.Entity.Department;
import com.humanresourcemanagement.ResourceMangement.Entity.Designation;
import com.humanresourcemanagement.ResourceMangement.Entity.User;
import com.humanresourcemanagement.ResourceMangement.Payload.requestDto.EmployeeUpdateDto;
import com.humanresourcemanagement.ResourceMangement.Payload.responseDto.MessageResponse;
import com.humanresourcemanagement.ResourceMangement.Repository.DepartmentRepo;
import com.humanresourcemanagement.ResourceMangement.Repository.DesignationRepo;
import com.humanresourcemanagement.ResourceMangement.Repository.UserRepository;
import com.humanresourcemanagement.ResourceMangement.security.service.UserDetailsImpl;

import jakarta.validation.Valid;

@Service
public class EmployeeService {
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	DepartmentRepo departRepo;
	
	@Autowired
	DesignationRepo designationRepo;
	
	//update user
	public ResponseEntity<?> saveEmployee(Long id, @Valid EmployeeUpdateDto employeeDto, Authentication auth) {
		Optional<User> optionalUser = userRepo.findById(id);
		if(optionalUser.isPresent()) {
			User user = optionalUser.get();
			LocalDate now = LocalDate.now();
			if(now.isAfter(employeeDto.getJoinDate())) {
				user.setJoinDate(employeeDto.getJoinDate());
			} else {
				return ResponseEntity.badRequest().body(new MessageResponse("Error: You should add the date in AD. Join date mustnot exceed the current date"));
			} 
			if(now.isAfter(employeeDto.getLeaveDate())) {
				user.setLeaveDate(employeeDto.getLeaveDate());
			} else {
				return ResponseEntity.badRequest().body(new MessageResponse("Error: You should add the date in AD. Leave date mustnot exceed the current date"));
			}
			Long departId = employeeDto.getDepartment();
			Optional<Department> optionalDepartment = departRepo.findById(departId);
			if(optionalDepartment.isPresent()) {
				user.setDepartment(optionalDepartment.get());
				Long designationId = employeeDto.getDesignation();
				Optional<Designation> optionalDesignation = designationRepo.findById(designationId);
				if(optionalDesignation.isPresent()) {
					user.setDesignation(optionalDesignation.get());
					
					UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
					Optional<User> optionalUserr = userRepo.findById(userDetails.getId());
					if(optionalUserr.isPresent()) {
						user.setApprover(optionalUserr.get().getId());
					}
					userRepo.save(user);
					return ResponseEntity.ok().body("Succesfully added");
				} else {
					return ResponseEntity.badRequest().body(new MessageResponse("Error: Designation not found by id " + designationId));
				}
			} else {
				return ResponseEntity.badRequest().body(new MessageResponse("Error: Department not found by id " + departId));
			}
		} else {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: user not found by id " + id));
		}
	
	}
	
	
}
