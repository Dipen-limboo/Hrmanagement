package com.humanresourcemanagement.ResourceMangement.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.humanresourcemanagement.ResourceMangement.Entity.Designation;
import com.humanresourcemanagement.ResourceMangement.Entity.Promotion;
import com.humanresourcemanagement.ResourceMangement.Entity.SubDepartment;
import com.humanresourcemanagement.ResourceMangement.Entity.User;
import com.humanresourcemanagement.ResourceMangement.Payload.requestDto.EmployeeUpdateDto;
import com.humanresourcemanagement.ResourceMangement.Payload.requestDto.PromotionDto;
import com.humanresourcemanagement.ResourceMangement.Payload.responseDto.MessageResponse;
import com.humanresourcemanagement.ResourceMangement.Payload.responseDto.PromotionResponseDto;
import com.humanresourcemanagement.ResourceMangement.Repository.DepartmentRepo;
import com.humanresourcemanagement.ResourceMangement.Repository.DesignationRepo;
import com.humanresourcemanagement.ResourceMangement.Repository.PromotionRepo;
import com.humanresourcemanagement.ResourceMangement.Repository.SubDepartmentRepo;
import com.humanresourcemanagement.ResourceMangement.Repository.UserRepository;
import com.humanresourcemanagement.ResourceMangement.security.service.UserDetailsImpl;

