package com.humanresourcemanagement.ResourceMangement.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.humanresourcemanagement.ResourceMangement.Entity.Bank;
import com.humanresourcemanagement.ResourceMangement.Entity.Branch;
import com.humanresourcemanagement.ResourceMangement.Entity.Department;
import com.humanresourcemanagement.ResourceMangement.Entity.Designation;
import com.humanresourcemanagement.ResourceMangement.Entity.EmpBank;
import com.humanresourcemanagement.ResourceMangement.Entity.EmployeeOfficialInfo;
import com.humanresourcemanagement.ResourceMangement.Entity.Grade;
import com.humanresourcemanagement.ResourceMangement.Entity.JobType;
import com.humanresourcemanagement.ResourceMangement.Entity.LeaveInfo;
import com.humanresourcemanagement.ResourceMangement.Entity.Organization;
import com.humanresourcemanagement.ResourceMangement.Entity.Promotion;
import com.humanresourcemanagement.ResourceMangement.Entity.Roaster;
import com.humanresourcemanagement.ResourceMangement.Entity.SubDepartment;
import com.humanresourcemanagement.ResourceMangement.Entity.Transfer;
import com.humanresourcemanagement.ResourceMangement.Entity.User;
import com.humanresourcemanagement.ResourceMangement.Entity.Weekend;
import com.humanresourcemanagement.ResourceMangement.Entity.WorkingType;
import com.humanresourcemanagement.ResourceMangement.Payload.requestDto.BankDto;
import com.humanresourcemanagement.ResourceMangement.Payload.requestDto.BranchDto;
import com.humanresourcemanagement.ResourceMangement.Payload.requestDto.DepartmentDto;
import com.humanresourcemanagement.ResourceMangement.Payload.requestDto.DesignationDto;
import com.humanresourcemanagement.ResourceMangement.Payload.requestDto.GradeDto;
import com.humanresourcemanagement.ResourceMangement.Payload.requestDto.JobTypeDto;
import com.humanresourcemanagement.ResourceMangement.Payload.requestDto.LeaveUpdateDto;
import com.humanresourcemanagement.ResourceMangement.Payload.requestDto.RoasterUpdateDto;
import com.humanresourcemanagement.ResourceMangement.Payload.requestDto.SubDepartmentDto;
import com.humanresourcemanagement.ResourceMangement.Payload.requestDto.TransferUpdateDto;
import com.humanresourcemanagement.ResourceMangement.Payload.requestDto.WeekendUpdateDto;
import com.humanresourcemanagement.ResourceMangement.Payload.requestDto.WorkTypeDto;
import com.humanresourcemanagement.ResourceMangement.Payload.responseDto.BranchResponseDto;
import com.humanresourcemanagement.ResourceMangement.Payload.responseDto.EmpBankResponseDto;
import com.humanresourcemanagement.ResourceMangement.Payload.responseDto.GradeResponseDto;
import com.humanresourcemanagement.ResourceMangement.Payload.responseDto.JobTypeResponseDto;
import com.humanresourcemanagement.ResourceMangement.Payload.responseDto.LeaveResponseDto;
import com.humanresourcemanagement.ResourceMangement.Payload.responseDto.MessageResponse;
import com.humanresourcemanagement.ResourceMangement.Payload.responseDto.OrganizationResponseDto;
import com.humanresourcemanagement.ResourceMangement.Payload.responseDto.RoasterResponseDto;
import com.humanresourcemanagement.ResourceMangement.Payload.responseDto.TransferResponseDto;
import com.humanresourcemanagement.ResourceMangement.Payload.responseDto.WeekendResponseDto;
import com.humanresourcemanagement.ResourceMangement.Payload.responseDto.WorkTypeResponseDto;
import com.humanresourcemanagement.ResourceMangement.Repository.BankRepo;
import com.humanresourcemanagement.ResourceMangement.Repository.BranchRepo;
import com.humanresourcemanagement.ResourceMangement.Repository.DepartmentRepo;
import com.humanresourcemanagement.ResourceMangement.Repository.DesignationRepo;
import com.humanresourcemanagement.ResourceMangement.Repository.EmpBankRepo;
import com.humanresourcemanagement.ResourceMangement.Repository.EmployeeOfficialInfoRepo;
import com.humanresourcemanagement.ResourceMangement.Repository.GradeRepo;
import com.humanresourcemanagement.ResourceMangement.Repository.JobTypeRepo;
import com.humanresourcemanagement.ResourceMangement.Repository.LeaveRepo;
import com.humanresourcemanagement.ResourceMangement.Repository.OrganizationRepo;
import com.humanresourcemanagement.ResourceMangement.Repository.PromotionRepo;
import com.humanresourcemanagement.ResourceMangement.Repository.RoasterRepo;
import com.humanresourcemanagement.ResourceMangement.Repository.SubDepartmentRepo;
import com.humanresourcemanagement.ResourceMangement.Repository.TransferRepo;
import com.humanresourcemanagement.ResourceMangement.Repository.UserRepository;
import com.humanresourcemanagement.ResourceMangement.Repository.WeekendRepo;
import com.humanresourcemanagement.ResourceMangement.Repository.WokingTypeRepo;

import jakarta.validation.Valid;

@Service
public class AdminService {
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	DepartmentRepo departRepo;

	@Autowired
	DesignationRepo designationRepo;
	
	@Autowired
	SubDepartmentRepo subDepartmentRepo;
	
	@Autowired
	WokingTypeRepo workRepo;
	
	@Autowired
	JobTypeRepo jobRepo;
	
	@Autowired
	OrganizationRepo orgRepo;
	
	@Autowired
	GradeRepo gradeRepo;
	
	@Autowired
	BranchRepo branchRepo;
	
	@Autowired
	BankRepo bankRepo;
	
	@Autowired
	EmpBankRepo empBankRepo;
	
	@Autowired
	EmployeeOfficialInfoRepo empRepo;	
	
	@Autowired
	TransferRepo transferRepo;
	
	@Autowired
	RoasterRepo roasterRepo;
	
	@Autowired
	LeaveRepo leaveRepo;
	
	@Autowired
	WeekendRepo weekRepo;

