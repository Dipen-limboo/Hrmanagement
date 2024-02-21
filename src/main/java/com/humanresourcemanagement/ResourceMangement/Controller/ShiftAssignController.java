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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.humanresourcemanagement.ResourceMangement.Payload.requestDto.ShiftAssignDto;
import com.humanresourcemanagement.ResourceMangement.Service.ShiftAssignService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/superAdmin")
@SecurityRequirement(name="Bearer Authentication")
public class ShiftAssignController {
	
	@Autowired
	ShiftAssignService service;
	
	@PostMapping("/addShiftAssign")
	@PreAuthorize("hasRole('ADMIN') or hasRole('SUPERADMIN')")
	public ResponseEntity<?> createShiftAssign(@Valid @RequestBody ShiftAssignDto shiftDto){
		return service.saveShiftAssign(shiftDto);
	}
	
	@GetMapping("/getShiftAssignList")
	@PreAuthorize("hasRole('ADMIN') or hasRole('SUPERADMIN')")
	public ResponseEntity<?> viewShiftAssignList(@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "10") int size,
			@RequestParam(name = "sort", required = false, defaultValue = "id") String id,
			@RequestParam(name = "order", required = false, defaultValue = "desc") String sortDir
		) {
			Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(id).ascending() : Sort.by(id).descending();
			Pageable pageable = PageRequest.of(page -1, size, sort);
		return service.findAllShiftAssign(pageable);
	}
	
	@GetMapping("/getShiftAssign/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('SUPERADMIN')")
	public ResponseEntity<?> viewShiftAssignListById(@PathVariable Long id){
		return service.findShiftAssignById(id);
	}
	
	@DeleteMapping("/deleteShiftAssign/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('SUPERADMIN')")
	public ResponseEntity<?> deleteShiftAssignListById(@PathVariable Long id){
		return service.deleteShiftAssign(id);
	}
	
	@PutMapping("/getShiftAssign/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('SUPERADMIN')")
	public ResponseEntity<?> updateShiftAssignListById(@PathVariable Long id, @Valid @RequestBody ShiftAssignDto shiftDto){
		return service.updateShiftAssign(id, shiftDto);
	}
}
