package com.humanresourcemanagement.ResourceMangement.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.humanresourcemanagement.ResourceMangement.Entity.FiscalYear;
import com.humanresourcemanagement.ResourceMangement.Payload.requestDto.FiscalDto;
import com.humanresourcemanagement.ResourceMangement.Payload.responseDto.FiscalResponseDto;
import com.humanresourcemanagement.ResourceMangement.Repository.FiscalYearRepo;

import jakarta.validation.Valid;

@Service
public class FiscalService {
	
	@Autowired
	FiscalYearRepo fiscalRepo;

	public ResponseEntity<?> saveFiscalYear(@Valid FiscalDto fiscalDto) {
		if(fiscalRepo.existsByYear(fiscalDto.getYear()))
			return ResponseEntity.badRequest().body("Already exists by the year");
		FiscalYear fiscal = new FiscalYear();
		fiscal.setYear(fiscalDto.getYear());
		fiscal.setFromDate(fiscalDto.getFrom_date());
		fiscal.setToDate(fiscalDto.getTo_date());
		fiscal.setStatus(fiscalDto.isStatus());
		fiscalRepo.save(fiscal);
		return ResponseEntity.ok().body(fiscal);
	}

	public ResponseEntity<?> findAllFiscalYear(Pageable pageable) {
		Page<FiscalYear> fiscalList = fiscalRepo.findAll(pageable);
		List<FiscalResponseDto> responseDtoList = new ArrayList<>();
		if(fiscalList.isEmpty())
			return ResponseEntity.badRequest().body("No any fiscal year is found!");
		for(FiscalYear fiscal: fiscalList) {
			FiscalResponseDto responseDto = new FiscalResponseDto();
			responseDto.setFiscal_id(fiscal.getId());
			responseDto.setYear(fiscal.getYear());
			responseDto.setFrom_date(fiscal.getFromDate());
			responseDto.setTo_date(fiscal.getToDate());
			responseDto.setStatus(fiscal.getStatus());
			responseDtoList.add(responseDto);
		}
		return ResponseEntity.ok().body(responseDtoList);
	}

	public ResponseEntity<?> findFiscalYearById(Long id) {
		Optional<FiscalYear> optionalFiscalYear = fiscalRepo.findById(id);
		if(optionalFiscalYear.isEmpty())
			return ResponseEntity.badRequest().body("Fiscal Year not found by id " +id);
		FiscalYear fiscal = optionalFiscalYear.get();
		FiscalResponseDto responseDto = new FiscalResponseDto();
		responseDto.setFiscal_id(fiscal.getId());
		responseDto.setYear(fiscal.getYear());
		responseDto.setFrom_date(fiscal.getFromDate());
		responseDto.setTo_date(fiscal.getToDate());
		responseDto.setStatus(fiscal.getStatus());
		return ResponseEntity.ok().body(responseDto);
	}

	public ResponseEntity<?> deleteFiscalYear(Long id) {
		if(!fiscalRepo.existsById(id))
			return ResponseEntity.badRequest().body("Fiscal Year not found by id " +id);
		fiscalRepo.deleteById(id);
		return ResponseEntity.ok().body("Successfully, deleted the fiscal year with fiscal id " +id);
	}

	public ResponseEntity<?> updateFiscalYear(Long id, @Valid FiscalDto fiscalDto) {
		Optional<FiscalYear> optionalFiscalYear = fiscalRepo.findById(id);
		if(optionalFiscalYear.isEmpty())
			return ResponseEntity.badRequest().body("Fiscal Year not found by id " +id);
		FiscalYear fiscal = optionalFiscalYear.get();
		fiscal.setYear(fiscalDto.getYear());
		fiscal.setFromDate(fiscalDto.getFrom_date());
		fiscal.setToDate(fiscalDto.getTo_date());
		fiscal.setStatus(fiscalDto.isStatus());
		fiscalRepo.save(fiscal);
		return ResponseEntity.ok().body(fiscal);
	}
	
	
}
