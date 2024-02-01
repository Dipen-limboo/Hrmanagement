package com.humanresourcemanagement.ResourceMangement.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.humanresourcemanagement.ResourceMangement.Payload.requestDto.DepartmentDto;
import com.humanresourcemanagement.ResourceMangement.Payload.requestDto.DesignationDto;
import com.humanresourcemanagement.ResourceMangement.Payload.requestDto.SubDepartmentDto;
import com.humanresourcemanagement.ResourceMangement.Service.SuperAdminService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/superAdmin")
public class SuperAdminController {

	@Autowired
	SuperAdminService service;
	
	
	@GetMapping("/departmentLists")
	@PreAuthorize("hasRole('ADMIN') or hasRole('SUPERADMIN')")
	public ResponseEntity<?> listOfDepartment(){
		return service.findDepartment();
	}
	
	@GetMapping("/designationLists")
	@PreAuthorize("hasRole('ADMIN') or hasRole('SUPERADMIN')")
	public ResponseEntity<?> listOfDesignation(@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "10") int size,
			@RequestParam(name = "sort", required = false, defaultValue = "id") String id,
			@RequestParam(name = "order", required = false, defaultValue = "desc") String sortDir
		) {
			Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(id).ascending() : Sort.by(id).descending();
			Pageable pageable = PageRequest.of(page -1, size, sort);
		return service.findAllDesgination(pageable);
	}
	
	@GetMapping("/sub_departmentLists")
	@PreAuthorize("hasRole('ADMIN') or hasRole('SUPERADMIN')")
	public ResponseEntity<?> listOfSubDepartment(){
		return service.findSubDepartment();
	}
	
	
	
	
	//delete
	@DeleteMapping("deleteDepartment/{id}")
	@PreAuthorize("hasRole('SUPERADMIN')")
	public ResponseEntity<?> deleteDepartment(@PathVariable Long id){
		return service.deleteDepartmentById(id);
	}
	
	@DeleteMapping("deleteDesignation/{id}")
	@PreAuthorize("hasRole('SUPERADMIN')")
	public ResponseEntity<?> deleteDesignation(@PathVariable Long id){
		return service.deleteDesignationById(id);
	}
	
	@DeleteMapping("deleteSubDepartment/{id}")
	@PreAuthorize("hasRole('SUPERADMIN')")
	public ResponseEntity<?> deleteSubDepartment(@PathVariable Long id){
		return service.deleteSubDepartmentById(id);
	}
	
	
	
	//getById
	@GetMapping("/getDepartment/{id}")
	@PreAuthorize("hasRole('SUPERADMIN')")
	public ResponseEntity<?> getDepartment(@PathVariable Long id){
		return service.getDepartmentById(id);
	}
	
	@GetMapping("/getDesignation/{id}")
	@PreAuthorize("hasRole('SUPERADMIN')")
	public ResponseEntity<?> getDesignation(@PathVariable Long id){
		return service.getDesignationById(id);
	}
	
	@GetMapping("/getSubDepartment/{id}")
	@PreAuthorize("hasRole('SUPERADMIN')")
	public ResponseEntity<?> getSubDepartment(@PathVariable Long id){
		return service.getSubDepartmentById(id);
	}
	
	
	
	//update
	@PutMapping("/getDepartment/{id}")
	@PreAuthorize("hasRole('SUPERADMIN')")
	public ResponseEntity<?> updateDepartmentById(@PathVariable Long id, @RequestBody DepartmentDto departmentDto){
		return service.updateDepartment(id, departmentDto);
	}
	
	@PutMapping("/getDesignation/{id}")
	@PreAuthorize("hasRole('SUPERADMIN')")
	public ResponseEntity<?> updateDesignationById(@PathVariable Long id, @RequestBody DesignationDto designationDto){
		return service.updateDesignation(id, designationDto);
	}
	
	@PutMapping("/getSubDepartment/{id}")
	@PreAuthorize("hasRole('SUPERADMIN')")
	public ResponseEntity<?> updateSubDepartmentById(@PathVariable Long id, @RequestBody SubDepartmentDto subDepartmentDto){
		return service.updateSubDepartment(id, subDepartmentDto);
	}
}