	@Autowired
	PromotionRepo promotionRepo;
	
	public ResponseEntity<?> findDepartment() {
		List<Department> departmentList = departRepo.findAll();
		List<DepartmentDto> departmentDtoList = new ArrayList<>();
		
		if(!departmentList.isEmpty()) {
			for(Department department: departmentList) {
				DepartmentDto departmentDto = new DepartmentDto();
				departmentDto.setName(department.getName());
				departmentDto.setAddress(department.getAddress());
				departmentDto.setTel(department.getPhone());
				
				departmentDtoList.add(departmentDto);
			}
			return ResponseEntity.ok().body(departmentDtoList);
		} else {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: There is no any department"));
		}
	}

	public ResponseEntity<?> findAllDesgination(Pageable pageable) {
		Page<Designation> designationList = designationRepo.findAll(pageable);
		List<DesignationDto> designationDtoList = new ArrayList<>();
		
		if(!designationList.isEmpty()) {
			for(Designation designation: designationList) {
				DesignationDto designationDto = new DesignationDto();
				designationDto.setName(designation.getName());
				Department de = designation.getDepartment(); 
				Optional<Department> department =departRepo.findById(de.getId()); 
				if(department.isPresent()) {
					designationDto.setDepartment(department.get().getId());
				}
				designationDtoList.add(designationDto);
			}
			return ResponseEntity.ok().body(designationDtoList);
		} else {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: There is no any designation"));
		}
	}
	
	public ResponseEntity<?> findSubDepartment() {
		List<SubDepartment> subDepartmentlist=  subDepartmentRepo.findAll();
		List<SubDepartmentDto> subDepartmentDtolist= new ArrayList<>();
		
		for(SubDepartment sub: subDepartmentlist) {
			SubDepartmentDto subDto = new SubDepartmentDto();
			subDto.setName(sub.getName());
			subDto.setDepartment_id(sub.getDepartment().getId());
			subDepartmentDtolist.add(subDto);
		}
		return ResponseEntity.ok().body(subDepartmentDtolist);
	}
	
	public ResponseEntity<?> deleteDepartmentById(Long id) {
		Optional<Department> department = departRepo.findById(id);
		if(department.isPresent()) {
			departRepo.deleteById(id);
		} else {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Ther is no any department with Id  " +id));
		}
		return ResponseEntity.ok().body("You have succesfully deleted department id: " +id);
	}

	public ResponseEntity<?> deleteDesignationById(Long id) {
		Optional<Designation> designation= designationRepo.findById(id);
		if(designation.isPresent()) {
			designationRepo.deleteById(id);
		} else {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Ther is no any designation with Id  " +id));
		}
		return ResponseEntity.ok().body("You have succesfully deleted designation id: " +id);
	}
	
	public ResponseEntity<?> deleteSubDepartmentById(Long id) {
		Optional<SubDepartment> subDepartment = subDepartmentRepo.findById(id);
		if(subDepartment.isPresent()) {
			subDepartmentRepo.deleteById(id);
		} else {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Ther is no any sub department with Id  " +id));
		}
		return ResponseEntity.ok().body("You have succesfully deleted Sub_department id: " +id);
	}
	
	public ResponseEntity<?> getDepartmentById(Long id) {
		Optional<Department> optionalDepartment = departRepo.findById(id);
		if(optionalDepartment.isPresent()) {
			Department department = optionalDepartment.get();
			DepartmentDto departmentDto = new DepartmentDto();
			departmentDto.setName(department.getName());
			departmentDto.setAddress(department.getAddress());
			departmentDto.setTel(department.getPhone());
			return ResponseEntity.ok().body(departmentDto);
		} else {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Department not found by id " + id));
		}
		
	}

	public ResponseEntity<?> getDesignationById(Long id) {
		Optional<Designation> optionalDesignation= designationRepo.findById(id);
		if(optionalDesignation.isPresent()) {
			Designation designation= optionalDesignation.get();
			DesignationDto designationDto = new DesignationDto();
			designationDto.setName(designation.getName());
			designationDto.setDepartment(designation.getDepartment().getId());
			return ResponseEntity.ok().body(designationDto);
		} else {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Designation not found by id " + id));
		}
	}
	
	public ResponseEntity<?> getSubDepartmentById(Long id) {
		Optional<SubDepartment> optionalSubDepartment = subDepartmentRepo.findById(id);
		if(optionalSubDepartment.isPresent()) {
			SubDepartment subdepartment = optionalSubDepartment.get();
			SubDepartmentDto subdepartmentDto = new SubDepartmentDto();
			subdepartmentDto.setName(subdepartment.getName());
			subdepartmentDto.setDepartment_id(subdepartment.getDepartment().getId());
			return ResponseEntity.ok().body(subdepartmentDto);
		} else {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Department not found by id " + id));
		}
	}
	
	public ResponseEntity<?> updateDepartment(Long id, DepartmentDto departmentDto) {
		Optional<Department> optionalDepartment = departRepo.findById(id);
		if(optionalDepartment.isPresent()) {
			Department department = optionalDepartment.get();
			
			if(departRepo.existsByName(departmentDto.getName())) {
				return ResponseEntity.badRequest().body(new MessageResponse("Error: Department name already exists"));
			} 
			
			if(departRepo.existsByPhone(departmentDto.getTel())){
				return ResponseEntity.badRequest().body(new MessageResponse("Error: Phone already exists" ));
			}
			if(departmentDto.getName() == null) {
				department.setName(department.getName());
			} else {
				department.setName(departmentDto.getName());
			}
			
		
			if(departmentDto.getAddress() == null) {
				department.setAddress(department.getAddress());
			} else {
				department.setAddress(departmentDto.getAddress());
			}
			if(departmentDto.getTel() == null) {
				department.setPhone(department.getPhone());
			} else {
				department.setPhone(departmentDto.getTel());
			}
			departRepo.save(department);
			return ResponseEntity.ok().body(department);
		} else {
			return ResponseEntity.badRequest().body("Error: Department not found by id: " +id);
		}
	}

