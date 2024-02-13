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
import com.humanresourcemanagement.ResourceMangement.Entity.Contract;
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
import com.humanresourcemanagement.ResourceMangement.Payload.requestDto.ContractUpdateDto;
import com.humanresourcemanagement.ResourceMangement.Payload.requestDto.EmployeeUpdateDto;
import com.humanresourcemanagement.ResourceMangement.Payload.requestDto.OffiEmployeeUpdateDto;
import com.humanresourcemanagement.ResourceMangement.Payload.requestDto.PromotionDto;
import com.humanresourcemanagement.ResourceMangement.Payload.requestDto.RenewContractDto;
import com.humanresourcemanagement.ResourceMangement.Payload.requestDto.TransferDto;
import com.humanresourcemanagement.ResourceMangement.Payload.responseDto.ContractResponseDto;
import com.humanresourcemanagement.ResourceMangement.Payload.responseDto.EmployeeOfficialResponseDto;
import com.humanresourcemanagement.ResourceMangement.Payload.responseDto.MessageResponse;
import com.humanresourcemanagement.ResourceMangement.Payload.responseDto.PromotionResponseDto;
import com.humanresourcemanagement.ResourceMangement.Repository.BranchRepo;
import com.humanresourcemanagement.ResourceMangement.Repository.ContractRepo;
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
	
	@Autowired
	ContractRepo contractRepo;
	
	
	//update user
	public ResponseEntity<?> saveEmployee(Long id, @Valid EmployeeUpdateDto employeeDto, Authentication auth) {
		Optional<User> optionalUser = userRepo.findById(id);
		if(!optionalUser.isPresent()) 
			return ResponseEntity.badRequest().body(new MessageResponse("Error: user not found by id " + id));
		
		User user = optionalUser.get();
		Promotion promotedUser = new Promotion();
		EmployeeOfficialInfo emp = new EmployeeOfficialInfo();
		Contract contract = new Contract();
		
		if(empRepo.existsById("emp0"+user.getId()))
			return ResponseEntity.badRequest().body(new MessageResponse("Employee already exists"));
		promotedUser.setUser(user);
		emp.setUser(user);
		emp.setId("emp0"+user.getId());
		
		LocalDate now = LocalDate.now();
		if(employeeDto.getJoinDate() == null) {
			user.setJoinDate(user.getJoinDate());
		} else {
			user.setJoinDate(employeeDto.getJoinDate());
			promotedUser.setJoinDate(employeeDto.getJoinDate());
		}
		
		promotedUser.setEndDate(null);
	
		UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
		Optional<User> optionalUserr = userRepo.findById(userDetails.getId());
		if(optionalUserr.isPresent()) 
			promotedUser.setApprover(optionalUserr.get());
		
		Long subdepartId = employeeDto.getSubDepartment();
		Optional<SubDepartment> optionalSubDepartment = subDepartmentRepo.findById(subdepartId);
		if(!optionalSubDepartment.isPresent()) 
			return ResponseEntity.badRequest().body(new MessageResponse("Error: SubDepartment not found by id " + subdepartId));
		promotedUser.setSubDepartment(optionalSubDepartment.get());
		emp.setSection(optionalSubDepartment.get());
	
		Optional<Designation> designation = designationRepo.findById(employeeDto.getDesignation());
		if(!designation.isPresent())
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Designation not found by id" + employeeDto.getDesignation()));
		promotedUser.setDesignation(designation.get());
		emp.setDesignation(designation.get());
		
			
		
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
		if(employeeDto.getJobType()==2  || employeeDto.getJobType()==3) {
			contract.setJoinDate(employeeDto.getJoinDate());
			contract.setEndDate(employeeDto.getEndDate());
			contract.setUser(user);
			contract.setApprover(optionalUserr.get());
			contract.setDesignation(designation.get());
			contract.setSection(optionalSubDepartment.get());
		}
		emp.setWorkingType(workingType.get());
		emp.setJobType(jobType.get());
		emp.setBranch(branch.get());
		emp.setGrade(grade.get());
		
		promotedUser.setBranch(branch.get());
		promotedUser.setRemarks(employeeDto.getRemarks());
		
		if(promotedUser.getSubDepartment().getDepartment().getId()!= promotedUser.getDesignation().getDepartment().getId()) 
			return ResponseEntity.badRequest().body(new MessageResponse("Error: department are different in subDepartment and desgination"));
		userRepo.save(user);
		promotionRepo.save(promotedUser);
		empRepo.save(emp);
		contractRepo.save(contract);
		return ResponseEntity.ok().body(promotedUser);
	}
	
	
	public ResponseEntity<?> promoteEmployee(Long id, @Valid PromotionDto promotionDto, Authentication auth) {
		Optional<User> optionalUser = userRepo.findById(id);
		if(!optionalUser.isPresent()) 
			return ResponseEntity.badRequest().body(new MessageResponse("Error: user not found by id " + id));
		User user = optionalUser.get();
		Optional<EmployeeOfficialInfo> employee = empRepo.findByUser(user); 
		if(!employee.isPresent())
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Doesnot have employee detials of user " + user.getId()));
		EmployeeOfficialInfo emp = employee.get();
		Promotion promotion = promotionRepo.findFirstByUserOrderByIdDesc(user);
		Promotion newPromotion = new Promotion();
		promotion.setEndDate(promotionDto.getJoinDate());
		promotion.setStatus(false);
		newPromotion.setJoinDate(promotionDto.getJoinDate());
		newPromotion.setUser(user);
		Optional<Designation> designation = designationRepo.findById(promotionDto.getDesignation());
		if(!designation.isPresent()) 
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Designation not found by id" + promotionDto.getDesignation()));
		if(promotion.getDesignation().getId() == designation.get().getId())
			return ResponseEntity.badRequest().body(new MessageResponse("Cannot promote in the current desgination"));
		newPromotion.setDesignation(designation.get());
		emp.setDesignation(designation.get());
		
		Optional<SubDepartment> subDepartment = subDepartmentRepo.findById(promotionDto.getSubDepartment());
		if(!subDepartment.isPresent())
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Sub Department not found by id" + promotionDto.getSubDepartment()));
		newPromotion.setSubDepartment(subDepartment.get());
		emp.setSection(subDepartment.get());
		
		UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
		Optional<User> optionalUserr = userRepo.findById(userDetails.getId());
		if(optionalUserr.isPresent()) 
			newPromotion.setApprover(optionalUserr.get());
	
		if(promotionDto.getBranch() == null) {newPromotion.setBranch(null);} else {
			Optional<Branch> branch = branchRepo.findById(promotionDto.getBranch());
			if(!branch.isPresent())
				return ResponseEntity.badRequest().body(new MessageResponse("Error: Not such type of branch"));
			newPromotion.setBranch(branch.get());
			emp.setBranch(branch.get());
		}
		
		if(newPromotion.getSubDepartment().getDepartment().getId() != newPromotion.getDesignation().getDepartment().getId())
			return ResponseEntity.badRequest().body(new MessageResponse("Error: department are different in subDepartment and desgination"));
		empRepo.save(emp);
		promotionRepo.save(promotion);
		promotionRepo.save(newPromotion);
		return ResponseEntity.ok().body(newPromotion);
		
	}


	public ResponseEntity<?> getAllPromotionList(Pageable pageable) {
		Page<Promotion> promotionList = promotionRepo.findAll(pageable);
		List<PromotionResponseDto> promotionResponseDto = new ArrayList<>();
		if(promotionList.isEmpty()) 
			return ResponseEntity.badRequest().body(new MessageResponse("Error: There is no any details!! "));
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
    	if(promotionDto.getJoinDate() != null) {
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


	public ResponseEntity<?> renew(Long id, @Valid RenewContractDto renewDto, Authentication auth) {
		Optional<User> optionalUser = userRepo.findById(id);
		if(!optionalUser.isPresent()) 
			return ResponseEntity.badRequest().body(new MessageResponse("Error: user not found by id " + id));
		User user = optionalUser.get();
		Contract contract = new Contract();
		Optional<Contract> optContract = contractRepo.findByUserAndContractStatus(user, true);
		if(optContract.isEmpty())
			return ResponseEntity.badRequest().body(new MessageResponse("No any contract of given user"));
		Contract pastContract = optContract.get();
				Optional<Designation> designation = designationRepo.findById(renewDto.getDesignation_id());
		if(designation.isEmpty())
			return ResponseEntity.badRequest().body(new MessageResponse("Designation not found!!"));
		Optional<SubDepartment> section = subDepartmentRepo.findById(renewDto.getSection_id());
		if(section.isEmpty())
			return ResponseEntity.badRequest().body(new MessageResponse("Section not found!!"));
		if(!designation.get().getDepartment().getId().equals(section.get().getDepartment().getId()))
			return ResponseEntity.badRequest().body(new MessageResponse("Designation and Section are not from same department"));
		if(pastContract.getEndDate().isAfter(renewDto.getJoin_date())) 
			pastContract.setEndDate(renewDto.getJoin_date());
		
		contract.setJoinDate(renewDto.getJoin_date());
		contract.setEndDate(renewDto.getEnd_date());		
		UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
		Optional<User> approver = userRepo.findById(userDetails.getId());
		if(approver.isPresent())
			contract.setApprover(approver.get());
		contract.setUser(user);
		if(!pastContract.getDesignation().equals(designation.get())  || !pastContract.getSection().equals(section.get())) {
			Optional<EmployeeOfficialInfo> empInfo = empRepo.findByUser(user);
			if(!empInfo.isPresent())
				return ResponseEntity.badRequest().body(new MessageResponse("No any employee information of user " + user.getId()));
			Optional<Promotion> optionaPromotion = promotionRepo.findByUserAndStatus(user, true);
			if(optionaPromotion.isEmpty())
				return ResponseEntity.badRequest().body(new MessageResponse("No any promotion details of user " +user.getId()));
			Promotion currentPromotion = optionaPromotion.get();
			currentPromotion.setEndDate(renewDto.getJoin_date());
			currentPromotion.setStatus(false);
			empInfo.get().setDesignation(designation.get());
			empInfo.get().setSection(section.get());
			Promotion promotion = new Promotion();
			promotion.setJoinDate(renewDto.getJoin_date());
			promotion.setEndDate(null);
			promotion.setDesignation(designation.get());
			promotion.setSubDepartment(section.get());
			promotion.setBranch(currentPromotion.getBranch());
			promotion.setApprover(approver.get());
			promotion.setUser(user);
			promotionRepo.save(promotion);
			promotionRepo.save(currentPromotion);
			empRepo.save(empInfo.get());
		}
		contract.setDesignation(designation.get());
		contract.setSection(section.get());
		pastContract.setContractStatus(false);
		contractRepo.save(pastContract);
		contractRepo.save(contract);
		return ResponseEntity.ok().body(contract);
	}


	public ResponseEntity<?> findContract(Long id) {
		Optional<Contract> optionalContract = contractRepo.findById(id);
		if(optionalContract.isEmpty())
			return ResponseEntity.badRequest().body(new MessageResponse("Invalid id "+id));
		Contract contract = optionalContract.get();
		ContractResponseDto responseDto = new ContractResponseDto();
		responseDto.setContract_id(contract.getId());
		responseDto.setJoin_date(contract.getJoinDate());
		responseDto.setEnd_date(contract.getEndDate());
		responseDto.setUser_id(contract.getUser().getId());
		responseDto.setDesignation_id(contract.getDesignation().getId());
		responseDto.setSection_id(contract.getSection().getId());
		responseDto.setApprover_id(contract.getApprover().getId());
		return ResponseEntity.ok().body(responseDto);
	}


	public ResponseEntity<?> findAllContract(Pageable pageable) {
		Page<Contract> contractList = contractRepo.findAll(pageable);
		List<ContractResponseDto> responseDtoList = new ArrayList<>();
		if(contractList.isEmpty())
			return ResponseEntity.badRequest().body(new MessageResponse("No any contract details in your database"));
		for(Contract contract: contractList) {
			ContractResponseDto responseDto = new ContractResponseDto();
			responseDto.setContract_id(contract.getId());
			responseDto.setJoin_date(contract.getJoinDate());
			responseDto.setEnd_date(contract.getEndDate());
			responseDto.setUser_id(contract.getUser().getId());
			responseDto.setDesignation_id(contract.getDesignation().getId());
			responseDto.setSection_id(contract.getSection().getId());
			responseDto.setApprover_id(contract.getApprover().getId());
			responseDtoList.add(responseDto);
		}
		return ResponseEntity.ok().body(responseDtoList);
	}


	public ResponseEntity<?> updateContract(Long id, @Valid ContractUpdateDto updateDto) {
		if(contractRepo.existsByIdAndContractStatus(id, false))
			return ResponseEntity.badRequest().body(new MessageResponse("you cannot invoke the past contract "));
		Optional<Contract> optionalContract = contractRepo.findById(id);
		System.out.println("_____________________________1");
		if(optionalContract.isEmpty())
			return ResponseEntity.badRequest().body(new MessageResponse("Contract id "+ id +" not found!!"));
		Contract contract = optionalContract.get();
		System.out.println("_____________________________2");
		Optional<Promotion> optionalPromotion = promotionRepo.findByUserAndJoinDate(contract.getUser(), contract.getJoinDate());
		if(!optionalPromotion.isEmpty()) {
			Promotion promotion = optionalPromotion.get();
			System.out.println("_____________________________3" + contract.getJoinDate());

			Optional<Promotion> optPromotion = promotionRepo.findByUserAndEndDate(promotion.getUser(), promotion.getJoinDate());
			System.out.println("_____________________________4" + promotion.getJoinDate());
			if(!optPromotion.isPresent()) {} 
				Promotion pastPromotion = optPromotion.get();

				Optional<EmployeeOfficialInfo> emp = empRepo.findByUser(contract.getUser());
				if(emp.isEmpty())
					return ResponseEntity.badRequest().body(new MessageResponse("Employee official information not found of this user!!"));
				if(updateDto.getJoin_date()==null) {
					pastPromotion.setEndDate(pastPromotion.getEndDate());
					promotion.setJoinDate(promotion.getJoinDate());
				} else {
					pastPromotion.setEndDate(updateDto.getJoin_date());
					promotion.setJoinDate(updateDto.getJoin_date());
				}
				
				if(updateDto.getEnd_date()==null) {promotion.setEndDate(promotion.getEndDate());} else {promotion.setEndDate(updateDto.getEnd_date());}
				if(updateDto.getDesignation_id()==null) {
					promotion.setDesignation(promotion.getDesignation());
					emp.get().setDesignation(emp.get().getDesignation());
				} else {
					Optional<Designation> desgination = designationRepo.findById(updateDto.getDesignation_id());
					if(desgination.isEmpty())
						return ResponseEntity.badRequest().body(new MessageResponse("Designation not found!!"));
					promotion.setDesignation(desgination.get());
					if(promotionRepo.existsByUserAndStatus(contract.getUser(), promotion.isStatus()==true)) {
						emp.get().setDesignation(desgination.get());	
					}
				}
				if(updateDto.getSection_id() == null) {
					promotion.setSubDepartment(promotion.getSubDepartment());
					emp.get().setSection(emp.get().getSection());
				} else {
					Optional<SubDepartment> section = subDepartmentRepo.findById(updateDto.getSection_id());
					if(section.isEmpty())
						return ResponseEntity.badRequest().body(new MessageResponse("Section not found!!"));
					promotion.setSubDepartment(section.get());
					if(promotionRepo.existsByUserAndStatus(contract.getUser(), promotion.isStatus()==true))
						emp.get().setSection(section.get());
				}
				if(!promotion.getDesignation().getDepartment().getId().equals(promotion.getSubDepartment().getDepartment().getId()))
					return ResponseEntity.badRequest().body(new MessageResponse("Designation and Section are from different department"));
				pastPromotion.setUser(contract.getUser());
				emp.get().setUser(contract.getUser());
				promotionRepo.save(promotion);
				empRepo.save(emp.get());
				promotionRepo.save(pastPromotion);
			
		}
		System.out.println("_____________________________5");

		if(updateDto.getJoin_date()==null) {contract.setJoinDate(contract.getJoinDate());} else {contract.setJoinDate(updateDto.getJoin_date());}
		if(updateDto.getEnd_date()==null) {contract.setEndDate(contract.getEndDate());} else {contract.setEndDate(updateDto.getEnd_date());}
		if(updateDto.getDesignation_id()==null) {contract.setDesignation(contract.getDesignation());} else {
			Optional<Designation> desgination = designationRepo.findById(updateDto.getDesignation_id());
			if(desgination.isEmpty())
				return ResponseEntity.badRequest().body(new MessageResponse("Designation not found!!"));
			contract.setDesignation(desgination.get());
		}
		if(updateDto.getSection_id()==null) {contract.setSection(contract.getSection());} else {
			Optional<SubDepartment> section = subDepartmentRepo.findById(updateDto.getSection_id());
			if(section.isEmpty())
				return ResponseEntity.badRequest().body(new MessageResponse("Section not found!!"));
			contract.setSection(section.get());
		}
		if(!contract.getDesignation().getDepartment().getId().equals(contract.getSection().getDepartment().getId()))
			return ResponseEntity.badRequest().body(new MessageResponse("Designation and Section are from different department"));
		contractRepo.save(contract);
		return ResponseEntity.ok().body(contract);
	}

	
	
}
