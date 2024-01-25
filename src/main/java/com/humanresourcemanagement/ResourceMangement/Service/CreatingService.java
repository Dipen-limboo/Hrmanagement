package com.humanresourcemanagement.ResourceMangement.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.humanresourcemanagement.ResourceMangement.EncryptDecrypt.EncryptDecrypt;
import com.humanresourcemanagement.ResourceMangement.Entity.AdditionalInfo;
import com.humanresourcemanagement.ResourceMangement.Entity.Bank;
import com.humanresourcemanagement.ResourceMangement.Entity.Department;
import com.humanresourcemanagement.ResourceMangement.Entity.Designation;
import com.humanresourcemanagement.ResourceMangement.Entity.Document;
import com.humanresourcemanagement.ResourceMangement.Entity.FamilyInfo;
import com.humanresourcemanagement.ResourceMangement.Entity.User;
import com.humanresourcemanagement.ResourceMangement.Enum.Relation;
import com.humanresourcemanagement.ResourceMangement.Enum.Type;
import com.humanresourcemanagement.ResourceMangement.Payload.requestDto.AdditionalDto;
import com.humanresourcemanagement.ResourceMangement.Payload.requestDto.BankDto;
import com.humanresourcemanagement.ResourceMangement.Payload.requestDto.DepartmentDto;
import com.humanresourcemanagement.ResourceMangement.Payload.requestDto.DesignationDto;
import com.humanresourcemanagement.ResourceMangement.Payload.requestDto.FamilyInfoDto;
import com.humanresourcemanagement.ResourceMangement.Payload.responseDto.MessageResponse;
import com.humanresourcemanagement.ResourceMangement.Repository.AdditionalRepo;
import com.humanresourcemanagement.ResourceMangement.Repository.BankRepo;
import com.humanresourcemanagement.ResourceMangement.Repository.DepartmentRepo;
import com.humanresourcemanagement.ResourceMangement.Repository.DesignationRepo;
import com.humanresourcemanagement.ResourceMangement.Repository.DocumentRepo;
import com.humanresourcemanagement.ResourceMangement.Repository.FamilyRepo;
import com.humanresourcemanagement.ResourceMangement.Repository.UserRepository;
import com.humanresourcemanagement.ResourceMangement.security.service.UserDetailsImpl;

import jakarta.validation.Valid;

@Service
public class CreatingService {
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	DepartmentRepo departRepo;
	
	@Autowired
	DocumentRepo documentRepo;

	@Autowired
	DesignationRepo designationRepo;
	
	@Autowired
	BankRepo bankRepo;
	
	@Autowired
	FamilyRepo familyRepo;
	
	@Autowired
	AdditionalRepo additionalRepo; 
	
	@Autowired
	EncryptDecrypt encryptSer;
	
	public CreatingService(EncryptDecrypt encryptSer) {
		super();
		this.encryptSer = encryptSer;
	}

	public ResponseEntity<?> addDepartment(@Valid DepartmentDto departmentDto) {
		if(departRepo.existsByName(departmentDto.getName())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Department already exists."));
		}
		Department department = new Department();
		department.setName(departmentDto.getName());
		department.setBranch(departmentDto.getBranch());
		department.setAddress(departmentDto.getAddress());
		department.setPhone(departmentDto.getTel());
		departRepo.save(department);
		return ResponseEntity.ok().body(departmentDto);
	}