	public ResponseEntity<?> updateDesignation(Long id, DesignationDto designationDto) {
		Optional<Designation> optionalDesignation = designationRepo.findById(id);
		if(optionalDesignation.isPresent()) {
			Designation designation = optionalDesignation.get();
			
			if(designationRepo.existsByName(designationDto.getName())) {
				return ResponseEntity.badRequest().body(new MessageResponse("Error: " + designation.getName() + " already exists"));
			}
			if(designationDto.getName() == null) {
				designation.setName(designation.getName());
			} else {
				designation.setName(designationDto.getName());
			} 
			if(designationDto.getDepartment() == null) {
				designation.setDepartment(designation.getDepartment());
			} else {
				Optional<Department> department = departRepo.findById(designationDto.getDepartment());
				if(department.isPresent()) {
					designation.setDepartment(department.get());
				} else {
					return ResponseEntity.badRequest().body("Error: Department not found by id: " +id);
				}
			}
			designationRepo.save(designation);
			return ResponseEntity.ok().body(designation);
		}else {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Designation not found by id " + id));
		}
	}

	public ResponseEntity<?> updateSubDepartment(Long id, SubDepartmentDto subDepartmentDto) {
		Optional<SubDepartment> optionalSubDepartment = subDepartmentRepo.findById(id);
		if(optionalSubDepartment.isPresent()) {
			SubDepartment subdepartment = optionalSubDepartment.get();
			
			if(subDepartmentRepo.existsByName(subDepartmentDto.getName())) {
				return ResponseEntity.badRequest().body(new MessageResponse("Error: Sub Department name already exists " + subDepartmentDto.getName()));
			} 
						
			if(subDepartmentDto.getName() == null) {
				subdepartment.setName(subdepartment.getName());
			} else {
				subdepartment.setName(subDepartmentDto.getName());
			}
			
			if(subDepartmentDto.getDepartment_id() == null) {
				subdepartment.setDepartment(subdepartment.getDepartment());
			}else {
				Optional<Department> department = departRepo.findById(subDepartmentDto.getDepartment_id());
				if(department.isPresent()) {
					subdepartment.setDepartment(department.get());
				} else {
					return ResponseEntity.badRequest().body(new MessageResponse("Error: Department id " + subDepartmentDto.getDepartment_id()  +" doesnot exists!!"));
				}
			}
			
			subDepartmentRepo.save(subdepartment);
			return ResponseEntity.ok().body(subdepartment);
		} else {
			return ResponseEntity.badRequest().body("Error: Sub Department not found by id: " +id);
		}
	}

	public ResponseEntity<?> findWorkingType() {
		List <WorkingType> workingTypeLists = workRepo.findAll();
		List<WorkTypeResponseDto> workingTyperesponseDtoLists = new ArrayList<>();
		
		if(workingTypeLists.isEmpty())
			return ResponseEntity.badRequest().body(new MessageResponse("Error: No any list of working type"));
		for(WorkingType work : workingTypeLists) {
			WorkTypeResponseDto workResponseDto = new WorkTypeResponseDto();
			workResponseDto.setWork_type_id(work.getId());
			workResponseDto.setWork_type(work.getWorkingType());
			workingTyperesponseDtoLists.add(workResponseDto);
		}	
		return ResponseEntity.ok().body(workingTyperesponseDtoLists);
	}

	public ResponseEntity<?> deleteWorkTypeById(Long id) {
		Optional<WorkingType> optionalWork = workRepo.findById(id);
		if(optionalWork.isEmpty())
			return ResponseEntity.badRequest().body("Error: Work Type not found by id: " +id);
		workRepo.deleteById(id);
		return ResponseEntity.ok().body("Succesfuully deleted the working type with id " + id);
	}

	public ResponseEntity<?> getWorkTypeId(Long id) {
		Optional<WorkingType> optionalWork = workRepo.findById(id);
		if(optionalWork.isEmpty())
			return ResponseEntity.badRequest().body("Error: Work Type not found by id: " +id);
		WorkingType work = optionalWork.get();
		WorkTypeResponseDto workResponseDto = new WorkTypeResponseDto();
		workResponseDto.setWork_type_id(work.getId());
		workResponseDto.setWork_type(work.getWorkingType());
		return ResponseEntity.ok().body(workResponseDto);
	}

	public ResponseEntity<?> updateWorkTypeById(Long id, WorkTypeDto workTypeDto) {
		Optional<WorkingType> optionalWork = workRepo.findById(id);
		if(optionalWork.isEmpty())
			return ResponseEntity.badRequest().body("Error: Work Type not found by id: " +id);
		WorkingType work = optionalWork.get();
		work.setWorkingType(workTypeDto.getWorkType());
		workRepo.save(work);
		return ResponseEntity.ok().body(work);
	}

	public ResponseEntity<?> findJobType() {
		List <JobType> jobTypeLists= jobRepo.findAll();
		List<JobTypeResponseDto> jobTyperesponseDtoLists = new ArrayList<>();
		
		if(jobTypeLists.isEmpty())
			return ResponseEntity.badRequest().body(new MessageResponse("Error: No any list of Job type"));
		for(JobType job: jobTypeLists) {
			JobTypeResponseDto jobResponseDto = new JobTypeResponseDto();
			jobResponseDto.setJob_type_id(job.getId());
			jobResponseDto.setJob_type_name(job.getJobType());
			jobTyperesponseDtoLists.add(jobResponseDto);
		}	
		return ResponseEntity.ok().body(jobTyperesponseDtoLists);
	}
	
	public ResponseEntity<?> deleteJobTypeById(Long id) {
		Optional<JobType> optionalJob= jobRepo.findById(id);
		if(optionalJob.isEmpty())
			return ResponseEntity.badRequest().body("Error: Job Type not found by id: " +id);
		jobRepo.deleteById(id);
		return ResponseEntity.ok().body("Succesfuully deleted the job type with id " + id);
	}
	
	public ResponseEntity<?> getJobTypeId(Long id) {
		Optional<JobType> optionalJob= jobRepo.findById(id);
		if(optionalJob.isEmpty())
			return ResponseEntity.badRequest().body("Error: Job Type not found by id: " +id);
		JobType job= optionalJob.get();
		JobTypeResponseDto jobResponseDto = new JobTypeResponseDto();
		jobResponseDto.setJob_type_id(job.getId());
		jobResponseDto.setJob_type_name(job.getJobType());
		return ResponseEntity.ok().body(jobResponseDto);
	}

