package com.humanresourcemanagement.ResourceMangement.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
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
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.humanresourcemanagement.ResourceMangement.EncryptDecrypt.EncryptDecrypt;
import com.humanresourcemanagement.ResourceMangement.Entity.Bank;
import com.humanresourcemanagement.ResourceMangement.Entity.Document;
import com.humanresourcemanagement.ResourceMangement.Entity.Education;
import com.humanresourcemanagement.ResourceMangement.Entity.EmpBank;
import com.humanresourcemanagement.ResourceMangement.Entity.FamilyInfo;
import com.humanresourcemanagement.ResourceMangement.Entity.Training;
import com.humanresourcemanagement.ResourceMangement.Entity.User;
import com.humanresourcemanagement.ResourceMangement.Enum.DocumentType;
import com.humanresourcemanagement.ResourceMangement.Enum.Relation;
import com.humanresourcemanagement.ResourceMangement.Enum.Type;
import com.humanresourcemanagement.ResourceMangement.Payload.requestDto.EducationUpdateDto;
import com.humanresourcemanagement.ResourceMangement.Payload.requestDto.TrainingUpdateDto;
import com.humanresourcemanagement.ResourceMangement.Payload.responseDto.DocumentResponseDto;
import com.humanresourcemanagement.ResourceMangement.Payload.responseDto.EducationResponseDto;
import com.humanresourcemanagement.ResourceMangement.Payload.responseDto.EmpBankResponseDto;
import com.humanresourcemanagement.ResourceMangement.Payload.responseDto.FamilyDto;
import com.humanresourcemanagement.ResourceMangement.Payload.responseDto.MessageResponse;
import com.humanresourcemanagement.ResourceMangement.Payload.responseDto.TrainingResponseDto;
import com.humanresourcemanagement.ResourceMangement.Repository.BankRepo;
import com.humanresourcemanagement.ResourceMangement.Repository.DocumentRepo;
import com.humanresourcemanagement.ResourceMangement.Repository.EducationRepo;
import com.humanresourcemanagement.ResourceMangement.Repository.EmpBankRepo;
import com.humanresourcemanagement.ResourceMangement.Repository.FamilyRepo;
import com.humanresourcemanagement.ResourceMangement.Repository.TrainingRepo;
import com.humanresourcemanagement.ResourceMangement.Repository.UserRepository;
import com.humanresourcemanagement.ResourceMangement.security.service.UserDetailsImpl;

import jakarta.validation.Valid;

@Service
public class InfoService {
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	BankRepo bankRepo;
	
	@Autowired
	DocumentRepo documentRepo;
	
	@Autowired 
	FamilyRepo familyRepo;
	
	@Autowired
	TrainingRepo trainRepo;
	
	@Autowired
	EducationRepo educationRepo;
	
	@Autowired
	EmpBankRepo empBankRepo;
	
