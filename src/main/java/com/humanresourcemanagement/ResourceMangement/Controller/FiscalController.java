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
import com.humanresourcemanagement.ResourceMangement.Service.FiscalService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/superAdmin")
@SecurityRequirement(name="Bearer Authentication")
public class FiscalController {
		
	@Autowired
	FiscalService fiscalService;
	
	@PostMapping("/addFiscalYear")
	@PreAuthorize("hasRole('ADMIN') or hasRole('SUPERADMIN')")
	public ResponseEntity<?> addFiscalYear(@Valid @RequestBody FiscalDto fiscalDto){
		return fiscalService.saveFiscalYear(fiscalDto);
	}
	
	@GetMapping("/getFiscalYearList")
	@PreAuthorize("hasRole('ADMIN') or hasRole('SUPERADMIN')")
	public ResponseEntity<?> viewListOfFiscalYear(@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "10") int size,
			@RequestParam(name = "sort", required = false, defaultValue = "id") String id,
			@RequestParam(name = "order", required = false, defaultValue = "desc") String sortDir
		) {
			Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(id).ascending() : Sort.by(id).descending();
			Pageable pageable = PageRequest.of(page -1, size, sort);
		return fiscalService.findAllFiscalYear(pageable);
	}
	
	@GetMapping("/getFiscalYear/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('SUPERADMIN')")
	public ResponseEntity<?> viewFiscalYearById(@PathVariable Long id ){
		return fiscalService.findFiscalYearById(id);
	}
	
	@DeleteMapping("/deleteFiscalYear/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('SUPERADMIN')")
	public ResponseEntity<?> deleteFiscalYearById(@PathVariable Long id ){
		return fiscalService.deleteFiscalYear(id);
	}
	
	@PutMapping("/getFiscalYear/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('SUPERADMIN')")
	public ResponseEntity<?> updateFiscalYearById(@PathVariable Long id, @Valid @RequestBody FiscalDto fiscalDto){
		return fiscalService.updateFiscalYear(id, fiscalDto);
	}
}