	public ResponseEntity<?> updateJobTypeById(Long id, JobTypeDto jobTypeDto) {
		Optional<JobType> optionalJob= jobRepo.findById(id);
		if(optionalJob.isEmpty())
			return ResponseEntity.badRequest().body("Error: Job Type not found by id: " +id);
		JobType job= optionalJob.get();
		job.setJobType(jobTypeDto.getJobType());
		jobRepo.save(job);
		return ResponseEntity.ok().body(job);
	}

	public ResponseEntity<?> findOrg() {
		List<Organization> orgList = orgRepo.findAll();
		List<OrganizationResponseDto> orgResponseDtoList = new ArrayList<>();
		
		if(orgList.isEmpty())
			return ResponseEntity.badRequest().body(new MessageResponse("There is not any organization in the database"));
		for(Organization org: orgList) {
			OrganizationResponseDto orgResponseDto = new OrganizationResponseDto();
			orgResponseDto.setOrg_id(org.getId());
			orgResponseDto.setOrg_name(org.getOrgName());
			orgResponseDto.setOrg_address(org.getOrgAddress());
			orgResponseDto.setOrg_email(org.getOrgEmail());
			orgResponseDto.setOrg_phone(org.getOrgPhone());
			orgResponseDto.setOrg_website(org.getOrgWebsite());
			orgResponseDto.setOrg_logo_path(org.getPath());
			orgResponseDtoList.add(orgResponseDto);
		}
		return ResponseEntity.ok().body(orgResponseDtoList);
	}

	public ResponseEntity<?> deleteOrgById(Long id) {
		Optional<Organization> optionalOrg = orgRepo.findById(id);
		if(optionalOrg.isEmpty())
			return ResponseEntity.badRequest().body(new MessageResponse("Cannot find with organization with id: " + id));
		orgRepo.deleteById(id);
		return ResponseEntity.ok().body("Succesfully deleted the organization with Id "+ id);
	}

	public ResponseEntity<?> getOrganizationById(Long id) {
		Optional<Organization> optionalOrg = orgRepo.findById(id);
		if(optionalOrg.isEmpty())
			return ResponseEntity.badRequest().body(new MessageResponse("Cannot find with organization with id: " + id));
		Organization org = optionalOrg.get();
		OrganizationResponseDto orgResponseDto = new OrganizationResponseDto();
		orgResponseDto.setOrg_id(org.getId());
		orgResponseDto.setOrg_name(org.getOrgName());
		orgResponseDto.setOrg_address(org.getOrgAddress());
		orgResponseDto.setOrg_email(org.getOrgEmail());
		orgResponseDto.setOrg_phone(org.getOrgPhone());
		orgResponseDto.setOrg_website(org.getOrgWebsite());
		orgResponseDto.setOrg_logo_path(org.getPath());
		return ResponseEntity.ok().body(orgResponseDto);
	}