	public ResponseEntity<?> addDesgination(@Valid DesignationDto designationDto) {
		if(designationRepo.existsByName(designationDto.getName())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Designation already exists."));
		}
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

	public ResponseEntity<?> addBank(@Valid BankDto bankDto, Authentication auth) {
		if(bankRepo.existsByNameAndHolderName(bankDto.getName(), bankDto.getHolderName())){
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Already exists."));
		}
		Bank bank = new Bank();
		bank.setName(bankDto.getName());
		bank.setBranch(bankDto.getBranch());
		bank.setAccountNumber(bankDto.getAccount());
		bank.setHolderName(bankDto.getHolderName());
		
		UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
		Long userId = userDetails.getId();
		Optional<User> optionalUser = userRepo.findById(userId);
		if(optionalUser.isPresent()) {
//			bank.setUser(optionalUser.get());
		} else {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Usernot found by  id" + userId));
		}
		bankRepo.save(bank);
		return ResponseEntity.ok().body(bank);
	}

	public ResponseEntity<?> addDocument(String citizenship, String pan, String nationalityId, LocalDate issuedDate,
			String issuedPlace, MultipartFile file, Authentication auth) {
		try {
			if(documentRepo.existsByCitizenship(citizenship)) {
				return ResponseEntity.badRequest().body(new MessageResponse("Error: Citizenship already exists."));
			}
			if(documentRepo.existsByPan(pan)) {
				return ResponseEntity.badRequest().body(new MessageResponse("Error: Pan already exists."));
			}
			if(documentRepo.existsByNationality(nationalityId)) {
				return ResponseEntity.badRequest().body(new MessageResponse("Error: Nationality id already exists."));
			}
			Path uploadsDir = Paths.get("Document");
			if(!Files.exists(uploadsDir)) {
				Files.createDirectories(uploadsDir);
			}
			
			String fileName = StringUtils.cleanPath(file.getOriginalFilename());
			Path filePath = uploadsDir.resolve(fileName);
			Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
			
			UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
			Long userId = userDetails.getId();
			Optional<User> optionalUser= userRepo.findById(userId);
			
			Document document = new Document();
			if(citizenship!=null) {
				document.setCitizenship(encryptSer.encrypt(citizenship));
			} else {
				document.setCitizenship(null);
			}
			if(pan!=null) {
				document.setPan(encryptSer.encrypt(pan));
			} else {
				document.setPan(null);
			}
			if(nationalityId!=null) {
				document.setNationality(encryptSer.encrypt(nationalityId));
			} else {
				document.setNationality(null);
			}
			document.setIssuedDate(issuedDate);
			document.setIssuedPlace(issuedPlace);
			document.setFilePath(filePath.toString());
			
			if(optionalUser.isPresent()) {
//			document.setUser(optionalUser.get());	
			}
			documentRepo.save(document);
			return ResponseEntity.ok().body(document);
		} catch (Exception e) {
		    return ResponseEntity.status(500).body("Error saving user: " + e.getMessage());
		}
	}

	public ResponseEntity<?> addFamily(@Valid FamilyInfoDto familyInfoDto, Authentication auth) {
		FamilyInfo family = new FamilyInfo();
		family.setFirstName(familyInfoDto.getFirstname());
		family.setMiddleName(familyInfoDto.getMiddlename());
		family.setLastName(familyInfoDto.getLastname());
		Set<String> strRelation = familyInfoDto.getRelation();
		if(strRelation==null) {
			family.setRelation(null);
		} else {
			strRelation.forEach(relation -> {
				switch(relation) {
				case "father":
					family.setRelation(Relation.FATHER);
					break;
				case "mother":
					family.setRelation(Relation.MOTHER);
					break;
				case "brother":
					family.setRelation(Relation.BROTHER);
					break;
				case "sister":
					family.setRelation(Relation.SISTER);
					break;
				case "grandmother":
					family.setRelation(Relation.GRANDMOTHER);
					break;
				case "grandfather":
					family.setRelation(Relation.GRANDFATHER);
					break;	
				case "wife":
					family.setRelation(Relation.WIFE);
					break;	
				default:
					family.setRelation(null);
				}
			});
		}
		
		family.setPhone(familyInfoDto.getPhone());
		
		UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
		Long userId = userDetails.getId();
		Optional<User> optionalUser = userRepo.findById(userId);
		if(optionalUser.isPresent()) {
//			family.setUser(optionalUser.get());
		}
		familyRepo.save(family);
		return ResponseEntity.ok().body("Successfully added Family information of " + optionalUser.get().getUsername());
	}

	public ResponseEntity<?> addAdditional(@Valid AdditionalDto additionalDto, Authentication auth) {
		AdditionalInfo additional = new AdditionalInfo();
		additional.setName(additionalDto.getName());
		additional.setLevel(additionalDto.getLevel());
		additional.setJoinDate(additionalDto.getJoinDate());
		additional.setEndDate(additionalDto.getEndDate());
		UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
		Optional<User> optionalUser = userRepo.findById(userDetails.getId());
		if(optionalUser.isPresent()) {
//			additional.setUser(optionalUser.get());
		}
		Set<String> strType = additionalDto.getType();
		if(strType==null) {
			additional.setType(null);
		} else {
			strType.forEach(type -> {
				switch(type) {
				case "education":
					additional.setType(Type.EDUCATION);
					break;
				case "experience":
					additional.setType(Type.EXPERIENCE);
					break;
				case "training":
					additional.setType(Type.TRAINING);
					break;
				default:
					additional.setType(null);
				}
			});
		}
		additionalRepo.save(additional);
		return ResponseEntity.ok().body("Successfully added to the additional info of " + userDetails.getUsername());
	}

	
}
