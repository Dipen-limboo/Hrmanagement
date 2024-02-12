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

import com.humanresourcemanagement.ResourceMangement.Entity.Branch;
import com.humanresourcemanagement.ResourceMangement.Entity.Designation;
import com.humanresourcemanagement.ResourceMangement.Entity.EmployeeOfficialInfo;
import com.humanresourcemanagement.ResourceMangement.Entity.Grade;
import com.humanresourcemanagement.ResourceMangement.Entity.JobType;
import com.humanresourcemanagement.ResourceMangement.Entity.Promotion;
import com.humanresourcemanagement.ResourceMangement.Entity.SubDepartment;
import com.humanresourcemanagement.ResourceMangement.Entity.Transfer;
import com.humanresourcemanagement.ResourceMangement.Entity.User;
import com.humanresourcemanagement.ResourceMangement.Entity.WorkingType;
import com.humanresourcemanagement.ResourceMangement.Enum.Status;
import com.humanresourcemanagement.ResourceMangement.Payload.requestDto.EmployeeUpdateDto;
import com.humanresourcemanagement.ResourceMangement.Payload.requestDto.OffiEmployeeUpdateDto;
import com.humanresourcemanagement.ResourceMangement.Payload.requestDto.PromotionDto;
import com.humanresourcemanagement.ResourceMangement.Payload.requestDto.TransferDto;
import com.humanresourcemanagement.ResourceMangement.Payload.responseDto.EmployeeOfficialResponseDto;
import com.humanresourcemanagement.ResourceMangement.Payload.responseDto.MessageResponse;
import com.humanresourcemanagement.ResourceMangement.Payload.responseDto.PromotionResponseDto;
import com.humanresourcemanagement.ResourceMangement.Repository.BranchRepo;
import com.humanresourcemanagement.ResourceMangement.Repository.DepartmentRepo;
import com.humanresourcemanagement.ResourceMangement.Repository.DesignationRepo;
import com.humanresourcemanagement.ResourceMangement.Repository.EmployeeOfficialInfoRepo;
import com.humanresourcemanagement.ResourceMangement.Repository.GradeRepo;
import com.humanresourcemanagement.ResourceMangement.Repository.JobTypeRepo;
import com.humanresourcemanagement.ResourceMangement.Repository.PromotionRepo;
import com.humanresourcemanagement.ResourceMangement.Repository.SubDepartmentRepo;
import com.humanresourcemanagement.ResourceMangement.Repository.TransferRepo;
import com.humanresourcemanagement.ResourceMangement.Repository.UserRepository;
import com.humanresourcemanagement.ResourceMangement.Repository.WokingTypeRepo;
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
	
	@Autowired
	EmployeeOfficialInfoRepo empRepo;
	
	@Autowired
	WokingTypeRepo workingRepo;
	
	@Autowired
	JobTypeRepo jobRepo;
	
	@Autowired
	GradeRepo gradeRepo;
	
	@Autowired
	BranchRepo branchRepo;
	
	@Autowired
	TransferRepo transferRepo;
	
	//update user
	public ResponseEntity<?> saveEmployee(Long id, @Valid EmployeeUpdateDto employeeDto, Authentication auth) {
		Optional<User> optionalUser = userRepo.findById(id);
		if(optionalUser.isPresent()) {
			User user = optionalUser.get();
			
			Promotion promotedUser = new Promotion();
			EmployeeOfficialInfo emp = new EmployeeOfficialInfo();
			promotedUser.setUser(user);
			emp.setUser(user);
			emp.setId("emp0"+user.getId());
			
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
					emp.setSection(optionalSubDepartment.get());
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
					emp.setDesignation(designation.get());
				} else {
					return ResponseEntity.badRequest().body(new MessageResponse("Error: Designation not found by id" + employeeDto.getDesignation()));
				}
			}
			
			Optional<WorkingType> workingType = workingRepo.findById((long) 1);
			if(workingType.isEmpty())
				return ResponseEntity.badRequest().body(new MessageResponse("Error: Not such type of work"));
			Optional<JobType> jobType = jobRepo.findById(employeeDto.getJobType());
			if(jobType.isEmpty())
				return ResponseEntity.badRequest().body(new MessageResponse("Error: Not such type of job"));
			Optional<Grade> grade = gradeRepo.findById(employeeDto.getGrade());
			if(!grade.isPresent())
				return ResponseEntity.badRequest().body(new MessageResponse("Error: Not such type of grade"));
			Optional<Branch> branch = branchRepo.findById(employeeDto.getBranch());
			if(!branch.isPresent())
				return ResponseEntity.badRequest().body(new MessageResponse("Error: Not such type of branch"));
			emp.setWorkingType(workingType.get());
			emp.setJobType(jobType.get());
			emp.setBranch(branch.get());
			emp.setGrade(grade.get());
			
			promotedUser.setBranch(branch.get());
			promotedUser.setRemarks(employeeDto.getRemarks());
			
			if(promotedUser.getSubDepartment().getDepartment().getId()== promotedUser.getDesignation().getDepartment().getId()) {
				userRepo.save(user);
				promotionRepo.save(promotedUser);
				empRepo.save(emp);
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
			Optional<EmployeeOfficialInfo> employee = empRepo.findByUser(user); 
			if(!employee.isPresent())
				return ResponseEntity.badRequest().body(new MessageResponse("Error: Doesnot have employee detials of user " + user.getId()));
			EmployeeOfficialInfo emp = employee.get();
 			Promotion promotion = promotionRepo.findFirstByUserOrderByIdDesc(user);
			Promotion newPromotion = new Promotion();
				if(LocalDate.now().isAfter(promotionDto.getJoinDate())) {
					promotion.setEndDate(promotionDto.getJoinDate());
					promotion.setStatus(false);
					newPromotion.setJoinDate(promotionDto.getJoinDate());
				} else {
					return ResponseEntity.badRequest().body(new MessageResponse("Error: Date must be in AD and is should not exceed the current date"));
				}
				newPromotion.setUser(user);
				Optional<Designation> designation = designationRepo.findById(promotionDto.getDesignation());
				if(designation.isPresent()) {
					if(promotion.getDesignation().getId() == designation.get().getId())
						return ResponseEntity.badRequest().body(new MessageResponse("Cannot promote in the current desgination"));
					newPromotion.setDesignation(designation.get());
					emp.setDesignation(designation.get());
				} else {
					return ResponseEntity.badRequest().body(new MessageResponse("Error: Designation not found by id" + promotionDto.getDesignation()));
				}
				
				Optional<SubDepartment> subDepartment = subDepartmentRepo.findById(promotionDto.getSubDepartment());
				if(subDepartment.isPresent()) {
					newPromotion.setSubDepartment(subDepartment.get());
					emp.setSection(subDepartment.get());
				} else {
					return ResponseEntity.badRequest().body(new MessageResponse("Error: Sub Department not found by id" + promotionDto.getSubDepartment()));
				}
				
				UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
				Optional<User> optionalUserr = userRepo.findById(userDetails.getId());
				if(optionalUserr.isPresent()) {
					newPromotion.setApprover(optionalUserr.get());
				}
				
				if(promotionDto.getBranch() == null) {newPromotion.setBranch(null);} else {
					Optional<Branch> branch = branchRepo.findById(promotionDto.getBranch());
					if(!branch.isPresent())
						return ResponseEntity.badRequest().body(new MessageResponse("Error: Not such type of branch"));
					newPromotion.setBranch(branch.get());
					emp.setBranch(branch.get());
				}
				
				if(newPromotion.getSubDepartment().getDepartment().getId() == newPromotion.getDesignation().getDepartment().getId()) {
					empRepo.save(emp);
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


	public ResponseEntity<?> deletePromotion(Long id) {
		Optional<Promotion> optionalPromotion = promotionRepo.findById(id);
		if(optionalPromotion.isPresent()) {
			promotionRepo.deleteById(id);
			return ResponseEntity.ok().body("Succesfully deleted the promotion with id " +id);
		} else {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: There is no any promotion with id " +id));
		}
		
	}


	public ResponseEntity<?> transferEmp(Long id, @Valid TransferDto transferDto, Authentication auth) {
		UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
		Optional<User> userOption = userRepo.findById(userDetails.getId());
		if(!userOption.isPresent())
			return ResponseEntity.badRequest().body(new MessageResponse("User not found with id " +id));
		Optional<User> optionalUser = userRepo.findById(id);
		if(optionalUser.isEmpty())
			return ResponseEntity.badRequest().body(new MessageResponse("User not found with id " +id));
		User user = optionalUser.get();
		Optional<EmployeeOfficialInfo> empOptional = empRepo.findByUser(user);
		if(!empOptional.isPresent())
			return ResponseEntity.badRequest().body(new MessageResponse("Employee details is not enlisted in the organization with user id "+id));
		EmployeeOfficialInfo emp = empOptional.get();
		Transfer transfer = new Transfer();
		Optional<Branch> branch = branchRepo.findById(transferDto.getBranch_id());
		if(!branch.isPresent())
			return ResponseEntity.badRequest().body(new MessageResponse("Branch not found in the list"));
		Optional<Designation> designation = designationRepo.findById(transferDto.getDesignation_id());
		if(!designation.isPresent())
			return ResponseEntity.badRequest().body(new MessageResponse("Designation not found in the list"));
		Optional<SubDepartment> section = subDepartmentRepo.findById(transferDto.getSection_id());
		if(!section.isPresent())
			return ResponseEntity.badRequest().body(new MessageResponse("Section not found in the list"));
		if(!section.get().getDepartment().getId().equals(designation.get().getDepartment().getId()))
			return ResponseEntity.badRequest().body(new MessageResponse("Department are different in the section and designation.. "));
		Optional<Promotion> promotion = promotionRepo.findByUserAndSubDepartmentAndDesignation(user, section.get(), designation.get());
		if(promotion.isPresent()) {
			Promotion promo = promotion.get();
			promo.setBranch(branch.get());
			emp.setBranch(branch.get());
			promotionRepo.save(promo);
		} else {
			Promotion newPromotion = new Promotion();
			Promotion promo = promotionRepo.findFirstByUserOrderByIdDesc(user);
			if(!LocalDate.now().isAfter(transferDto.getTransferDate()))
				return ResponseEntity.badRequest().body(new MessageResponse("Date must not exceed toady's date"));
			promo.setEndDate(transferDto.getTransferDate());
			promo.setStatus(false);
			newPromotion.setBranch(branch.get());
			newPromotion.setDesignation(designation.get());
			newPromotion.setEndDate(null);
			newPromotion.setJoinDate(transferDto.getTransferDate());
			newPromotion.setRemarks(transferDto.getRemarks());
			newPromotion.setSubDepartment(section.get());
			newPromotion.setUser(user);
			newPromotion.setStatus(true);
			newPromotion.setApprover(userOption.get());
			emp.setBranch(branch.get());
			emp.setDesignation(designation.get());
			emp.setSection(section.get());
			emp.setUser(user);
			emp.setWorkingType(emp.getWorkingType());
			emp.setJobType(emp.getJobType());
			emp.setGrade(emp.getGrade());
			promotionRepo.save(newPromotion);
		}
		transfer.setUser(user);
		transfer.setBranch(branch.get());
		transfer.setDesignation(designation.get());
		transfer.setSection(section.get());
		transfer.setTransferDate(transferDto.getTransferDate());
		transfer.setRemark(transferDto.getRemarks());
		transferRepo.save(transfer);
		empRepo.save(emp);
		return ResponseEntity.ok().body(transfer);
	}


	public ResponseEntity<?> getAllOfficailEmployeeList(Pageable pageable) {
		Page<EmployeeOfficialInfo> empOffiList = empRepo.findAll(pageable);
		List<EmployeeOfficialResponseDto> responseDtoList = new ArrayList<>();
		if(empOffiList.isEmpty())
			return ResponseEntity.badRequest().body(new MessageResponse("No any List of employee"));
		for(EmployeeOfficialInfo emp: empOffiList) {
			EmployeeOfficialResponseDto responseDto = new EmployeeOfficialResponseDto();
			responseDto.setEmployee_official_id(emp.getId());
			responseDto.setUser_id(emp.getUser().getId());
			responseDto.setDesignation_id(emp.getDesignation().getId());
			responseDto.setBranch_id(emp.getBranch().getId());
			responseDto.setSection_id(emp.getSection().getId());
			responseDto.setJob_type_id(emp.getJobType().getId());
			responseDto.setWorking_type_id(emp.getWorkingType().getId());
			responseDtoList.add(responseDto);
		}
		return ResponseEntity.ok().body(responseDtoList);
	}


	public ResponseEntity<?> getEmployee(String id) {
		Optional<EmployeeOfficialInfo> optionalEmp = empRepo.findById(id);
		if(optionalEmp.isEmpty())
			return ResponseEntity.badRequest().body(new MessageResponse("No employee Details with employeeId " +id));
		EmployeeOfficialInfo emp = optionalEmp.get();
		EmployeeOfficialResponseDto responseDto = new EmployeeOfficialResponseDto();
		responseDto.setEmployee_official_id(emp.getId());
		responseDto.setUser_id(emp.getUser().getId());
		responseDto.setDesignation_id(emp.getDesignation().getId());
		responseDto.setBranch_id(emp.getBranch().getId());
		responseDto.setSection_id(emp.getSection().getId());
		responseDto.setJob_type_id(emp.getJobType().getId());
		responseDto.setWorking_type_id(emp.getWorkingType().getId());
		return ResponseEntity.ok().body(responseDto);
	}


	public ResponseEntity<?> updateEmployee(String id, OffiEmployeeUpdateDto updateDto) {
		Optional<EmployeeOfficialInfo> optionalEmp = empRepo.findById(id);
		if(optionalEmp.isEmpty())
			return ResponseEntity.badRequest().body(new MessageResponse("No employee Details with employeeId " +id));
		EmployeeOfficialInfo emp = optionalEmp.get();
		Optional<User> userOpt = userRepo.findById(emp.getUser().getId());
		if(!userOpt.isPresent())
			return ResponseEntity.badRequest().body(new MessageResponse("No employee found with user id " + emp.getUser().getId()));
		User user = userOpt.get();
		System.out.println("_________________________________________________" + updateDto.getWorking_type_id());
		if(updateDto.getWorking_type_id()==null) {emp.setWorkingType(emp.getWorkingType());} else {
			Optional<WorkingType> work = workingRepo.findById(updateDto.getWorking_type_id());
			if(!work.isPresent())
				return ResponseEntity.badRequest().body(new MessageResponse("No such type of working status"));
			if(updateDto.getWorking_type_id()== 1) {user.setStatus(Status.ACTIVE);} else {user.setStatus(Status.DEACTIVE);}
			emp.setWorkingType(work.get());
		}
		if(updateDto.getJob_type_id()==null) {emp.setJobType(emp.getJobType());} else {
			Optional<JobType> job = jobRepo.findById(updateDto.getJob_type_id());
			if(job.isEmpty())
				return ResponseEntity.badRequest().body(new MessageResponse("No such type of job status"));
			if(updateDto.getJob_type_id()==1) {user.setLeaveDate(null);} else {
				user.setLeaveDate(user.getLeaveDate());
			}
			emp.setJobType(job.get());
		}
		userRepo.save(user);
		empRepo.save(emp);
		return ResponseEntity.ok(emp);
	}

	
	
}
