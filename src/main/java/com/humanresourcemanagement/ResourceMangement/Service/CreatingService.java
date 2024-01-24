package com.humanresourcemanagement.ResourceMangement.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.humanresourcemanagement.ResourceMangement.EncryptDecrypt.EncryptDecrypt;
import com.humanresourcemanagement.ResourceMangement.Entity.Bank;
import com.humanresourcemanagement.ResourceMangement.Entity.Department;
import com.humanresourcemanagement.ResourceMangement.Entity.Designation;
import com.humanresourcemanagement.ResourceMangement.Entity.Document;
import com.humanresourcemanagement.ResourceMangement.Entity.User;
import com.humanresourcemanagement.ResourceMangement.Payload.requestDto.BankDto;
import com.humanresourcemanagement.ResourceMangement.Payload.requestDto.DepartmentDto;
import com.humanresourcemanagement.ResourceMangement.Payload.requestDto.DesignationDto;
import com.humanresourcemanagement.ResourceMangement.Payload.responseDto.MessageResponse;
import com.humanresourcemanagement.ResourceMangement.Repository.BankRepo;
import com.humanresourcemanagement.ResourceMangement.Repository.DepartmentRepo;
import com.humanresourcemanagement.ResourceMangement.Repository.DesignationRepo;
import com.humanresourcemanagement.ResourceMangement.Repository.DocumentRepo;
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
			bank.setUser(optionalUser.get());
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
			document.setUser(optionalUser.get());	
			}
			documentRepo.save(document);
			return ResponseEntity.ok().body(document);
		} catch (Exception e) {
		    return ResponseEntity.status(500).body("Error saving user: " + e.getMessage());
		}
	}

	
}
