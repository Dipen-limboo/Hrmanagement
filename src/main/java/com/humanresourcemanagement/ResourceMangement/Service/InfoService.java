package com.humanresourcemanagement.ResourceMangement.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.humanresourcemanagement.ResourceMangement.Entity.AdditionalInfo;
import com.humanresourcemanagement.ResourceMangement.Entity.Bank;
import com.humanresourcemanagement.ResourceMangement.Entity.Department;
import com.humanresourcemanagement.ResourceMangement.Entity.Designation;
import com.humanresourcemanagement.ResourceMangement.Entity.Document;
import com.humanresourcemanagement.ResourceMangement.Entity.FamilyInfo;
import com.humanresourcemanagement.ResourceMangement.Entity.User;
import com.humanresourcemanagement.ResourceMangement.Payload.requestDto.AdditionalDto;
import com.humanresourcemanagement.ResourceMangement.Payload.requestDto.BankDto;
import com.humanresourcemanagement.ResourceMangement.Payload.requestDto.DepartmentDto;
import com.humanresourcemanagement.ResourceMangement.Payload.requestDto.DesignationDto;
import com.humanresourcemanagement.ResourceMangement.Payload.responseDto.DocumentResponseDto;
import com.humanresourcemanagement.ResourceMangement.Payload.responseDto.FamilyDto;
import com.humanresourcemanagement.ResourceMangement.Payload.responseDto.MessageResponse;
import com.humanresourcemanagement.ResourceMangement.Repository.AdditionalRepo;
import com.humanresourcemanagement.ResourceMangement.Repository.BankRepo;
import com.humanresourcemanagement.ResourceMangement.Repository.DepartmentRepo;
import com.humanresourcemanagement.ResourceMangement.Repository.DesignationRepo;
import com.humanresourcemanagement.ResourceMangement.Repository.DocumentRepo;
import com.humanresourcemanagement.ResourceMangement.Repository.FamilyRepo;
import com.humanresourcemanagement.ResourceMangement.Repository.UserRepository;
import com.humanresourcemanagement.ResourceMangement.security.service.UserDetailsImpl;

@Service
public class InfoService {
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	AdditionalRepo additionalRepo;
	
	@Autowired
	BankRepo bankRepo;
	
	@Autowired
	DocumentRepo documentRepo;
	
	@Autowired 
	FamilyRepo familyRepo;
	
	@Autowired
	DepartmentRepo departRepo;

	@Autowired
	DesignationRepo designationRepo;

