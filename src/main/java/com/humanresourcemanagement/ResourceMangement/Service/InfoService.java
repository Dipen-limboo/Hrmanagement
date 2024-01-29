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
import com.humanresourcemanagement.ResourceMangement.Payload.requestDto.DocumentDto;
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
				additionalDto.setBoard(additional.getBoard());
				additionalDto.setGpa(additional.getGpa());
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
	public ResponseEntity<?> deleteDepartmentById(Long id) {
		departRepo.deleteById(id);
		return ResponseEntity.ok().body("You have succesfully deleted department id: " +id);
	}

	public ResponseEntity<?> deleteDesignationById(Long id) {
		designationRepo.deleteById(id);
		return ResponseEntity.ok().body("You have succesfully deleted designation id: " +id);
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

	public ResponseEntity<?> deleteAdditional(Long id, Authentication auth) {
		UserDetailsImpl userdetails = (UserDetailsImpl) auth.getPrincipal();
		Optional<User> optionalUser = userRepo.findById(userdetails.getId());
		if(optionalUser.isPresent()) {
			User user = optionalUser.get();
			additionalRepo.deleteByIdAndUser(id, user);
		}
		return ResponseEntity.ok().body("Succesfully deleted the additional info with id" + id);
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

	public ResponseEntity<?> getDepartmentById(Long id) {
		Optional<Department> optionalDepartment = departRepo.findById(id);
		if(optionalDepartment.isPresent()) {
			Department department = optionalDepartment.get();
			DepartmentDto departmentDto = new DepartmentDto();
			departmentDto.setName(department.getName());
			departmentDto.setBranch(department.getBranch());
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
	
	public ResponseEntity<?> getAdditionalInfo(Authentication auth) {
		UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
		Optional<User> optionalUser = userRepo.findById(userDetails.getId());
		if(optionalUser.isPresent()) {
			User user = optionalUser.get();
			if(additionalRepo.existsByUser(user)) {
				List<AdditionalInfo> infoList = additionalRepo.findByUser(user);
				List<AdditionalDto> additionalDtoList = new ArrayList<>();
				for (AdditionalInfo additional: infoList) {
					AdditionalDto additionalDto = new AdditionalDto();
					String type = additional.getType().toString();
					Set<String> str = new HashSet<>();
					str.add(type);
					additionalDto.setType(str);
					additionalDto.setName(additional.getName());
					additionalDto.setLevel(additional.getLevel());
					additionalDto.setJoinDate(additional.getJoinDate());
					additionalDto.setEndDate(additional.getEndDate());
					additionalDto.setUserId(additional.getUser().getId());
					additionalDto.setBoard(additional.getBoard());
					additionalDto.setGpa(additional.getGpa());
					additionalDtoList.add(additionalDto);
				}
				return ResponseEntity.ok().body(additionalDtoList);
			} else {
				return ResponseEntity.badRequest().body(new MessageResponse("Error: Additional Infos not found by user id" + user.getId()));
			}
			
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
	
	public ResponseEntity<?> getAccount(Authentication auth) {
		UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
		Optional<User> optionalUser = userRepo.findById(userDetails.getId());
		if(optionalUser.isPresent()) {
			User user = optionalUser.get();
			if(bankRepo.existsByUser(user)) {
				List<Bank> bankList = bankRepo.findByUser(user);
				List<BankDto> bankDtoList = new ArrayList<>();
				
				for(Bank bank: bankList) {
					BankDto bankDto = new BankDto();
					bankDto.setHolderName(bank.getHolderName());
					bankDto.setAccount(bank.getAccountNumber());
					bankDto.setName(bank.getName());
					bankDto.setBranch(bank.getBranch());
					bankDto.setUser_id(bank.getUser().getId());
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
			
			if(departmentDto.getBranch() == null) {
				department.setBranch(department.getBranch());
			}else {
				department.setBranch(departmentDto.getBranch());
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

	public ResponseEntity<?> updateAdditionalInfo(Long id, AdditionalDto additionalDto, Authentication auth) {
		UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
		Optional<User> user = userRepo.findById(userDetails.getId());
		if(user.isPresent()) {
			Optional<AdditionalInfo> optionalInfo = additionalRepo.findByUserAndId(user.get(), id);
			if(optionalInfo.isPresent()) {
				AdditionalInfo additional = optionalInfo.get();
				if(additionalDto.getType() == null) {
					additional.setType(additional.getType());
				} else {
					Set<String> str = additionalDto.getType();
					str.forEach(type -> {
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
							additional.setType(additional.getType());
						}
					});
					if(str.contains("education")) {
						if(additionalDto.getBoard() == null) {
							additional.setBoard(additional.getBoard());
						} else {
							additional.setBoard(additionalDto.getBoard());
						}
						
						if(additionalDto.getGpa()== 0.00){
							additional.setGpa(additional.getGpa());
						} else {
							additional.setGpa(additionalDto.getGpa());
						}
					}
				}
				
				LocalDate currentDate = LocalDate.now();
				if(additionalDto.getName()==null) {
					additional.setName(additional.getName());
				} else {
					additional.setName(additionalDto.getName());
				}
				
				if(additionalDto.getLevel() == null) {
					additional.setLevel(additional.getLevel());
				} else {
					additional.setLevel(additionalDto.getLevel());
				}
				
				if(additionalDto.getBoard() == null) {
					additional.setBoard(additional.getBoard());
				} else {
					additional.setBoard(additionalDto.getBoard());
				}
				
				if(additionalDto.getJoinDate() == null) {
					additional.setJoinDate(additional.getJoinDate());
				} else {
					if(currentDate.isAfter(additional.getJoinDate())) {
						additional.setJoinDate(additionalDto.getJoinDate());
					} else {
						return ResponseEntity.badRequest().body(new MessageResponse("Error: Join date must not exceed " + currentDate));
					}
				}
				
				if(additionalDto.getEndDate() == null) {
					additional.setEndDate(additional.getEndDate());
				} else {
					if(currentDate.isAfter(additional.getEndDate())) {
						additional.setEndDate(additionalDto.getEndDate());
					} else {
						return ResponseEntity.badRequest().body(new MessageResponse("Error: End date must not exceed " + currentDate));
					}
				}
				additional.setUser(additional.getUser());
				additionalRepo.save(additional);
				return ResponseEntity.ok().body(additional);
				} else {
					return ResponseEntity.badRequest().body(new MessageResponse("Error: User " + user.get().getId() + " has no authority to modify or update " + id));
				}
			} else {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: User not found by id" + userDetails.getId()));
		}
	}

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

	public ResponseEntity<?> updateAccount(Long id, BankDto bankDto, Authentication auth) {
		UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
		Optional<User> optionalUser = userRepo.findById(userDetails.getId());
		if(optionalUser.isPresent()) {
			User user = optionalUser.get();
			Optional<Bank> optionalBank = bankRepo.findByIdAndUser(id, user);
			if(optionalBank.isPresent()) {
				Bank bank = optionalBank.get();
				if(bankDto.getHolderName() == null) {
					bank.setHolderName(bank.getHolderName());
				} else {
					bank.setHolderName(bankDto.getHolderName());
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
				if(bankDto.getBranch() == null) {
					bank.setBranch(bank.getBranch());
				} else {
					bank.setBranch(bankDto.getBranch());
				}
				bank.setUser(user);
				bankRepo.save(bank);
				return ResponseEntity.ok().body(bank);
			} else {
				return ResponseEntity.badRequest().body(new MessageResponse("Error: User id " + user.getId() + " doesnot have an authority to modify this bank details " +id));
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
}
	