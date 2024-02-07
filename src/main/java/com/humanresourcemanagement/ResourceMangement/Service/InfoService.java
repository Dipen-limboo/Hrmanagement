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
import com.humanresourcemanagement.ResourceMangement.Entity.EmpBank;
import com.humanresourcemanagement.ResourceMangement.Entity.FamilyInfo;
import com.humanresourcemanagement.ResourceMangement.Entity.User;
import com.humanresourcemanagement.ResourceMangement.Enum.Relation;
import com.humanresourcemanagement.ResourceMangement.Payload.requestDto.BankDto;
import com.humanresourcemanagement.ResourceMangement.Payload.responseDto.DocumentResponseDto;
import com.humanresourcemanagement.ResourceMangement.Payload.responseDto.EmpBankResponseDto;
import com.humanresourcemanagement.ResourceMangement.Payload.responseDto.FamilyDto;
import com.humanresourcemanagement.ResourceMangement.Payload.responseDto.MessageResponse;
import com.humanresourcemanagement.ResourceMangement.Repository.BankRepo;
import com.humanresourcemanagement.ResourceMangement.Repository.DocumentRepo;
import com.humanresourcemanagement.ResourceMangement.Repository.EmpBankRepo;
import com.humanresourcemanagement.ResourceMangement.Repository.FamilyRepo;
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
				if(document.getCitizenship() == null) {
					documentResponseDto.setCitizenship(null);
				} else {
					documentResponseDto.setCitizenship(EncryptDecrypt.decrypt(document.getCitizenship()));
				}
				if(document.getPan() == null) {
					documentResponseDto.setPan(null);
				} else {
					documentResponseDto.setPan(EncryptDecrypt.decrypt(document.getPan()));
				}
				if(document.getNationality() == null) {
					documentResponseDto.setNationalityId(null);
				} else {
					documentResponseDto.setNationalityId(EncryptDecrypt.decrypt(document.getNationality()));
				}
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
			if(optionaldocument.isPresent()) {
				String filePath = optionaldocument.get().getFilePath();
				if(filePath != null && !filePath.isEmpty()) {
					Files.deleteIfExists(Paths.get(filePath));
				}
			documentRepo.deleteByIdAndUser(id, user);
			} else {
				return ResponseEntity.badRequest().body(new MessageResponse("Error: You cannot delete this document."));
			}
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
					if(document.getCitizenship() == null) {
						documentResponseDto.setCitizenship(null);
					} else {
						documentResponseDto.setCitizenship(EncryptDecrypt.decrypt(document.getCitizenship()));
					}
					if(document.getPan() == null) {
						documentResponseDto.setPan(null);
					} else {
						documentResponseDto.setPan(EncryptDecrypt.decrypt(document.getPan()));
					}
					if(document.getNationality() == null) {
						documentResponseDto.setNationalityId(null);
					} else {
						documentResponseDto.setNationalityId(EncryptDecrypt.decrypt(document.getNationality()));
					}
					documentResponseDto.setIssuedDate(document.getIssuedDate());
					documentResponseDto.setIssuedPlace(document.getIssuedPlace());
					documentResponseDto.setPath(document.getFilePath());
					documentResponseDto.setUserId(user.getId());
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
	public ResponseEntity<?> updateDocuments(Long id, String citizenship, String pan, String nationalityId,
			LocalDate issuedDate, String issuedPlace, MultipartFile file, Authentication auth) {
		UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
		Optional<User> user = userRepo.findById(userDetails.getId());
		if(user.isPresent()) {
			if(documentRepo.existsByUser(user.get())) {
				Optional<Document> optionalDocument = documentRepo.findByIdAndUser(id, user.get());
				if(optionalDocument.isPresent()) {
					Document document = optionalDocument.get();
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
						if(citizenship == null) {
							document.setCitizenship(document.getCitizenship());
						} else {
							document.setCitizenship(EncryptDecrypt.encrypt(citizenship));
						} 
						
						if(pan == null) {
							document.setPan(document.getPan());
						} else {
							document.setPan(EncryptDecrypt.encrypt(pan));
						}
						
						if (nationalityId == null) {
							document.setNationality(document.getNationality());
						} else {
							document.setNationality(EncryptDecrypt.encrypt(nationalityId));
						}
						LocalDate currentDate = LocalDate.now();
						if(issuedDate == null) {
							document.setIssuedDate(document.getIssuedDate());
						} else {
							if(currentDate.isAfter(issuedDate)) {
								document.setIssuedDate(issuedDate);
							} else {
								return ResponseEntity.badRequest().body(new MessageResponse("Error: issued Date should not exceed the current date " + currentDate));
							}
						}
						
						if(issuedPlace == null) {
							document.setIssuedPlace(document.getIssuedPlace());
						} else {
							document.setIssuedPlace(issuedPlace);
						}
						if(file == null) {
							document.setFilePath(document.getFilePath());
						} else {
							Path uploadsDir = Paths.get("src/main/resources/static/Document");
							if(!Files.exists(uploadsDir)) {
								Files.createDirectories(uploadsDir);
							}
							String fileName = StringUtils.cleanPath(file.getOriginalFilename());
							Path filePath = uploadsDir.resolve(fileName);
							if(documentRepo.existsByFilePath(filePath.toString())) {
								return ResponseEntity.badRequest().body(new MessageResponse("Error: Documents already exists with " + file));
							} else {
								String path = document.getFilePath();
								if(path != null && !path.isEmpty()) {
									Files.deleteIfExists(Paths.get(path));
								}
								Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
								document.setFilePath(filePath.toString());
							}
						}
						documentRepo.save(document);
						return ResponseEntity.ok().body(document);
					} catch (Exception e) {
					    return ResponseEntity.status(500).body("Error saving user: " + e.getMessage());
					}
				} else {
					return ResponseEntity.badRequest().body(new MessageResponse("Error: User " + user.get().getId() + " has no authority to modify or update " +id)); 
				}
			} else {
				return ResponseEntity.badRequest().body(new MessageResponse("Error: User not found by id" + userDetails.getId()));
			}
		} else {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: User not found by id" + userDetails.getId()));
		}
	}

	

	public ResponseEntity<?> updateFamily(Long id, String firstName, String middleName, String lastName,
			Set<String> relation, String phone, MultipartFile file, Authentication auth) throws IOException {
		UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
		Optional<User> optionalUser = userRepo.findById(userDetails.getId());
		if(optionalUser.isPresent()) {
			User user = optionalUser.get();
			Optional<FamilyInfo> optionalFamily = familyRepo.findByIdAndUser(id, user);
			if(optionalFamily.isPresent()) {
				FamilyInfo family = optionalFamily.get();
				if(firstName == null){
					family.setFirstName(family.getFirstName());
				} else {
					family.setFirstName(firstName);
				}
				
				if(middleName == null) {
					family.setMiddleName(family.getMiddleName());
				} else {
					family.setMiddleName(middleName);
				}
				
				if(lastName == null) {
					family.setLastName(family.getLastName());
				} else {
					family.setLastName(lastName);
				}
				
				if(relation == null) {
					family.setRelation(family.getRelation());
				} else {
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
				
				if(phone == null) {
					family.setPhone(family.getPhone());
				} else {
					family.setPhone(phone);
				}
				
				family.setUser(user);
				
				if(file==null) {
					family.setFile(family.getFile());
				} else {
					Path uploadsDir = Paths.get("src/main/resources/static/Images");
					if(!Files.exists(uploadsDir)) {
						Files.createDirectories(uploadsDir);
					}
					String fileName = StringUtils.cleanPath(file.getOriginalFilename());
					Path filePath = uploadsDir.resolve(fileName);
					if(familyRepo.existsByFile(filePath.toString())) {
						return ResponseEntity.badRequest().body(new MessageResponse("Error: Documents already exists with " + file));
					} else {
						String path = family.getFile();
						if(path != null && !path.isEmpty()) {
							Files.deleteIfExists(Paths.get(path));
						}
						Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
						family.setFile(filePath.toString());
					}
				}
				familyRepo.save(family);
				return ResponseEntity.ok().body(family);
			} else {
				return ResponseEntity.badRequest().body(new MessageResponse("Error: User id " + user.getId() + " doesnot have an authority to modify this family details " +id));
			}
		} else {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: User not found by id" + userDetails.getId()));
		}
	}

	public ResponseEntity<?> updateEmpBank(@Valid String emp_account_number, String emp_bank_branch,
			MultipartFile qrPath, Long bank_id, Long id, Authentication auth) throws IOException {
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
		} else {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: User not found by id" + userDetails.getId()));
		}
	}

	

	

}
	