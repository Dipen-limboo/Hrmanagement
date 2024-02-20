package com.humanresourcemanagement.ResourceMangement.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.humanresourcemanagement.ResourceMangement.Payload.requestDto.ContractUpdateDto;
import com.humanresourcemanagement.ResourceMangement.Payload.requestDto.EmployeeUpdateDto;
import com.humanresourcemanagement.ResourceMangement.Payload.requestDto.OffiEmployeeUpdateDto;
import com.humanresourcemanagement.ResourceMangement.Payload.requestDto.PromotionDto;
import com.humanresourcemanagement.ResourceMangement.Payload.requestDto.RenewContractDto;
import com.humanresourcemanagement.ResourceMangement.Payload.requestDto.TransferDto;
import com.humanresourcemanagement.ResourceMangement.Service.EmployeeService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/employee")
@SecurityRequirement(name="Bearer Authentication")
public class EmployeeController {
	
	@Autowired
	EmployeeService empService;

	@PostMapping("/createEmployee/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('SUPERADMIN')")
	@Transactional
	public ResponseEntity<?> updateUser(@PathVariable Long id, @Valid @RequestBody EmployeeUpdateDto employeeDto, Authentication auth){
		return empService.saveEmployee(id, employeeDto, auth);
	}
	
	@PostMapping("/promoteEmployee/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('SUPERADMIN')")
	public ResponseEntity<?> promoteUser(@PathVariable Long id, @Valid @RequestBody PromotionDto promotionDto, Authentication auth){
		return empService.promoteEmployee(id, promotionDto, auth);
	}
	
	@PostMapping("/renewContract/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('SUPERADMIN')")
	@Transactional
	public ResponseEntity<?> renewContract(@PathVariable Long id, @Valid @RequestBody RenewContractDto renewDto, Authentication auth){
		return empService.renew(id, renewDto, auth);
	}
	
	@PostMapping("/transferEmployee/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('SUPERADMIN')")
	@Transactional
	public ResponseEntity<?> transferUser(@PathVariable Long id, @Valid @RequestBody TransferDto transferDto, Authentication auth){
		return empService.transferEmp(id, transferDto, auth);
	}
	
	
	///get list	
	
	
	@GetMapping("/getOfficialEmployeeList")
	@PreAuthorize("hasRole('ADMIN') or hasRole('SUPERADMIN')")
	public ResponseEntity<?> getListOfOfficailEmployee(@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "10") int size,
			@RequestParam(name = "sort", required = false, defaultValue = "id") String id,
			@RequestParam(name = "order", required = false, defaultValue = "desc") String sortDir
		) {
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(id).ascending() : Sort.by(id).descending();
		Pageable pageable = PageRequest.of(page -1, size, sort);
		return empService.getAllOfficailEmployeeList(pageable);
	}

	@GetMapping("/getAllPromotionList")
	@PreAuthorize("hasRole('ADMIN') or hasRole('SUPERADMIN') ")
	public ResponseEntity<?> listOfAllPromotion(@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "10") int size,
			@RequestParam(name = "sort", required = false, defaultValue = "id") String id,
			@RequestParam(name = "order", required = false, defaultValue = "desc") String sortDir
		) {
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(id).ascending() : Sort.by(id).descending();
		Pageable pageable = PageRequest.of(page -1, size, sort);
		return empService.getAllPromotionList(pageable);
	}
	
	@GetMapping("/getContractList")
	@PreAuthorize("hasRole('ADMIN') or hasRole('SUPERADMIN')")
	public ResponseEntity<?> getContractList(@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "10") int size,
			@RequestParam(name = "sort", required = false, defaultValue = "id") String id,
			@RequestParam(name = "order", required = false, defaultValue = "desc") String sortDir
		) {
			Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(id).ascending() : Sort.by(id).descending();
			Pageable pageable = PageRequest.of(page -1, size, sort);
		return empService.findAllContract(pageable);
	}
	
	
	
	//getBy Id
	@GetMapping("/getEmpContract/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('SUPERADMIN')")
	public ResponseEntity<?> getContractById(@PathVariable Long id) {
		return empService.findContract(id);
	}
	
	
	@GetMapping("/getOfficialEmployee/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> getOfficialEmployeeById(@PathVariable String id){
		return empService.getEmployee(id);
	}
	
	@GetMapping("/getPromotionList/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('SUPERADMIN') ")
	public ResponseEntity<?> listOfPromotionById(@PathVariable Long id){
		return empService.getPromotionListById(id);
	}
	
	//delete
	@DeleteMapping("/getPromotion/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('SUPERADMIN') ")
	public ResponseEntity<?> deletePromotionById(@PathVariable Long id){
		return empService.deletePromotion(id);
	}
	
	
	//update employee
	@PutMapping("/getOfficialEmployee/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	@Transactional
	public ResponseEntity<?> updateOfficialEmployeeById(@PathVariable String id, @RequestBody OffiEmployeeUpdateDto updateDto){
		return empService.updateEmployee(id, updateDto);
	}

	@PutMapping("/getPromotion/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('SUPERADMIN') ")
	@Transactional
	public ResponseEntity<?> updatePromotionById(@PathVariable Long id, @Valid @RequestBody PromotionDto promotionDto){
		return empService.updatePromotion(id, promotionDto);
	}	
	
	@PutMapping("/getEmpContract/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('SUPERADMIN') ")
	@Transactional
	public ResponseEntity<?> updateEmpContractById(@PathVariable Long id, @Valid @RequestBody ContractUpdateDto updateDto){
		return empService.updateContract(id, updateDto);
	}
	
}