	public ResponseEntity<?> findAllAccountOfUser(Authentication auth) {
		UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
		Optional<User> optionalUser = userRepo.findById(userDetails.getId());
		if(optionalUser.isPresent()) {
			User user = optionalUser.get();
			if(empBankRepo.existsByUser(user)) {
				List<EmpBank> bankList = empBankRepo.findByUser(user);
				List<EmpBankResponseDto> bankDtoList = new ArrayList<>();
				
				for(EmpBank empbank: bankList) {
					EmpBankResponseDto bankDto = new EmpBankResponseDto();
					bankDto.setBank_id(empbank.getId());
					bankDto.setEmp_account_number(empbank.getAccountNumber());
					bankDto.setEmp_bank_branch(empbank.getBranch());
					bankDto.setEmp_bank_id(empbank.getBank().getId());
					bankDto.setUser_id(user.getId());
					bankDto.setQrPath(empbank.getQrPath());
					bankDto.setHoldername(user.getFirstName() + user.getMiddleName() + user.getLastName());
					bankDtoList.add(bankDto);
				}
				return ResponseEntity.ok().body(bankDtoList);
			} else {
				return ResponseEntity.badRequest().body(new MessageResponse("Error:Bank account not found by user id" + user.getId()));
			}
		} else {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: User not found by id" + userDetails.getId()));
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
				documentResponseDto.setDocument_id(document.getId());
				documentResponseDto.setType(document.getType().toString());
				documentResponseDto.setPath(document.getFilePath());
				documentResponseDto.setId_number(document.getNumber());
				documentResponseDto.setIssued_date(document.getIssuedDate());
				documentResponseDto.setExprity_date(document.getExpiryDate());
				documentResponseDto.setUserId(document.getUser().getId());
				documentResponseDtoList.add(documentResponseDto);
			}
			return ResponseEntity.ok().body(documentResponseDtoList);
		} else {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: There is no any document of any employees"));
		}
	}
	
	
	
	//delete part

	public ResponseEntity<?> deleteEmpbankById(Long id, Authentication auth) throws IOException {
		UserDetailsImpl userdetails = (UserDetailsImpl) auth.getPrincipal();
		Optional<User> optionalUser = userRepo.findById(userdetails.getId());
		if(optionalUser.isPresent()) {
			User user = optionalUser.get();
			Optional<EmpBank> optionalBank = empBankRepo.findById(id);
			if(optionalBank.isEmpty())
				return ResponseEntity.badRequest().body(new MessageResponse("Error: Account not found by id" + id));
			Optional<EmpBank> optionalempBank= empBankRepo.findByIdAndUser(id, user);
			if(optionalempBank.isPresent()) {
				String filePath = optionalempBank.get().getQrPath();
				if(filePath != null && !filePath.isEmpty()) {
					Files.deleteIfExists(Paths.get(filePath));
				}
			empBankRepo.deleteByIdAndUser(id, user);
			} else {
				return ResponseEntity.badRequest().body(new MessageResponse("Error: You cannot delete this account."));
			}
		}
		return ResponseEntity.ok().body("Succesfully deleted the Document with id" + id);
	}
	
	public ResponseEntity<?> deleteDocument(Long id, Authentication auth) throws IOException {
		UserDetailsImpl userdetails = (UserDetailsImpl) auth.getPrincipal();
		Optional<User> optionalUser = userRepo.findById(userdetails.getId());
		if(optionalUser.isPresent()) {
			User user = optionalUser.get();
			Optional<Document> optionaldocument = documentRepo.findByIdAndUser(id, user);
			if(!optionaldocument.isPresent())
				return ResponseEntity.badRequest().body(new MessageResponse("Error: You cannot delete this document."));
			String filePath = optionaldocument.get().getFilePath();
			if(filePath != null && !filePath.isEmpty()) 
				Files.deleteIfExists(Paths.get(filePath));
			documentRepo.deleteByIdAndUser(id, user);
		}
		return ResponseEntity.ok().body("Succesfully deleted the Document with id" + id);
	}

	public ResponseEntity<?> deleteFamilyInfo(Long id, Authentication auth) throws IOException {
		UserDetailsImpl userdetails = (UserDetailsImpl) auth.getPrincipal();
		Optional<User> optionalUser = userRepo.findById(userdetails.getId());
		if(optionalUser.isPresent()) {
			User user = optionalUser.get();
			Optional<FamilyInfo> optionalFamily = familyRepo.findByIdAndUser(id, user);
			if(optionalFamily.isPresent()) {
				String filePath = optionalFamily.get().getFile();
				if(filePath != null && !filePath.isEmpty()) {
					Files.deleteIfExists(Paths.get(filePath));
					}
			
			familyRepo.deleteByIdAndUser(id, user);
			} else {
				return ResponseEntity.badRequest().body(new MessageResponse("Error: You cannot delete this information."));
			}
		}
		return ResponseEntity.ok().body("Succesfully deleted the Family info with id" + id);
	}

	
	
	//Get
	public ResponseEntity<?> getEmpBank(Long id, Authentication auth) {
		UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
		Optional<User> optionalUser = userRepo.findById(userDetails.getId());
		if(optionalUser.isPresent()) {
			User user = optionalUser.get();
			if(!empBankRepo.existsByUser(user))
				return ResponseEntity.badRequest().body(new MessageResponse("You don't have any account "));
			Optional<EmpBank> empBankOptional = empBankRepo.findByIdAndUser(id, user);
			if(empBankOptional.isEmpty())
				return ResponseEntity.badRequest().body(new MessageResponse("User does not get an access to get id " + id));
			EmpBank empBank = empBankOptional.get();
			EmpBankResponseDto responseDto = new EmpBankResponseDto();
			responseDto.setBank_id(empBank.getId());
			responseDto.setEmp_account_number(empBank.getAccountNumber());
			responseDto.setEmp_bank_branch(empBank.getBranch());
			responseDto.setEmp_bank_id(empBank.getBank().getId());
			responseDto.setHoldername(empBank.getUser().getFirstName() +" "+ empBank.getUser().getMiddleName()+" " + empBank.getUser().getLastName());
			responseDto.setQrPath(empBank.getQrPath());
			responseDto.setUser_id(empBank.getUser().getId());
			return ResponseEntity.ok().body(responseDto);
		} else {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: User not found by id" + userDetails.getId()));
		}
		
	}
	
	public ResponseEntity<?> getDocuments(Authentication auth) {
		UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
		Optional<User> optionalUser = userRepo.findById(userDetails.getId());
		if(optionalUser.isPresent()) {
			User user = optionalUser.get();
			if(documentRepo.existsByUser(user)) {
				List<Document> documentList = documentRepo.findByUser(user);
				List<DocumentResponseDto> documentDtoList = new ArrayList<>();
				
				for(Document document: documentList) {
					DocumentResponseDto documentResponseDto= new DocumentResponseDto();
					documentResponseDto.setDocument_id(document.getId());
					documentResponseDto.setType(document.getType().toString());
					documentResponseDto.setPath(document.getFilePath());
					documentResponseDto.setId_number(document.getNumber());
					documentResponseDto.setIssued_date(document.getIssuedDate());
					documentResponseDto.setExprity_date(document.getExpiryDate());
					documentResponseDto.setUserId(document.getUser().getId());
					documentDtoList.add(documentResponseDto);
				}
				return ResponseEntity.ok().body(documentDtoList);
			} else {
				return ResponseEntity.badRequest().body(new MessageResponse("Error: User id " + user.getId() + " doesnot have any documents"));
			}
			
		} else {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: User not found by id" + userDetails.getId()));
		}
	}
	
	
	public ResponseEntity<?> getFamily(Authentication auth) {
		UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
		Optional<User> optionalUser = userRepo.findById(userDetails.getId());
		if(optionalUser.isPresent()) {
			User user = optionalUser.get();
			if(familyRepo.existsByUser(user)) {
				List<FamilyInfo> familyList = familyRepo.findByUser(user);
				List<FamilyDto> familyDtoList = new ArrayList<>();
				for(FamilyInfo family: familyList) {
					FamilyDto familyDto = new FamilyDto();
					familyDto.setFirstname(family.getFirstName());
					familyDto.setMiddlename(family.getMiddleName());
					familyDto.setLastname(family.getLastName());
					familyDto.setPhone(family.getPhone());
					familyDto.setFile(family.getFile());
					familyDto.setUserId(user.getId());
					String relation = family.getRelation().toString();
					Set<String> str = new HashSet<>();
					str.add(relation);
					familyDto.setRelation(str);
					familyDtoList.add(familyDto);
				}
				return ResponseEntity.ok().body(familyDtoList);
			} else {
				return ResponseEntity.badRequest().body(new MessageResponse("Error: User Id " + user.getId() + " doesnot have any family information."));
			}
		} else {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: User not found by id" + userDetails.getId()));
		}
	}
	
	//update part
	public ResponseEntity<?> updateDocuments(Long id, Set<String> type, String id_number, LocalDate issued_date,
			LocalDate expiry_date, MultipartFile file, Authentication auth) {
		UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
		Optional<User> user = userRepo.findById(userDetails.getId());
		if(!user.isPresent())
			return ResponseEntity.badRequest().body(new MessageResponse("Error: User not found by id" + userDetails.getId()));
			if(!documentRepo.existsByUser(user.get()))
				return ResponseEntity.badRequest().body(new MessageResponse("Error: User not found by id" + userDetails.getId()));
			Optional<Document> optionalDocument = documentRepo.findByIdAndUser(id, user.get());
			if(!optionalDocument.isPresent())
				return ResponseEntity.badRequest().body(new MessageResponse("Error: User " + user.get().getId() + " has no authority to modify or update " +id)); 
			Document document = optionalDocument.get();
			try {
				if(type==null) {document.setType(document.getType());} else {
					Set<String> str = type;
					str.forEach(types -> {
						switch(types) {
						case "citizenship":
							document.setType(DocumentType.CITIZENSHIP);
							break;
						case "pan":
							document.setType(DocumentType.PAN);
							break;
						case "license":
							document.setType(DocumentType.LICENSE);
							break;
						case "passport":
							document.setType(DocumentType.PASSPORT);
							break;
						case "nationality":
							document.setType(DocumentType.NATIONALITY);
							break;
						default:
							document.setType(null);
						}
					});
				}
				
				if(id_number==null) {document.setNumber(document.getNumber());} else {document.setNumber(id_number);}
				if(issued_date ==null) {document.setIssuedDate(document.getIssuedDate());} else {document.setIssuedDate(issued_date);}
				if(expiry_date == null) {document.setExpiryDate(document.getExpiryDate());} else {document.setExpiryDate(expiry_date);}
				if(file == null) {document.setFilePath(document.getFilePath());} else {
					Path uploadsDir = Paths.get("src/main/resources/static/Document");
					if(!Files.exists(uploadsDir)) 
						Files.createDirectories(uploadsDir);
					String fileName = StringUtils.cleanPath(file.getOriginalFilename());
					Path filePath = uploadsDir.resolve(fileName);
					if(documentRepo.existsByFilePath(filePath.toString()))
						return ResponseEntity.badRequest().body(new MessageResponse("Error: Documents already exists with " + file));
					String path = document.getFilePath();
					if(path != null && !path.isEmpty()) 
						Files.deleteIfExists(Paths.get(path));
					Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
					document.setFilePath(filePath.toString());
				}
				document.setUser(user.get());
				documentRepo.save(document);
				return ResponseEntity.ok().body(document);
			} catch (Exception e) {
			    return ResponseEntity.status(500).body("Error saving user: " + e.getMessage());
			}
	}

	

	public ResponseEntity<?> updateFamily(Long id, String firstName, String middleName, String lastName,
			Set<String> relation, String phone, MultipartFile file, Authentication auth) throws IOException {
		UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
		Optional<User> optionalUser = userRepo.findById(userDetails.getId());
		if(!optionalUser.isPresent()) 
			return ResponseEntity.badRequest().body(new MessageResponse("Error: User not found by id" + userDetails.getId()));
		User user = optionalUser.get();
		Optional<FamilyInfo> optionalFamily = familyRepo.findByIdAndUser(id, user);
		if(!optionalFamily.isPresent()) 
			return ResponseEntity.badRequest().body(new MessageResponse("Error: User id " + user.getId() + " doesnot have an authority to modify this family details " +id));
		FamilyInfo family = optionalFamily.get();
		if(firstName == null){family.setFirstName(family.getFirstName());} else {family.setFirstName(firstName);}
		if(middleName == null) {family.setMiddleName(family.getMiddleName());} else {family.setMiddleName(middleName);}
		if(lastName == null) {family.setLastName(family.getLastName());} else {family.setLastName(lastName);}
		if(relation == null) {family.setRelation(family.getRelation());} else {
			Set<String> strRelation = relation;
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
		if(phone == null) {family.setPhone(family.getPhone());} else {family.setPhone(phone);}
		family.setUser(user);
		if(file==null) {family.setFile(family.getFile());} else {
			Path uploadsDir = Paths.get("src/main/resources/static/Images");
			if(!Files.exists(uploadsDir))
				Files.createDirectories(uploadsDir);
			String fileName = StringUtils.cleanPath(file.getOriginalFilename());
			Path filePath = uploadsDir.resolve(fileName);
			if(familyRepo.existsByFile(filePath.toString()))
				return ResponseEntity.badRequest().body(new MessageResponse("Error: Documents already exists with " + file));
			String path = family.getFile();
			if(path != null && !path.isEmpty())
				Files.deleteIfExists(Paths.get(path));
			Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
			family.setFile(filePath.toString());
		}
		familyRepo.save(family);
		return ResponseEntity.ok().body(family);
	}

	public ResponseEntity<?> updateEmpBank(@Valid String emp_account_number, String emp_bank_branch,
			MultipartFile qrPath, Long bank_id, Long id, Authentication auth) throws IOException {
		UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
		Optional<User> optionalUser = userRepo.findById(userDetails.getId());
		if(!optionalUser.isPresent()) 
			return ResponseEntity.badRequest().body(new MessageResponse("Error: User not found by id" + userDetails.getId()));
			User user = optionalUser.get();
			if(!empBankRepo.existsByUser(user))
				return ResponseEntity.badRequest().body(new MessageResponse("You don't have any account "));
			Optional<EmpBank> empBankOptional = empBankRepo.findByIdAndUser(id, user);
			if(empBankOptional.isEmpty())
				return ResponseEntity.badRequest().body(new MessageResponse("User does not get an access to get id " + id));
			EmpBank empBank = empBankOptional.get();
			if(emp_account_number == null) {empBank.setAccountNumber(empBank.getAccountNumber());} else {empBank.setAccountNumber(emp_account_number);}
			if(emp_bank_branch==null) {empBank.setBranch(empBank.getBranch());} else {empBank.setBranch(emp_bank_branch);}
			if(qrPath==null) {empBank.setQrPath(empBank.getQrPath());} else {
				Path uploadsDir = Paths.get("src/main/resources/static/QR");
				if(!Files.exists(uploadsDir))
					Files.createDirectories(uploadsDir);
				String fileName = StringUtils.cleanPath(qrPath.getOriginalFilename());
				Path filePath = uploadsDir.resolve(fileName);
				if(familyRepo.existsByFile(filePath.toString())) 
					return ResponseEntity.badRequest().body(new MessageResponse("Error: QR already exists with " + qrPath));
				String path = empBank.getQrPath();
				if(path != null && !path.isEmpty()) 
					Files.deleteIfExists(Paths.get(path));
				Files.copy(qrPath.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
				empBank.setQrPath(filePath.toString());
			}
			empBank.setUser(user);
			if(bank_id == null) {empBank.setBank(empBank.getBank());} else {
				Optional<Bank> bank = bankRepo.findById(bank_id);
				if(bank.isEmpty())
					return ResponseEntity.badRequest().body(new MessageResponse("Bank not found with id " +bank_id));
				empBank.setBank(bank.get());
			}
			empBankRepo.save(empBank);
			return ResponseEntity.ok().body(empBank);
	}

	public ResponseEntity<?> findAllTraining(Pageable pageable) {
		Page<Training> trainingList = trainRepo.findAll(pageable);
		List<TrainingResponseDto> responseDtoList = new ArrayList<>();
		if(trainingList.isEmpty())
			return ResponseEntity.badRequest().body(new MessageResponse("No any details found"));
		for(Training train: trainingList) {
			TrainingResponseDto responseDto = new TrainingResponseDto();
			responseDto.setTrain_exp_id(train.getId());
			responseDto.setType(train.getType().toString());
			responseDto.setOrganization(train.getName());
			responseDto.setPosition(train.getPosition());
			responseDto.setJoin_date(train.getJoinDate());
			responseDto.setEnd_date(train.getEndDate());
			responseDto.setUser_id(train.getUser().getId());
			responseDtoList.add(responseDto);
		}
		return ResponseEntity.ok().body(responseDtoList);
	}

	public ResponseEntity<?> gettrainingList(Authentication auth) {
		UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
		Optional<User> optionalUser = userRepo.findById(userDetails.getId());
		if(optionalUser.isEmpty())
			return ResponseEntity.badRequest().body(new MessageResponse("User not found with id " +userDetails.getId()));
		User user = optionalUser.get();
		List<Training> trainingList = trainRepo.findByUser(user);
		List<TrainingResponseDto> responseDtoList = new ArrayList<>();
		if(trainingList.isEmpty())
			return ResponseEntity.badRequest().body(new MessageResponse("No any details found of user with " + userDetails.getEmail()));
		for(Training train: trainingList) {
			TrainingResponseDto responseDto = new TrainingResponseDto();
			responseDto.setTrain_exp_id(train.getId());
			responseDto.setType(train.getType().toString());
			responseDto.setOrganization(train.getName());
			responseDto.setPosition(train.getPosition());
			responseDto.setJoin_date(train.getJoinDate());
			responseDto.setEnd_date(train.getEndDate());
			responseDto.setUser_id(train.getUser().getId());
			responseDtoList.add(responseDto);
		}
		return ResponseEntity.ok().body(responseDtoList);
		
	}

	public ResponseEntity<?> getTrainingById(Long id, Authentication auth) {
		UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
		Optional<User> optionalUser = userRepo.findById(userDetails.getId());
		if(optionalUser.isEmpty())
			return ResponseEntity.badRequest().body(new MessageResponse("User not found with id " +userDetails.getId()));
		User user = optionalUser.get();
		if(!trainRepo.existsById(id))
			return ResponseEntity.badRequest().body(new MessageResponse("Invalid Id " +id));
		Optional<Training> optionalTrain = trainRepo.findByIdAndUser(id, user);
		if(optionalTrain.isEmpty())
			return ResponseEntity.badRequest().body(new MessageResponse("User cannot get info with id " +id));
		Training train = optionalTrain.get();
		TrainingResponseDto responseDto = new TrainingResponseDto();
		responseDto.setTrain_exp_id(train.getId());
		responseDto.setType(train.getType().toString());
		responseDto.setOrganization(train.getName());
		responseDto.setPosition(train.getPosition());
		responseDto.setJoin_date(train.getJoinDate());
		responseDto.setEnd_date(train.getEndDate());
		responseDto.setUser_id(train.getUser().getId());
		return ResponseEntity.ok().body(responseDto);
	}

	public ResponseEntity<?> deleteTrainingById(Long id, Authentication auth) {
		UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
		Optional<User> optionalUser = userRepo.findById(userDetails.getId());
		if(optionalUser.isEmpty())
			return ResponseEntity.badRequest().body(new MessageResponse("User not found with id " +userDetails.getId()));
		User user = optionalUser.get();
		if(!trainRepo.existsById(id))
			return ResponseEntity.badRequest().body(new MessageResponse("Invalid Id " +id));
		Optional<Training> optionalTrain = trainRepo.findByIdAndUser(id, user);
		if(optionalTrain.isEmpty())
			return ResponseEntity.badRequest().body(new MessageResponse("User cannot get delete id " +id));
		trainRepo.deleteById(id);
		return ResponseEntity.ok().body("Deleted Succesfully id " + id +" !!" );
	}

	public ResponseEntity<?> updateTrainingById(TrainingUpdateDto updateDto, Long id, Authentication auth) {
		UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
		Optional<User> optionalUser = userRepo.findById(userDetails.getId());
		if(optionalUser.isEmpty())
			return ResponseEntity.badRequest().body(new MessageResponse("User not found with id " +userDetails.getId()));
		User user = optionalUser.get();
		if(!trainRepo.existsById(id))
			return ResponseEntity.badRequest().body(new MessageResponse("Invalid Id " +id));
		Optional<Training> optionalTrain = trainRepo.findByIdAndUser(id, user);
		if(optionalTrain.isEmpty())
			return ResponseEntity.badRequest().body(new MessageResponse("User cannot get delete id " +id));
		Training train = optionalTrain.get();
		if(updateDto.getType()==null) {train.setType(train.getType());} else {
			Set<String> str = updateDto.getType();
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
		}
		if(updateDto.getOrganization()==null) {train.setName(train.getName());} else {train.setName(updateDto.getOrganization());}
		if(updateDto.getPosition()==null) {train.setPosition(train.getPosition());} else {train.setPosition(updateDto.getPosition());}
		if(updateDto.getJoinDate()==null) {train.setJoinDate(train.getJoinDate());} else {
			if(!LocalDate.now().isAfter(updateDto.getJoinDate()))
				return ResponseEntity.badRequest().body(new MessageResponse("Date must not exceed today's date in AD"));
			train.setJoinDate(updateDto.getJoinDate());
		}
		if(updateDto.getEndDate()==null) {train.setEndDate(train.getEndDate());} else {
			if(!LocalDate.now().isAfter(updateDto.getEndDate()))
				return ResponseEntity.badRequest().body(new MessageResponse("Date must not exceed today's date in AD"));
			train.setEndDate(updateDto.getEndDate());
		}
		trainRepo.save(train);
		return ResponseEntity.ok().body(train);
	}

	public ResponseEntity<?> findAllEducation(Pageable pageable) {
		Page<Education> educationList = educationRepo.findAll(pageable);
		List<EducationResponseDto> responseDtoList = new ArrayList<>();
		if(educationList.isEmpty())
			return ResponseEntity.badRequest().body(new MessageResponse("No any details found"));
		for(Education education: educationList) {
			EducationResponseDto responseDto = new EducationResponseDto();
			responseDto.setEducational_institue_name(education.getName());
			responseDto.setEducation_id(education.getId());
			responseDto.setBoard(education.getBoard());
			responseDto.setLevel(education.getLevel());
			responseDto.setGpa(education.getGpa());
			responseDto.setStart_date(education.getStartDate());
			responseDto.setEnd_date(education.getEndDate());
			responseDto.setUser_id(education.getUser().getId());
			responseDtoList.add(responseDto);
		}
		return ResponseEntity.ok().body(responseDtoList);
	}

	public ResponseEntity<?> deleteEducationById(Long id, Authentication auth) {
		UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
		Optional<User> optionalUser = userRepo.findById(userDetails.getId());
		if(optionalUser.isEmpty())
			return ResponseEntity.badRequest().body(new MessageResponse("User not found with id " +userDetails.getId()));
		User user = optionalUser.get();
		if(!educationRepo.existsById(id))
			return ResponseEntity.badRequest().body(new MessageResponse("Invalid Id " +id));
		Optional<Education> optionalEducation= educationRepo.findByIdAndUser(id, user);
		if(optionalEducation.isEmpty())
			return ResponseEntity.badRequest().body(new MessageResponse("User cannot get delete id " +id));
		educationRepo.deleteById(id);
		return ResponseEntity.ok().body("Deleted Succesfully id " + id +" !!" );
	}

	public ResponseEntity<?> getEducaitonList(Authentication auth) {
		UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
		Optional<User> optionalUser = userRepo.findById(userDetails.getId());
		if(optionalUser.isEmpty())
			return ResponseEntity.badRequest().body(new MessageResponse("User not found with id " +userDetails.getId()));
		User user = optionalUser.get();
		List<Education> educationList = educationRepo.findByUser(user);
		List<EducationResponseDto> responseDtoList = new ArrayList<>();
		if(educationList.isEmpty())
			return ResponseEntity.badRequest().body(new MessageResponse("No any details found"));
		for(Education education: educationList) {
			EducationResponseDto responseDto = new EducationResponseDto();
			responseDto.setEducational_institue_name(education.getName());
			responseDto.setEducation_id(education.getId());
			responseDto.setBoard(education.getBoard());
			responseDto.setLevel(education.getLevel());
			responseDto.setGpa(education.getGpa());
			responseDto.setStart_date(education.getStartDate());
			responseDto.setEnd_date(education.getEndDate());
			responseDto.setUser_id(education.getUser().getId());
			responseDtoList.add(responseDto);
		}
		return ResponseEntity.ok().body(responseDtoList);
	}

	public ResponseEntity<?> getEducationById(Long id, Authentication auth) {
		UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
		Optional<User> optionalUser = userRepo.findById(userDetails.getId());
		if(optionalUser.isEmpty())
			return ResponseEntity.badRequest().body(new MessageResponse("User not found with id " +userDetails.getId()));
		User user = optionalUser.get();
		if(!educationRepo.existsById(id))
			return ResponseEntity.badRequest().body(new MessageResponse("Invalid Id " +id));
		Optional<Education> optionalEducation= educationRepo.findByIdAndUser(id, user);
		if(optionalEducation.isEmpty())
			return ResponseEntity.badRequest().body(new MessageResponse("User cannot get info of id " +id));
		Education education = optionalEducation.get();
		EducationResponseDto responseDto = new EducationResponseDto();
		responseDto.setEducational_institue_name(education.getName());
		responseDto.setEducation_id(education.getId());
		responseDto.setBoard(education.getBoard());
		responseDto.setLevel(education.getLevel());
		responseDto.setGpa(education.getGpa());
		responseDto.setStart_date(education.getStartDate());
		responseDto.setEnd_date(education.getEndDate());
		responseDto.setUser_id(education.getUser().getId());
		return ResponseEntity.ok().body(responseDto);
	}

	public ResponseEntity<?> updateEducationById(EducationUpdateDto updateDto, Long id, Authentication auth) {
		UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
		Optional<User> optionalUser = userRepo.findById(userDetails.getId());
		if(optionalUser.isEmpty())
			return ResponseEntity.badRequest().body(new MessageResponse("User not found with id " +userDetails.getId()));
		User user = optionalUser.get();
		if(!educationRepo.existsById(id))
			return ResponseEntity.badRequest().body(new MessageResponse("Invalid Id " +id));
		Optional<Education> optionalEducation= educationRepo.findByIdAndUser(id, user);
		if(optionalEducation.isEmpty())
			return ResponseEntity.badRequest().body(new MessageResponse("User cannot get info of id " +id));
		Education education = optionalEducation.get();
		if(updateDto.getEducational_institue_name()==null) {education.setName(education.getName());} else {education.setName(updateDto.getEducational_institue_name());}
		if(updateDto.getBoard()==null) {education.setBoard(education.getBoard());} else {education.setBoard(updateDto.getBoard());}
		if(updateDto.getLevel()==null) {education.setLevel(education.getLevel());} else {education.setLevel(updateDto.getLevel());}
		if(updateDto.getGpa()== 0.00) {education.setGpa(education.getGpa());} else {education.setGpa(updateDto.getGpa());}
		if(updateDto.getStart_date()==null) {education.setStartDate(education.getStartDate());} else {education.setStartDate(updateDto.getStart_date());}
		if(updateDto.getEnd_date()==null) {education.setEndDate(education.getEndDate());} else {education.setEndDate(updateDto.getEnd_date());}
		education.setUser(user);
		educationRepo.save(education);
		return ResponseEntity.ok().body(education);
	}

	

	

}
	