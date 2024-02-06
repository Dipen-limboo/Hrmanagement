package com.humanresourcemanagement.ResourceMangement.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.humanresourcemanagement.ResourceMangement.Entity.Department;
import com.humanresourcemanagement.ResourceMangement.Entity.Designation;
import com.humanresourcemanagement.ResourceMangement.Entity.JobType;
import com.humanresourcemanagement.ResourceMangement.Entity.SubDepartment;
import com.humanresourcemanagement.ResourceMangement.Entity.WorkingType;
import com.humanresourcemanagement.ResourceMangement.Payload.requestDto.DepartmentDto;
import com.humanresourcemanagement.ResourceMangement.Payload.requestDto.DesignationDto;
import com.humanresourcemanagement.ResourceMangement.Payload.requestDto.JobTypeDto;
import com.humanresourcemanagement.ResourceMangement.Payload.requestDto.SubDepartmentDto;
import com.humanresourcemanagement.ResourceMangement.Payload.requestDto.WorkTypeDto;
import com.humanresourcemanagement.ResourceMangement.Payload.responseDto.JobTypeResponseDto;
import com.humanresourcemanagement.ResourceMangement.Payload.responseDto.MessageResponse;
import com.humanresourcemanagement.ResourceMangement.Payload.responseDto.WorkTypeResponseDto;
import com.humanresourcemanagement.ResourceMangement.Repository.DepartmentRepo;
import com.humanresourcemanagement.ResourceMangement.Repository.DesignationRepo;
import com.humanresourcemanagement.ResourceMangement.Repository.JobTypeRepo;
import com.humanresourcemanagement.ResourceMangement.Repository.SubDepartmentRepo;
import com.humanresourcemanagement.ResourceMangement.Repository.UserRepository;
import com.humanresourcemanagement.ResourceMangement.Repository.WokingTypeRepo;

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
}