	public ResponseEntity<?> findDepartment() {
		List<Department> departmentList = departRepo.findAll();
		List<DepartmentDto> departmentDtoList = new ArrayList<>();
		
		if(!departmentList.isEmpty()) {
			for(Department department: departmentList) {
				DepartmentDto departmentDto = new DepartmentDto();
				departmentDto.setName(department.getName());
				departmentDto.setBranch(department.getBranch());
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

	public ResponseEntity<?> findAllaccountsOfStaff(Pageable pageable) {
		Page<Bank> bankList = bankRepo.findAll(pageable);
		List<BankDto> bankDtoList = new ArrayList<>();
		
		if(!bankList.isEmpty()) {
			for(Bank bank: bankList) {
				BankDto bankDto = new BankDto();
				bankDto.setName(bank.getName());
				bankDto.setBranch(bank.getBranch());
				bankDto.setHolderName(bank.getHolderName());
				bankDto.setAccount(bank.getAccountNumber());
				
				Optional<User> user = userRepo.findById(bank.getUser().getId());
				if(user.isPresent()) {
					bankDto.setUser_id(user.get().getId());
				}
				bankDtoList.add(bankDto);
			}
			return ResponseEntity.ok().body(bankDtoList);
		} else {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: There is no any account of any employees"));
		}
	}

	public ResponseEntity<?> findAlladditionalListofUser(Pageable pageable) {
		Page<AdditionalInfo> additionalList = additionalRepo.findAll(pageable);
		List<AdditionalDto> additionalDtoList = new ArrayList<>();
		
		if(!additionalList.isEmpty()) {
			for(AdditionalInfo additional : additionalList) {
				AdditionalDto additionalDto = new AdditionalDto();
				additionalDto.setName(additional.getName());
				
				String type = additional.getType().toString();
				String delimter = " ";
				String[] elements = type.split(delimter);
				Set<String> typeSet = new HashSet<>();
				for(String element: elements) {
					typeSet.add(element);
				}
				additionalDto.setType(typeSet);
				
				additionalDto.setJoinDate(additional.getJoinDate());
				additionalDto.setEndDate(additional.getEndDate());
				additionalDto.setLevel(additional.getLevel());

				Optional<User> user= userRepo.findById(additional.getUser().getId());
				if(user.isPresent()) {
					additionalDto.setUserId(user.get().getId());
				}
								
 				additionalDtoList.add(additionalDto);
			}
			return ResponseEntity.ok().body(additionalDtoList);
		} else {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: There is no any addtional information of employees"));
		}
	}

	public ResponseEntity<?> findAllfamilyInfoListofUser(Pageable pageable) {
		Page<FamilyInfo> familyList = familyRepo.findAll(pageable);
		List<FamilyDto> familyDtoList = new ArrayList<>();
		
		if(!familyList.isEmpty()) {
			for(FamilyInfo family: familyList) {
				FamilyDto familyDto = new FamilyDto();
				familyDto.setFirstname(family.getFirstName());
				familyDto.setMiddlename(family.getMiddleName());
				familyDto.setLastname(family.getLastName());
				familyDto.setPhone(family.getPhone());
				
				String relation = family.getRelation().toString();
				String[] elements = relation.split(" ");
				Set<String> relationSet= new HashSet<>();
				for(String element: elements) {
					relationSet.add(element);
				}
				familyDto.setRelation(relationSet);
				
				familyDto.setFile(family.getFile());
				
				Optional<User> user = userRepo.findById(family.getUser().getId());
				if(user.isPresent()) {
					familyDto.setUserId(user.get().getId());
				}
				familyDtoList.add(familyDto);
			}
			return ResponseEntity.ok().body(familyDtoList);
		} else {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: There is no any family details of any employees"));
		}
	}

	public ResponseEntity<?> findAllDocumentListOfUser(Pageable pageable) {
		Page<Document> documentList = documentRepo.findAll(pageable);
		List<DocumentResponseDto> documentResponseDtoList = new ArrayList<>();
		
		if(!documentList.isEmpty()) {
			for(Document document: documentList) {
				DocumentResponseDto documentResponseDto= new DocumentResponseDto();
				documentResponseDto.setCitizenship(document.getCitizenship());
				documentResponseDto.setPan(document.getPan());
				documentResponseDto.setNationalityId(document.getNationality());
				documentResponseDto.setIssuedDate(document.getIssuedDate());
				documentResponseDto.setIssuedPlace(document.getIssuedPlace());
				documentResponseDto.setPath(document.getFilePath());
				Optional<User> user = userRepo.findById(document.getUser().getId());
				if(user.isPresent()) {
					documentResponseDto.setUserId(user.get().getId());
				}
				documentResponseDtoList.add(documentResponseDto);
			}
			return ResponseEntity.ok().body(documentResponseDtoList);
		} else {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: There is no any document of any employees"));
		}
	}

	public ResponseEntity<?> delete(Long id, Authentication auth) {
		UserDetailsImpl userdetails = (UserDetailsImpl) auth.getPrincipal();
		Optional<User> optionalUser = userRepo.findById(userdetails.getId());
		if(optionalUser.isPresent()) {
			User user = optionalUser.get();
			bankRepo.deleteByIdAndUser(id, user);
		}
		return ResponseEntity.ok().body("Succesfully deleted the account id" + id);
	}
}
