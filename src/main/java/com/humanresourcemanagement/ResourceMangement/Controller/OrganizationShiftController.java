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

import com.humanresourcemanagement.ResourceMangement.Payload.requestDto.FiscalDto;
import com.humanresourcemanagement.ResourceMangement.Payload.requestDto.OrgShiftDto;
import com.humanresourcemanagement.ResourceMangement.Service.OrganizationShiftService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/superAdmin")
@SecurityRequirement(name="Bearer Authentication")
public class OrganizationShiftController {

	@Autowired
	OrganizationShiftService service;
	
	@PostMapping("/addOrganizationShift")
	@PreAuthorize("hasRole('ADMIN') or hasRole('SUPERADMIN')")
	public ResponseEntity<?> addOrgShift(@Valid @RequestBody OrgShiftDto orgDto){
		return service.saveOrgShift(orgDto);
	}
	
	@GetMapping("/getOrganizationShiftList")
	@PreAuthorize("hasRole('ADMIN') or hasRole('SUPERADMIN')")
	public ResponseEntity<?> viewListOfOrganizationShift(@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "10") int size,
			@RequestParam(name = "sort", required = false, defaultValue = "id") String id,
			@RequestParam(name = "order", required = false, defaultValue = "desc") String sortDir
		) {
			Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(id).ascending() : Sort.by(id).descending();
			Pageable pageable = PageRequest.of(page -1, size, sort);
		return service.findAllOrganizationShift(pageable);
	}
	
	@GetMapping("/getOrganizationShift/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('SUPERADMIN')")
	public ResponseEntity<?> viewOrgSjiftaById(@PathVariable Long id ){
		return service.findOrgShiftById(id);
	}
	
	@DeleteMapping("/deleteOrganizationShift/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('SUPERADMIN')")
	public ResponseEntity<?> deleteOrganizationShiftById(@PathVariable Long id ){
		return service.deleteOrgShift(id);
	}
	
	@PutMapping("/getOrganizationShift/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('SUPERADMIN')")
	public ResponseEntity<?> updateOrganizationShiftById(@PathVariable Long id, @Valid @RequestBody OrgShiftDto orgDto){
		return service.updateOrganizationShift(id, orgDto);
	}
}
