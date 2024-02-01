package com.humanresourcemanagement.ResourceMangement.Service;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.humanresourcemanagement.ResourceMangement.Entity.Designation;
import com.humanresourcemanagement.ResourceMangement.Entity.Promotion;
import com.humanresourcemanagement.ResourceMangement.Entity.SubDepartment;
import com.humanresourcemanagement.ResourceMangement.Entity.User;
import com.humanresourcemanagement.ResourceMangement.Payload.requestDto.EmployeeUpdateDto;
import com.humanresourcemanagement.ResourceMangement.Payload.responseDto.MessageResponse;
import com.humanresourcemanagement.ResourceMangement.Repository.DepartmentRepo;
import com.humanresourcemanagement.ResourceMangement.Repository.DesignationRepo;
import com.humanresourcemanagement.ResourceMangement.Repository.PromotionRepo;
import com.humanresourcemanagement.ResourceMangement.Repository.SubDepartmentRepo;
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
	
	@Autowired
	SubDepartmentRepo subDepartmentRepo;
	
	@Autowired
	PromotionRepo promotionRepo;
	//update user
	public ResponseEntity<?> saveEmployee(Long id, @Valid EmployeeUpdateDto employeeDto, Authentication auth) {
		Optional<User> optionalUser = userRepo.findById(id);
		if(optionalUser.isPresent()) {
			User user = optionalUser.get();
			
			Promotion promotedUser = new Promotion();
			promotedUser.setUser(user);
			
			LocalDate now = LocalDate.now();
			if(employeeDto.getJoinDate() == null) {
				user.setJoinDate(user.getJoinDate());
			} else {
				if(now.isAfter(employeeDto.getJoinDate())) {
					user.setJoinDate(employeeDto.getJoinDate());
					promotedUser.setJoinDate(employeeDto.getJoinDate());
				} else {
					return ResponseEntity.badRequest().body(new MessageResponse("Error: You should add the date in AD. Join date mustnot exceed the current date"));
				} 
			}
			
			promotedUser.setEndDate(null);
		
			UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
			Optional<User> optionalUserr = userRepo.findById(userDetails.getId());
			if(optionalUserr.isPresent()) {
				promotedUser.setApprover(optionalUserr.get());
			}
			
			if(employeeDto.getSubDepartment() == null) {
				promotedUser.setSubDepartment(null);
			} else {
				Long subdepartId = employeeDto.getSubDepartment();
				Optional<SubDepartment> optionalSubDepartment = subDepartmentRepo.findById(subdepartId);
				if(optionalSubDepartment.isPresent()) {
					promotedUser.setSubDepartment(optionalSubDepartment.get());
				}  else {
					return ResponseEntity.badRequest().body(new MessageResponse("Error: SubDepartment not found by id " + subdepartId));
				}
			}
			
			if(employeeDto.getDesignation() == null) {
				promotedUser.setDesignation(null);
			} else {
				Optional<Designation> designation = designationRepo.findById(employeeDto.getDesignation());
				if(designation.isPresent()) {
					promotedUser.setDesignation(designation.get());
				} else {
					return ResponseEntity.badRequest().body(new MessageResponse("Error: Designation not found by id" + employeeDto.getDesignation()));
				}
			}
			
			if(promotedUser.getSubDepartment().getDepartment().getId()== promotedUser.getDesignation().getDepartment().getId()) {
				userRepo.save(user);
				promotionRepo.save(promotedUser);
				return ResponseEntity.ok().body(promotedUser);
			} else {
				return ResponseEntity.badRequest().body(new MessageResponse("Error: department are different in subDepartment and desgination"));
			}
		} else {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: user not found by id " + id));
		}
	
	}
	
	
}
