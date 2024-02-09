package com.humanresourcemanagement.ResourceMangement.Service;

import java.io.IOException;
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
import com.humanresourcemanagement.ResourceMangement.Entity.Bank;
import com.humanresourcemanagement.ResourceMangement.Entity.Branch;
import com.humanresourcemanagement.ResourceMangement.Entity.Department;
import com.humanresourcemanagement.ResourceMangement.Entity.Designation;
import com.humanresourcemanagement.ResourceMangement.Entity.Document;
import com.humanresourcemanagement.ResourceMangement.Entity.Education;
import com.humanresourcemanagement.ResourceMangement.Entity.EmpBank;
import com.humanresourcemanagement.ResourceMangement.Entity.FamilyInfo;
import com.humanresourcemanagement.ResourceMangement.Entity.Grade;
import com.humanresourcemanagement.ResourceMangement.Entity.JobType;
import com.humanresourcemanagement.ResourceMangement.Entity.Organization;
import com.humanresourcemanagement.ResourceMangement.Entity.SubDepartment;
import com.humanresourcemanagement.ResourceMangement.Entity.Training;
import com.humanresourcemanagement.ResourceMangement.Entity.User;
import com.humanresourcemanagement.ResourceMangement.Entity.WorkingType;
import com.humanresourcemanagement.ResourceMangement.Enum.Relation;
import com.humanresourcemanagement.ResourceMangement.Enum.Type;
import com.humanresourcemanagement.ResourceMangement.Payload.requestDto.BankDto;
import com.humanresourcemanagement.ResourceMangement.Payload.requestDto.BranchDto;
import com.humanresourcemanagement.ResourceMangement.Payload.requestDto.DepartmentDto;
import com.humanresourcemanagement.ResourceMangement.Payload.requestDto.DesignationDto;
import com.humanresourcemanagement.ResourceMangement.Payload.requestDto.EducationDto;
import com.humanresourcemanagement.ResourceMangement.Payload.requestDto.GradeDto;
import com.humanresourcemanagement.ResourceMangement.Payload.requestDto.JobTypeDto;
import com.humanresourcemanagement.ResourceMangement.Payload.requestDto.SubDepartmentDto;
import com.humanresourcemanagement.ResourceMangement.Payload.requestDto.TrainingDto;
import com.humanresourcemanagement.ResourceMangement.Payload.requestDto.WorkTypeDto;
import com.humanresourcemanagement.ResourceMangement.Payload.responseDto.MessageResponse;
import com.humanresourcemanagement.ResourceMangement.Repository.BankRepo;
import com.humanresourcemanagement.ResourceMangement.Repository.BranchRepo;
import com.humanresourcemanagement.ResourceMangement.Repository.DepartmentRepo;
import com.humanresourcemanagement.ResourceMangement.Repository.DesignationRepo;
import com.humanresourcemanagement.ResourceMangement.Repository.DocumentRepo;
import com.humanresourcemanagement.ResourceMangement.Repository.EducationRepo;
import com.humanresourcemanagement.ResourceMangement.Repository.EmpBankRepo;
import com.humanresourcemanagement.ResourceMangement.Repository.FamilyRepo;
import com.humanresourcemanagement.ResourceMangement.Repository.GradeRepo;
import com.humanresourcemanagement.ResourceMangement.Repository.JobTypeRepo;
import com.humanresourcemanagement.ResourceMangement.Repository.OrganizationRepo;
import com.humanresourcemanagement.ResourceMangement.Repository.SubDepartmentRepo;
import com.humanresourcemanagement.ResourceMangement.Repository.TrainingRepo;
import com.humanresourcemanagement.ResourceMangement.Repository.UserRepository;
import com.humanresourcemanagement.ResourceMangement.Repository.WokingTypeRepo;
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
	TrainingRepo trainRepo; 
	
	@Autowired
	EmpBankRepo empBankRepo;
	
	@Autowired
	SubDepartmentRepo subDepartmentRepo; 	
	
	@Autowired
	WokingTypeRepo workRepo;
	
	@Autowired
	JobTypeRepo jobRepo;
	
	@Autowired
	BranchRepo branchRepo;
	
	@Autowired
	OrganizationRepo orgRepo;
	
	@Autowired
	GradeRepo gradeRepo;
	
	@Autowired
	EducationRepo educationRepo;
	
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
		department.setAddress(departmentDto.getAddress());
		department.setPhone(departmentDto.getTel());
		departRepo.save(department);
		return ResponseEntity.ok().body(department);
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

	public ResponseEntity<?> addBank(@Valid BankDto bankDto) {
		if(bankRepo.existsByNameAndAddress(bankDto.getName(), bankDto.getAddress())){
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Already exists."));
		}
		Bank bank = new Bank();
		bank.setName(bankDto.getName());
		bank.setAccountNumber(bankDto.getAccount());
		bank.setAddress(bankDto.getAddress());
		bankRepo.save(bank);
		return ResponseEntity.ok().body(bank);
	}

	public ResponseEntity<?> addDocument(String citizenship, String pan, String nationalityId, LocalDate issuedDate,
			String issuedPlace, MultipartFile file, Authentication auth) {
		try {
			if(documentRepo.existsByCitizenship(citizenship)  && citizenship != null) {
				return ResponseEntity.badRequest().body(new MessageResponse("Error: Citizenship already exists."));
			}
			if(documentRepo.existsByPan(pan) && pan != null) {
				return ResponseEntity.badRequest().body(new MessageResponse("Error: Pan already exists."));
			}
			if(documentRepo.existsByNationality(nationalityId) && nationalityId !=null) {
				return ResponseEntity.badRequest().body(new MessageResponse("Error: Nationality id already exists."));
			}
			Path uploadsDir = Paths.get("src/main/resources/static/Document");
			if(!Files.exists(uploadsDir)) {
				Files.createDirectories(uploadsDir);
			}
			
			String fileName = StringUtils.cleanPath(file.getOriginalFilename());
			Path filePath = uploadsDir.resolve(fileName);
			if(documentRepo.existsByFilePath(filePath.toString())) {
				return ResponseEntity.badRequest().body(new MessageResponse("Error: Documents already exists with " + file));
			}
			Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
			
			UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
			Long userId = userDetails.getId();
			Optional<User> optionalUser= userRepo.findById(userId);
			
			Document document = new Document();
			if(citizenship!=null) {
				document.setCitizenship(EncryptDecrypt.encrypt(citizenship));
			} else {
				document.setCitizenship(null);
			}
			if(pan!=null) {
				document.setPan(EncryptDecrypt.encrypt(pan));
			} else {
				document.setPan(null);
			}
			if(nationalityId!=null) {
				document.setNationality(EncryptDecrypt.encrypt(nationalityId));
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


	public ResponseEntity<?> addFamily(@Valid String firstName, String middleName, String lastName,
			Set<String> relation, String phone, MultipartFile file, Authentication auth) throws IOException {
		FamilyInfo family = new FamilyInfo();
		family.setFirstName(firstName);
		family.setMiddleName(middleName);
		family.setLastName(lastName);
		Set<String> strRelation = relation;
		if(strRelation==null) {
			family.setRelation(null);
		} else {
			strRelation.forEach(relations -> {
				switch(relations) {
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
		
		family.setPhone(phone);
		
		Path uploadsDir = Paths.get("src/main/resources/static/Images");
		if(!Files.exists(uploadsDir)) {
			Files.createDirectories(uploadsDir);
		}
		
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		Path filePath = uploadsDir.resolve(fileName);
		
		if(familyRepo.existsByFile(filePath.toString())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Image already exists with " + file));
		}
		Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
		
		
		family.setFile(filePath.toString());
		UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
		Long userId = userDetails.getId();
		Optional<User> optionalUser = userRepo.findById(userId);
		if(optionalUser.isPresent()) {
			family.setUser(optionalUser.get());
		}
		familyRepo.save(family);
		return ResponseEntity.ok().body("Successfully added Family information  ");
	}

	
	public ResponseEntity<?> addSubDepartment(@Valid SubDepartmentDto subDepartmentDto) {
		if (subDepartmentRepo.existsByName(subDepartmentDto.getName())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: " + subDepartmentDto.getName() + " already exists."));
		}
		SubDepartment sub = new SubDepartment();
		sub.setName(subDepartmentDto.getName());
		Long departmentId = subDepartmentDto.getDepartment_id();
		Optional<Department> optionalDepartment = departRepo.findById(departmentId);
		if(optionalDepartment.isPresent()) {
			sub.setDepartment(optionalDepartment.get());
		} else {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Department Id " +departmentId+ " does not exists ."));
		}
		subDepartmentRepo.save(sub);
		return ResponseEntity.ok().body(sub);

	}

	public ResponseEntity<?> saveWork(@Valid WorkTypeDto workDto) {
		WorkingType work = new WorkingType();
		if(workRepo.existsByWorkingType(workDto.getWorkType()))
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Already exists the work type"));
		work.setWorkingType(workDto.getWorkType());
		workRepo.save(work);
		return ResponseEntity.ok().body(work);
	}

	public ResponseEntity<?> saveJob(@Valid JobTypeDto jobDto) {
		JobType job = new JobType();
		if(jobRepo.existsByJobType(jobDto.getJobType()))
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Already exists the job type"));
		job.setJobType(jobDto.getJobType());
		jobRepo.save(job);
		return ResponseEntity.ok().body(job);
	}

	public ResponseEntity<?> saveOrg(@Valid String org_name, String org_address, String org_email, String org_phone,
			String org_website, MultipartFile org_logo_path) throws IOException {
		Organization org = new Organization();
		org.setOrgName(org_name);
		org.setOrgAddress(org_address);
		org.setOrgEmail(org_email);
		org.setOrgPhone(org_phone);
		org.setOrgWebsite(org_website);
		Path uploadsDir = Paths.get("src/main/resources/static/Images");
		if(!Files.exists(uploadsDir)) {
			Files.createDirectories(uploadsDir);
		}
		
		String fileName = StringUtils.cleanPath(org_logo_path.getOriginalFilename());
		Path filePath = uploadsDir.resolve(fileName);
		
		if(familyRepo.existsByFile(filePath.toString())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Image already exists with " + org_logo_path));
		}
		Files.copy(org_logo_path.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
		org.setPath(filePath.toString());
		orgRepo.save(org);
		return ResponseEntity.ok().body(org);
	}

	public ResponseEntity<?> saveGrad(@Valid GradeDto gradeDto) {
		Grade grade = new Grade();
		grade.setGrade(gradeDto.getGrade_type());
		gradeRepo.save(grade);
		return ResponseEntity.ok().body(grade);
	}

	public ResponseEntity<?> addEmpBank(@Valid String emp_account_number, String emp_bank_branch, MultipartFile qrPath,
			 Long bank_id, Authentication auth) throws IOException {
		UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
		Optional<User> optionalUser = userRepo.findById(userDetails.getId());
		if(optionalUser.isPresent()) {
			User user = optionalUser.get();
			EmpBank empbank = new EmpBank();
			empbank.setAccountNumber(emp_account_number);
			empbank.setBranch(emp_bank_branch);
			
			Path uploadsDir = Paths.get("src/main/resources/static/QR");
			if(!Files.exists(uploadsDir)) 
				Files.createDirectories(uploadsDir);
			String fileName = StringUtils.cleanPath(qrPath.getOriginalFilename());
			Path filePath = uploadsDir.resolve(fileName);
			
			if(familyRepo.existsByFile(filePath.toString())) 
				return ResponseEntity.badRequest().body(new MessageResponse("Error: Image already exists with " + qrPath));
			Files.copy(qrPath.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
			empbank.setQrPath(filePath.toString());
			empbank.setUser(user);
			Optional<Bank> bank = bankRepo.findById(bank_id);
			if(bank.isEmpty())
				return ResponseEntity.badRequest().body(new MessageResponse("Bank not found with id " +bank_id));
			empbank.setBank(bank.get());
			empBankRepo.save(empbank);
			return ResponseEntity.ok().body(empbank);
		} else {
			return ResponseEntity.badRequest().body(new MessageResponse("User not found by id " + userDetails.getId()));
		}
	}

	public ResponseEntity<?> addBranch(@Valid BranchDto branchDto) {
		Branch branch = new Branch();
		branch.setBranchName(branchDto.getBranch_name());
		branch.setBranchAddress(branchDto.getBranch_address());
		branch.setBranchPhone(branchDto.getBranch_phone());
		branch.setOutOfValley(branchDto.isIs_out_of_valley());
		Optional<Organization> org = orgRepo.findById(branchDto.getOrganization_id());
		if(org.isEmpty())
			return ResponseEntity.badRequest().body(new MessageResponse("Organization not found by id " + branchDto.getOrganization_id()));
		branch.setOrganization(org.get());
		branchRepo.save(branch);
		return ResponseEntity.ok().body(branch);
	}

	public ResponseEntity<?> addTraining(@Valid TrainingDto trainingDto, Authentication auth) {
		UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
		Optional<User> optionalUser = userRepo.findById(userDetails.getId());
		if(optionalUser.isEmpty())
			return ResponseEntity.badRequest().body(new MessageResponse("User not found with id " +userDetails.getId()));
		User user = optionalUser.get();
		Training train = new Training();
		Set<String> str = trainingDto.getType();
		str.forEach(type -> {
			switch(type) {
			case "training":
				train.setType(Type.TRAINING);
				break;
			case "experience":
				train.setType(Type.EXPERIENCE);
				break;
			default:
				train.setType(null);
			}
		});
		train.setName(trainingDto.getName());
		train.setPosition(trainingDto.getPosition());
		train.setJoinDate(trainingDto.getJoinDate());
		train.setEndDate(trainingDto.getEndDate());
		train.setUser(user);
		trainRepo.save(train);
		return ResponseEntity.ok().body(train);
	}

	public ResponseEntity<?> addEducation(@Valid EducationDto educationDto, Authentication auth) {
		UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
		Optional<User> optionalUser = userRepo.findById(userDetails.getId());
		if(optionalUser.isEmpty())
			return ResponseEntity.badRequest().body(new MessageResponse("User not found with id " +userDetails.getId()));
		User user = optionalUser.get();
		Education education = new Education();
		education.setName(educationDto.getEducational_institue_name());
		education.setBoard(educationDto.getBoard());
		education.setLevel(educationDto.getLevel());
		education.setGpa(educationDto.getGpa());
		education.setStartDate(educationDto.getStart_date());
		education.setEndDate(educationDto.getEnd_date());
		education.setUser(user);
		educationRepo.save(education);
		return ResponseEntity.ok().body(education);
	}

}