	public ResponseEntity<?> UpdateOrgById(Long id, @Valid String org_name, String org_address, String org_email,
			String org_phone, String org_website, MultipartFile org_logo_path) throws IOException {
		Optional<Organization> optionalOrg = orgRepo.findById(id);
		if(optionalOrg.isEmpty())
			return ResponseEntity.badRequest().body(new MessageResponse("Cannot find with organization with id: " + id));
		Organization org = optionalOrg.get();
		if(org_name==null) {
			org.setOrgName(org.getOrgName());
		} else {
			org.setOrgName(org_name);
		}
		if(org_address == null) {
			org.setOrgAddress(org.getOrgAddress());
		} else {
			org.setOrgAddress(org_address);
		}
		if(org_email == null ) {
			org.setOrgEmail(org.getOrgEmail());
		} else {
			org.setOrgEmail(org_email);
		}
		if(org_phone == null) {
			org.setOrgPhone(org.getOrgPhone());
		} else {
			org.setOrgPhone(org_phone);
		}
		if(org_website == null) {
			org.setOrgWebsite(org.getOrgWebsite());
		} else {
			org.setOrgWebsite(org_website);
		}
		if(org_logo_path == null) {
			org.setPath(org.getPath());
		} else {
			Path uploadsDir = Paths.get("src/main/resources/static/Images");
			if(!Files.exists(uploadsDir)) 
				Files.createDirectories(uploadsDir);
			String fileName = StringUtils.cleanPath(org_logo_path.getOriginalFilename());
			Path filePath = uploadsDir.resolve(fileName);
			if(orgRepo.existsByPath(filePath.toString())) 
				return ResponseEntity.badRequest().body(new MessageResponse("Error: Images already exists with " + org_logo_path));
			String path = org.getPath();
			if(path != null && !path.isEmpty()) 
				Files.deleteIfExists(Paths.get(path));
			Files.copy(org_logo_path.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
			org.setPath(filePath.toString());
		}
		orgRepo.save(org);
		return ResponseEntity.ok().body(org);
	}

	public ResponseEntity<?> findGrade(Pageable pageable) {
		Page<Grade> gradeList = gradeRepo.findAll(pageable);
		List<GradeResponseDto> gradeResponseDtoList =  new ArrayList<>();
		if(gradeList.isEmpty())
			return ResponseEntity.badRequest().body(new MessageResponse("There is not any grade in the database"));
 		for(Grade grade: gradeList) {
 			GradeResponseDto gradeResponseDto = new GradeResponseDto();
 			gradeResponseDto.setGrade_id(grade.getId());
 			gradeResponseDto.setGrade_type(grade.getGrade());
 			gradeResponseDtoList.add(gradeResponseDto);
 		}
		return ResponseEntity.ok().body(gradeResponseDtoList);
	}

	public ResponseEntity<?> deleteGradeById(Long id) {
		Optional<Grade> grade = gradeRepo.findById(id);
		if(grade.isEmpty())
			return ResponseEntity.badRequest().body(new MessageResponse("Grade not found by id " + id));
		gradeRepo.deleteById(id);
		return ResponseEntity.ok().body("Succesfully deleted the grade with Id "+ id);
	}

	public ResponseEntity<?> getGradeById(Long id) {
		Optional<Grade> gradeOptional = gradeRepo.findById(id);
		if(gradeOptional.isEmpty())
			return ResponseEntity.badRequest().body(new MessageResponse("Grade not found by id " + id));
		Grade grade = gradeOptional.get();
		GradeResponseDto gradeResponseDto = new GradeResponseDto();
		gradeResponseDto.setGrade_id(grade.getId());
		gradeResponseDto.setGrade_type(grade.getGrade());
		return ResponseEntity.ok().body(gradeResponseDto);
	}

	public ResponseEntity<?> updateGradeById(Long id, GradeDto gradeDto) {
		Optional<Grade> gradeOptional = gradeRepo.findById(id);
		if(gradeOptional.isEmpty())
			return ResponseEntity.badRequest().body(new MessageResponse("Grade not found by id " + id));
		Grade grade = gradeOptional.get();
		if(gradeDto.getGrade_type() == null) {
			grade.setGrade(grade.getGrade());
		} else {
			grade.setGrade(gradeDto.getGrade_type());
		}
		gradeRepo.save(grade);
		return ResponseEntity.ok().body(grade);
	}
	
	
	
	public ResponseEntity<?> findAllBank(Pageable pageable) {
		Page<Bank> bankList = bankRepo.findAll(pageable);
		List<BankDto> bankDtoList = new ArrayList<>();
		if(!bankList.isEmpty()) {
			for(Bank bank: bankList) {
				BankDto bankDto = new BankDto();
				bankDto.setName(bank.getName());
				bankDto.setAddress(bank.getAddress());
				bankDto.setAccount(bank.getAccountNumber());
				bankDtoList.add(bankDto);
			}
			return ResponseEntity.ok().body(bankDtoList);
		} else {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: There is no any bank details"));
		}
	}
	
	public ResponseEntity<?> delete(Long id) {
		Optional<Bank> optionalBank = bankRepo.findById(id);
		if(optionalBank.isEmpty())
			return ResponseEntity.badRequest().body(new MessageResponse("Bank not found with id " +id));
		bankRepo.deleteById(id);
		return ResponseEntity.ok().body("Succesfully deleted the account id" + id);
	}

	public ResponseEntity<?> getAccount(Long id) {
		Optional<Bank> optionalBank = bankRepo.findById(id);
		if(optionalBank.isEmpty())
			return ResponseEntity.badRequest().body(new MessageResponse("Bank not found with id " +id));
		Bank bank = optionalBank.get();
		BankDto bankDto = new BankDto();
		bankDto.setAddress(bank.getAddress());
		bankDto.setAccount(bank.getAccountNumber());
		bankDto.setName(bank.getName());
		return ResponseEntity.ok().body(bankDto);
	}
	
	public ResponseEntity<?> updateAccount(Long id, BankDto bankDto) {
		
		Optional<Bank> optionalBank = bankRepo.findById(id);
		if(optionalBank.isEmpty())
			return ResponseEntity.badRequest().body(new MessageResponse("Bank not found with id " +id));
		Bank bank = optionalBank.get();
		
		if(bankDto.getAddress() == null) {
			bank.setAddress(bank.getAddress());
		} else {
			bank.setAddress(bankDto.getAddress());
		} 
		if(bankDto.getAccount() == null) {
			bank.setAccountNumber(bank.getAccountNumber());
		} else {
			bank.setAccountNumber(bankDto.getAccount());
		}
		if(bankDto.getName() == null) {
			bank.setName(bank.getName());
		} else {
			bank.setName(bankDto.getName());
		}

		bankRepo.save(bank);
		return ResponseEntity.ok().body(bank);
	
		
	}

	public ResponseEntity<?> findEmpBank(Pageable pageable) {
		Page<EmpBank> empbankList = empBankRepo.findAll(pageable);
		List<EmpBankResponseDto> empBankResponseDtoList = new ArrayList<>();
		if(empbankList.isEmpty())
			return ResponseEntity.badRequest().body(new MessageResponse("Error: There is no any account of any employees"));
		for(EmpBank empBank: empbankList) {
			EmpBankResponseDto responseDto = new EmpBankResponseDto();
			responseDto.setBank_id(empBank.getId());
			responseDto.setEmp_account_number(empBank.getAccountNumber());
			responseDto.setEmp_bank_branch(empBank.getBranch());
			responseDto.setEmp_bank_id(empBank.getBank().getId());
			responseDto.setUser_id(empBank.getUser().getId());
			responseDto.setQrPath(empBank.getQrPath());
			responseDto.setHoldername(empBank.getUser().getFirstName() +" " + empBank.getUser().getMiddleName() + " " + empBank.getUser().getLastName());
			empBankResponseDtoList.add(responseDto);
		}
		return ResponseEntity.ok().body(empBankResponseDtoList);
	}

	public ResponseEntity<?> findBranch() {
		List<Branch> branchList = branchRepo.findAll();
		List<BranchResponseDto> responseDtoList = new ArrayList<>();
		if(branchList.isEmpty())
			return ResponseEntity.badRequest().body(new MessageResponse("No any branch found"));
		for(Branch branch: branchList) {
			BranchResponseDto responseDto = new BranchResponseDto();
			responseDto.setBranch_id(branch.getId());
			responseDto.setBranch_name(branch.getBranchName());
			responseDto.setBranch_address(branch.getBranchAddress());
			responseDto.setBranch_phone(branch.getBranchPhone());
			responseDto.setIs_out_of_valley(branch.isOutOfValley());
			responseDto.setOrganization_id(branch.getOrganization().getId());
			responseDtoList.add(responseDto);
		}
		return ResponseEntity.ok().body(responseDtoList);
	}

	public ResponseEntity<?> deleteBranchById(Long id) {
		Optional<Branch> optionalBranch = branchRepo.findById(id);
		if(optionalBranch.isEmpty())
			return ResponseEntity.badRequest().body(new MessageResponse("Cannot found branch by id " +id));
		branchRepo.deleteById(id);
		return ResponseEntity.ok().body("Succesfully deleted branch id " +id);
	}

	public ResponseEntity<?> getBranchById(Long id) {
		Optional<Branch> optionalBranch = branchRepo.findById(id);
		if(optionalBranch.isEmpty())
			return ResponseEntity.badRequest().body(new MessageResponse("Cannot found branch by id " +id));
		Branch branch = optionalBranch.get(); 
		BranchResponseDto responseDto = new BranchResponseDto();
		responseDto.setBranch_id(branch.getId());
		responseDto.setBranch_name(branch.getBranchName());
		responseDto.setBranch_address(branch.getBranchAddress());
		responseDto.setBranch_phone(branch.getBranchPhone());
		responseDto.setIs_out_of_valley(branch.isOutOfValley());
		responseDto.setOrganization_id(branch.getOrganization().getId());
		return ResponseEntity.ok().body(responseDto);
	}

	public ResponseEntity<?> updateBranch(Long id, BranchDto branchDto) {
		Optional<Branch> optionalBranch = branchRepo.findById(id);
		if(optionalBranch.isEmpty())
			return ResponseEntity.badRequest().body(new MessageResponse("Cannot found branch by id " +id));
		Branch branch = optionalBranch.get(); 
		if(branchDto.getBranch_name()==null) {branch.setBranchName(branch.getBranchName());} else {branch.setBranchName(branchDto.getBranch_name());}
		if(branchDto.getBranch_address()==null) {branch.setBranchAddress(branch.getBranchAddress());} else {branch.setBranchAddress(branchDto.getBranch_address());}
		if(branchDto.getBranch_phone()==null) {branch.setBranchPhone(branch.getBranchPhone());} else {branch.setBranchPhone(branchDto.getBranch_phone());}
		if(branchDto.isIs_out_of_valley()==true) {branch.setOutOfValley(true);} else {branch.setOutOfValley(false);}
		if(branchDto.getOrganization_id()==null) {branch.setOrganization(branch.getOrganization());} else {
			Optional<Organization> org = orgRepo.findById(branchDto.getOrganization_id());
			if(org.isEmpty())
				return ResponseEntity.badRequest().body(new MessageResponse("Organization not found by id " + branchDto.getOrganization_id()));
			branch.setOrganization(org.get());
		}
		branchRepo.save(branch);
		return ResponseEntity.ok().body(branch);
	}

	public ResponseEntity<?> findTransfer(Pageable pageable) {
		Page<Transfer> transferList = transferRepo.findAll(pageable);
		List<TransferResponseDto> transferResponseDtoList = new ArrayList<>();
		if(transferList.isEmpty())
			return ResponseEntity.badRequest().body(new MessageResponse("No body is transfer"));
		for (Transfer transfer: transferList) {
			TransferResponseDto responseDto = new TransferResponseDto();
			responseDto.setTransfer_id(transfer.getId());
			responseDto.setBranch_id(transfer.getBranch().getId());
			responseDto.setDesignation_id(transfer.getDesignation().getId());
			responseDto.setSection_id(transfer.getSection().getId());
			responseDto.setTransfer_date(transfer.getTransferDate());
			responseDto.setUser_id(transfer.getUser().getId());
			responseDto.setRemarks(transfer.getRemark());
			transferResponseDtoList.add(responseDto);
		}
		return ResponseEntity.ok().body(transferResponseDtoList);
	}

	public ResponseEntity<?> deleteTransferById(Long id) {
		Optional<Transfer> optionalTransfer = transferRepo.findById(id);
		if(!optionalTransfer.isPresent())
			return ResponseEntity.badRequest().body(new MessageResponse("No any transfer information of transfer " +id));
		transferRepo.deleteById(id);
		return ResponseEntity.ok().body("Succesfully!! deleted tansfer with id ");
	}

	public ResponseEntity<?> getTransferById(Long id) {
		Optional<Transfer> optionalTransfer = transferRepo.findById(id);
		if(!optionalTransfer.isPresent())
			return ResponseEntity.badRequest().body(new MessageResponse("No any transfer information of transfer " +id));
		Transfer transfer = optionalTransfer.get();
		TransferResponseDto responseDto = new TransferResponseDto();
		responseDto.setTransfer_id(transfer.getId());
		responseDto.setBranch_id(transfer.getBranch().getId());
		responseDto.setDesignation_id(transfer.getDesignation().getId());
		responseDto.setSection_id(transfer.getSection().getId());
		responseDto.setTransfer_date(transfer.getTransferDate());
		responseDto.setUser_id(transfer.getUser().getId());
		responseDto.setRemarks(transfer.getRemark());
		return ResponseEntity.ok().body(responseDto);		
	}

	public ResponseEntity<?> updateTransfer(Long id, @Valid TransferUpdateDto transferDto) {
		Optional<Transfer> optionalTransfer = transferRepo.findById(id);
		if(!optionalTransfer.isPresent())
			return ResponseEntity.badRequest().body(new MessageResponse("No any transfer information of transfer " +id));
		Transfer transfer = optionalTransfer.get();
		User user = transfer.getUser();
		Optional<EmployeeOfficialInfo> empOpt = empRepo.findByUser(user);
		if(empOpt.isEmpty())
			return ResponseEntity.badRequest().body(new MessageResponse("User not found in the Employee official list of user with id " + user.getId()));
		EmployeeOfficialInfo emp = empOpt.get();
		Promotion promo = promotionRepo.findFirstByUserOrderByIdDesc(user);
		if(transferDto.getDesignation_id() != null && transferDto.getSection_id() != null) {
			Optional<Designation> designation = designationRepo.findById(transferDto.getDesignation_id());
			if(!designation.isPresent())
				return ResponseEntity.badRequest().body(new MessageResponse("Designation not found in the list"));
			Optional<SubDepartment> section = subDepartmentRepo.findById(transferDto.getSection_id());
			if(!section.isPresent())
				return ResponseEntity.badRequest().body(new MessageResponse("Section not found in the list"));
			if(!designation.get().getDepartment().getId().equals(section.get().getDepartment().getId()))
				return ResponseEntity.badRequest().body(new MessageResponse("Department are different in the section and designation.. "));
		}
		if((transferDto.getDesignation_id() != null && transferDto.getSection_id() == null) || (transferDto.getDesignation_id() == null && transferDto.getSection_id() != null)  ) {
			Optional<Designation> designation = designationRepo.findById(transferDto.getDesignation_id());
			if(!designation.isPresent())
				return ResponseEntity.badRequest().body(new MessageResponse("Designation not found in the list"));
			Optional<SubDepartment> section = subDepartmentRepo.findById(transferDto.getSection_id());
			if(!section.isPresent())
				return ResponseEntity.badRequest().body(new MessageResponse("Section not found in the list"));
			if((transfer.getSection().getDepartment().getId()!= designation.get().getDepartment().getId()) || (section.get().getDepartment().getId()!=(transfer.getDesignation().getDepartment().getId())) )
				return ResponseEntity.badRequest().body(new MessageResponse("Department are different in the section and designation.. "));
		}
		if(transferDto.getDesignation_id() == null) {transfer.setBranch(transfer.getBranch());} else {
			Optional<Designation> designation = designationRepo.findById(transferDto.getDesignation_id());
			if(!designation.isPresent())
				return ResponseEntity.badRequest().body(new MessageResponse("Designation not found in the list"));
			emp.setDesignation(designation.get());
			transfer.setDesignation(designation.get());
			promo.setDesignation(designation.get());
		}
		if(transferDto.getSection_id()==null) {transfer.setSection(transfer.getSection());} else {
			Optional<SubDepartment> section = subDepartmentRepo.findById(transferDto.getSection_id());
			if(!section.isPresent())
				return ResponseEntity.badRequest().body(new MessageResponse("Section not found in the list"));
			emp.setSection(section.get());
			transfer.setSection(section.get());
			promo.setSubDepartment(section.get());
		}
		if(transferDto.getBranch_id() == null) {transfer.setBranch(transfer.getBranch());} else {
			Optional<Branch> branch = branchRepo.findById(transferDto.getBranch_id());
			if(!branch.isPresent())
				return ResponseEntity.badRequest().body(new MessageResponse("Branch not found in the list"));
			emp.setBranch(branch.get());
			transfer.setBranch(branch.get());
			promo.setBranch(branch.get());
		}
		if(transferDto.getRemarks()==null) {transfer.setRemark(transfer.getRemark());} else {transfer.setRemark(transferDto.getRemarks()); promo.setRemarks(transferDto.getRemarks());}
		if(transferDto.getTransferDate()==null) {transfer.setTransferDate(transfer.getTransferDate()); } else {
			if((transferDto.getDesignation_id() == null || transferDto.getSection_id() ==null) || (transferDto.getDesignation_id() == null && transferDto.getSection_id() ==null) ) {transfer.setTransferDate(transferDto.getTransferDate());} else {
				Promotion opt = promotionRepo.findByEndDateAndUser(promo.getJoinDate(), user);
				System.out.println("------------------------------------------------------------------"+promo.getJoinDate());
				opt.setEndDate(transferDto.getTransferDate());
				promo.setJoinDate(transferDto.getTransferDate());
				transfer.setTransferDate(transferDto.getTransferDate());
				promotionRepo.save(opt);
			}
		}
		promotionRepo.save(promo);
		empRepo.save(emp);
		transferRepo.save(transfer);
		
		return ResponseEntity.ok().body(transfer);		
	}

	public ResponseEntity<?> findTimeSheet(Pageable pageable) {
		Page<Roaster> timeSheetList = roasterRepo.findAll(pageable);
		List<RoasterResponseDto> responseDtoList = new ArrayList<>();
		if(timeSheetList.isEmpty())
			return ResponseEntity.badRequest().body("Succesfull!! but the time sheet is empty");
		for(Roaster time: timeSheetList) {
			RoasterResponseDto responseDto = new RoasterResponseDto();
			responseDto.setTimeSheet_id(time.getId());
			responseDto.setOrganization_id(time.getOrganization().getId());
			responseDto.setStart_time(time.getStartTime());
			responseDto.setEnd_time(time.getEndTime());
			responseDtoList.add(responseDto);
		}
		return ResponseEntity.ok().body(responseDtoList);
	}

	public ResponseEntity<?> getTimeSheetById(Long id) {
		Optional<Roaster> optionalTimeSheet = roasterRepo.findById(id);
		if(optionalTimeSheet.isEmpty())
			return ResponseEntity.badRequest().body("Successfull but the time sheet with id " + id + " not found!!");
		Roaster time = optionalTimeSheet.get();
		RoasterResponseDto responseDto = new RoasterResponseDto();
		responseDto.setTimeSheet_id(time.getId());
		responseDto.setOrganization_id(time.getOrganization().getId());
		responseDto.setStart_time(time.getStartTime());
		responseDto.setEnd_time(time.getEndTime());
		return ResponseEntity.ok().body(responseDto);
	}

	public ResponseEntity<?> deleteTimeSheetById(Long id) {
		if(!roasterRepo.existsById(id))
			return ResponseEntity.badRequest().body("Successfull but the given id is not found in database" );
		roasterRepo.deleteById(id);
		return ResponseEntity.ok().body("Succesfully deleted the id " + id);
	}

	public ResponseEntity<?> updateTimeSheet(Long id, RoasterUpdateDto timeDto) {
		Optional<Roaster> optionalTimeSheet = roasterRepo.findById(id);
		if(optionalTimeSheet.isEmpty())
			return ResponseEntity.badRequest().body("Successfull but the time sheet with id " + id + " not found!!");
		Roaster time = optionalTimeSheet.get();
		if(timeDto.getStartTime()==null) {time.setStartTime(time.getStartTime());} else {time.setStartTime(timeDto.getStartTime());}
		if(timeDto.getEndTime()==null) {time.setEndTime(time.getEndTime());} else {time.setEndTime(timeDto.getEndTime());}
		if(timeDto.getOrganization_id()==null) {time.setOrganization(time.getOrganization());} else {
			Optional<Organization> org = orgRepo.findById(timeDto.getOrganization_id());
			if(org.isEmpty())
				return ResponseEntity.badRequest().body("Department not found!!!");
			timeDto.setOrganization_id(org.get().getId());
		}
		roasterRepo.save(time);
		return ResponseEntity.ok().body(time);
	}

	public ResponseEntity<?> findAllLeave() {
		List<LeaveInfo> leaveList = leaveRepo.findAll();
		List<LeaveResponseDto> responseDtoList = new ArrayList<>();
		if(leaveList.isEmpty())
			return ResponseEntity.badRequest().body("Succesfull but the Leave list is empty");
		for(LeaveInfo leave: leaveList) {
			LeaveResponseDto responseDto = new LeaveResponseDto();
			responseDto.setLeave_id(leave.getId());
			responseDto.setLeave_name(leave.getLeaveName());
			responseDto.setMax_days(leave.getMaxDays());
			responseDto.setIs_cashable(leave.isCasable());
			responseDto.setStatus(leave.isStatus());
			responseDto.setIs_leave_forwareded(leave.isAccumulatable());
			responseDtoList.add(responseDto);
		}
		return ResponseEntity.ok().body(responseDtoList);
	}

	public ResponseEntity<?> deleteLeaveInfoById(Long id) {
		if(!leaveRepo.existsById(id))
			return ResponseEntity.badRequest().body("Leave id " + id+ " not found!!");
		leaveRepo.deleteById(id);
		return ResponseEntity.ok().body("Successfully deleted the leave id " + id);
	}

	public ResponseEntity<?> getLeaveInfoById(Long id) {
		Optional<LeaveInfo> optionalLeave = leaveRepo.findById(id);
		if(optionalLeave.isEmpty())
			return ResponseEntity.badRequest().body("Leave id " + id+ " not found!!");
		LeaveInfo leave = optionalLeave.get();
		LeaveResponseDto responseDto = new LeaveResponseDto();
		responseDto.setLeave_id(leave.getId());
		responseDto.setLeave_name(leave.getLeaveName());
		responseDto.setMax_days(leave.getMaxDays());
		responseDto.setIs_cashable(leave.isCasable());
		responseDto.setStatus(leave.isStatus());
		responseDto.setIs_leave_forwareded(leave.isAccumulatable());
		return ResponseEntity.ok().body(responseDto);
	}

	public ResponseEntity<?> updateLeaveInfo(Long id, LeaveUpdateDto updateDto) {
		Optional<LeaveInfo> optionalLeave = leaveRepo.findById(id);
		if(optionalLeave.isEmpty())
			return ResponseEntity.badRequest().body("Leave id " + id+ " not found!!");
		LeaveInfo leave = optionalLeave.get();
		if(updateDto.getLeave_name()==null) {leave.setLeaveName(leave.getLeaveName());} else {leave.setLeaveName(updateDto.getLeave_name());}
		if(updateDto.getMax_days()==0) {leave.setMaxDays(leave.getMaxDays());} else {leave.setMaxDays(updateDto.getMax_days());}
		if(updateDto.isIs_cashable() == leave.isCasable()) {
			leave.setCasable(leave.isCasable());
		} else {
			leave.setCasable(updateDto.isIs_cashable());
			}
		if(updateDto.isStatus() == leave.isStatus()) 
		{
			leave.setStatus(leave.isStatus());
		}
		else {
			leave.setStatus(updateDto.isStatus());
			}
		if(updateDto.isIs_leave_forwareded() == leave.isAccumulatable()) {leave.setAccumulatable(leave.isAccumulatable());} else {leave.setAccumulatable(updateDto.isIs_leave_forwareded());}
		leaveRepo.save(leave);
		return ResponseEntity.ok().body(leave);
	}

	public ResponseEntity<?> findAllWeekend(Pageable pageable) {
		Page<Weekend> weekendList = weekRepo.findAll(pageable);
		List<WeekendResponseDto> responseDtoList = new ArrayList<>();
		if(weekendList.isEmpty())
			return ResponseEntity.badRequest().body("There is no any weekend list saved!!");
		for(Weekend week:  weekendList) {
			WeekendResponseDto responseDto = new WeekendResponseDto();
			responseDto.setWeekend_id(week.getId());
			responseDto.setWeekend_date(week.getDate());
			responseDto.setEmployee_id(week.getEmployee().getId());
			responseDtoList.add(responseDto);
		}
		return ResponseEntity.ok().body(responseDtoList);
	}

	public ResponseEntity<?> deleteWeekendById(Long id) {
		if(!weekRepo.existsById(id))
			return ResponseEntity.badRequest().body("Weekend not found with weekend id " + id);
		weekRepo.deleteById(id);
		return ResponseEntity.ok().body("Successfully!! Deleted the weekend id " +id);
	}

	public ResponseEntity<?> getWeekendById(Long id) {
		Optional<Weekend> optionalWeekend = weekRepo.findById(id);
		if(optionalWeekend.isEmpty())
			return ResponseEntity.badRequest().body("Weekend not found with weekend id " + id); 
		Weekend week = optionalWeekend.get();
		WeekendResponseDto responseDto = new WeekendResponseDto();
		responseDto.setWeekend_id(week.getId());
		responseDto.setWeekend_date(week.getDate());
		responseDto.setEmployee_id(week.getEmployee().getId());
		return ResponseEntity.ok().body(responseDto);
	}

	public ResponseEntity<?> updateWeekend(Long id, WeekendUpdateDto updateDto) {
		Optional<Weekend> optionalWeekend = weekRepo.findById(id);
		if(optionalWeekend.isEmpty())
			return ResponseEntity.badRequest().body("Weekend not found with weekend id " + id); 
		Weekend week = optionalWeekend.get();
		Optional<EmployeeOfficialInfo> employee = empRepo.findById(updateDto.getEmployee_id());
		if(employee.isEmpty())
			return ResponseEntity.badRequest().body("Employee not found with id " + updateDto.getEmployee_id());
		week.setEmployee(employee.get());
		week.setDate(updateDto.getWeekend_date());
		weekRepo.save(week);
		return ResponseEntity.ok().body(week);
	}


}