import jakarta.transaction.Transactional;
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
	
	
	public ResponseEntity<?> promoteEmployee(Long id, @Valid PromotionDto promotionDto, Authentication auth) {
		Optional<User> optionalUser = userRepo.findById(id);
		if(optionalUser.isPresent()) {
			User user = optionalUser.get();
			
			Promotion promotion = promotionRepo.findFirstByUserOrderByIdDesc(user);
			Promotion newPromotion = new Promotion();
				if(LocalDate.now().isAfter(promotionDto.getJoinDate())) {
					promotion.setEndDate(promotionDto.getJoinDate());
					newPromotion.setJoinDate(promotionDto.getJoinDate());
				} else {
					return ResponseEntity.badRequest().body(new MessageResponse("Error: Date must be in AD and is should not exceed the current date"));
				}
				newPromotion.setUser(user);
				Optional<Designation> designation = designationRepo.findById(promotionDto.getDesignation());
				if(designation.isPresent()) {
					if(promotionRepo.existsByUserAndDesignation(user, designation.get())) {
						return ResponseEntity.badRequest().body(new MessageResponse("Error: User " + user.getUsername() + " have already " + designation.get().getName() + " designation"));
					}
					newPromotion.setDesignation(designation.get());
				} else {
					return ResponseEntity.badRequest().body(new MessageResponse("Error: Designation not found by id" + promotionDto.getDesignation()));
				}
				
				Optional<SubDepartment> subDepartment = subDepartmentRepo.findById(promotionDto.getSubDepartment());
				if(subDepartment.isPresent()) {
					newPromotion.setSubDepartment(subDepartment.get());
				} else {
					return ResponseEntity.badRequest().body(new MessageResponse("Error: Sub Department not found by id" + promotionDto.getSubDepartment()));
				}
				
				UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
				Optional<User> optionalUserr = userRepo.findById(userDetails.getId());
				if(optionalUserr.isPresent()) {
					newPromotion.setApprover(optionalUserr.get());
				}
				
				if(newPromotion.getSubDepartment().getDepartment().getId() == newPromotion.getDesignation().getDepartment().getId()) {
					promotionRepo.save(promotion);
					promotionRepo.save(newPromotion);
					return ResponseEntity.ok().body(newPromotion);
				} else {
					return ResponseEntity.badRequest().body(new MessageResponse("Error: department are different in subDepartment and desgination"));
				}
			
		} else {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: user not found by id " + id));
		}
	}


	public ResponseEntity<?> getPromotionList(Authentication auth) {
		UserDetailsImpl userDetials = (UserDetailsImpl) auth.getPrincipal();
		Optional<User> optionalUser = userRepo.findById(userDetials.getId());
		if(optionalUser.isPresent()) {
			User user = optionalUser.get();
			List<Promotion> promotionList = promotionRepo.findByUser(user);
			List<PromotionResponseDto> promotionResponseDto = new ArrayList<>();
			
			if(promotionList.isEmpty()) {
				return ResponseEntity.badRequest().body(new MessageResponse("Error: There is no any detal of user with id " + userDetials.getId()));
			} else {
				for(Promotion promotion: promotionList) {
					PromotionResponseDto responseDto = new PromotionResponseDto();
					responseDto.setId(promotion.getId());
					responseDto.setJoin_date(promotion.getJoinDate());
					responseDto.setEnd_date(promotion.getEndDate());
					responseDto.setDesignation(promotion.getDesignation().getName());
					responseDto.setSub_department(promotion.getSubDepartment().getName());
					responseDto.setApproved_by(promotion.getApprover().getUsername());
					promotionResponseDto.add(responseDto);
				}
			}
			return ResponseEntity.ok().body(promotionResponseDto);
		}else {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: user not found by id " + userDetials.getId()));
		}
	}


	public ResponseEntity<?> getAllPromotionList(Pageable pageable) {
		Page<Promotion> promotionList = promotionRepo.findAll(pageable);
		List<PromotionResponseDto> promotionResponseDto = new ArrayList<>();
		
		if(promotionList.isEmpty()) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: There is no any details!! "));
		} else {
			for(Promotion promotion: promotionList.getContent()) {
				PromotionResponseDto responseDto = new PromotionResponseDto();
				responseDto.setId(promotion.getId());
				responseDto.setJoin_date(promotion.getJoinDate());
				responseDto.setEnd_date(promotion.getEndDate());
				responseDto.setDesignation(promotion.getDesignation().getName());
				responseDto.setSub_department(promotion.getSubDepartment().getName());
				responseDto.setApproved_by(promotion.getApprover().getUsername());
				promotionResponseDto.add(responseDto);
			}
		}
		return ResponseEntity.ok().body(promotionResponseDto);
		}


	public ResponseEntity<?> getPromotionListById(Long id) {
		Optional<User> optionalUser = userRepo.findById(id);
		if(!optionalUser.isPresent()) 
			return ResponseEntity.badRequest().body(new MessageResponse("Error: user not found by id " + id));
	
		
		
			User user = optionalUser.get();
			List<Promotion> promotionList = promotionRepo.findByUser(user);
			List<PromotionResponseDto> promotionResponseDto = new ArrayList<>();
			if(promotionList.isEmpty())
				return ResponseEntity.badRequest().body(new MessageResponse("Error: There is no any details!! "));
			for(Promotion promotion: promotionList) {
				PromotionResponseDto responseDto = new PromotionResponseDto();
				responseDto.setId(promotion.getId());
				responseDto.setJoin_date(promotion.getJoinDate());
				responseDto.setEnd_date(promotion.getEndDate());
				responseDto.setDesignation(promotion.getDesignation().getName());
				responseDto.setSub_department(promotion.getSubDepartment().getName());
				responseDto.setApproved_by(promotion.getApprover().getUsername());
				promotionResponseDto.add(responseDto);
			}
			
			return ResponseEntity.ok().body(promotionResponseDto);
		
	}

	@Transactional
	public ResponseEntity<?> updatePromotion(Long id, PromotionDto promotionDto) {
	    Optional<Promotion> optionalPromotion = promotionRepo.findById(id);
	    if (!optionalPromotion.isPresent()) 
	    	return ResponseEntity.badRequest().body(new MessageResponse("Error: Promotion not found by id " + id));
	    
	    Promotion promotion = optionalPromotion.get();
	    User user = promotion.getUser();
	    Promotion promo = promotionRepo.findByEndDateAndUser(promotion.getJoinDate(), user );
	 
    	if(promotionDto.getDesignation() != null) {
	    	Optional<Designation> designation = designationRepo.findById(promotionDto.getDesignation());
	    	if(promotionRepo.existsByUserAndDesignation(user, designation.get()))
	    		return ResponseEntity.badRequest().body(new MessageResponse("Error: User " + user.getFirstName() +" "+user.getLastName() + " already have the given designation " + designation.get().getName()));
	    	if(!designation.isPresent())
	    		return ResponseEntity.badRequest().body(new MessageResponse("Error: Designation not found in database"));
	    	 if(promotionDto.getSubDepartment() != null) {
	 	    	Optional<SubDepartment> subDepartment = subDepartmentRepo.findById(promotionDto.getSubDepartment());
	 	    	if(!subDepartment.isPresent()) 
	 	    		return ResponseEntity.badRequest().body(new MessageResponse("Error: Sub Department not found in database"));
	 	    	if(!promotion.getSubDepartment().getDepartment().getId().equals(designation.get().getDepartment().getId()) || !subDepartment.get().getDepartment().getId().equals(promotion.getDesignation().getDepartment().getId()) ) {
	 				return ResponseEntity.badRequest().body(new MessageResponse("Error: department are different in subDepartment and desgination"));
	 	    	}
	 	    	promotion.setSubDepartment(subDepartment.get());
	 	       	promotion.setDesignation(designation.get());
	 	    	 	
	    	 }
	    } else {
	    	promotion.setDesignation(promotion.getDesignation());
	    }
    	if(promotionDto.getJoinDate() != null && LocalDate.now().isAfter(promotionDto.getJoinDate())) {
	    	promotion.setJoinDate(promotionDto.getJoinDate());
	    	promo.setEndDate(promotionDto.getJoinDate());
	    } else {	
	  	    	promotion.setJoinDate(promotion.getJoinDate());
	    }
    	promotion.setUser(promotion.getUser());
	    
	    promotion.setApprover(promotion.getApprover());
	    promotionRepo.save(promotion);
		promotionRepo.save(promo);
		return ResponseEntity.ok().body(promotion);
	     
	    
	}

	
	
}
