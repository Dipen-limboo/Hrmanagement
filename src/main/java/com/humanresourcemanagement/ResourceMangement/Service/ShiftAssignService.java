package com.humanresourcemanagement.ResourceMangement.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.humanresourcemanagement.ResourceMangement.Entity.EmployeeOfficialInfo;
import com.humanresourcemanagement.ResourceMangement.Entity.ShiftAssign;
import com.humanresourcemanagement.ResourceMangement.Payload.requestDto.ShiftAssignDto;
import com.humanresourcemanagement.ResourceMangement.Payload.responseDto.ShiftAssignResponseDto;
import com.humanresourcemanagement.ResourceMangement.Repository.EmployeeOfficialInfoRepo;
import com.humanresourcemanagement.ResourceMangement.Repository.ShiftAssignRepo;

import jakarta.validation.Valid;

@Service
public class ShiftAssignService {

	@Autowired
	ShiftAssignRepo shiftRepo;
	
	@Autowired
	EmployeeOfficialInfoRepo empRepo;
	
	public ResponseEntity<?> saveShiftAssign(@Valid ShiftAssignDto shiftDto) {
		ShiftAssign shift = new ShiftAssign();
		shift.setShiftDate(shiftDto.getShift_date());
		shift.setDay(shiftDto.getShift_date().getDayOfWeek().toString().toLowerCase());
		Optional<EmployeeOfficialInfo> employee = empRepo.findById(shiftDto.getEmployee_id());
		if(employee.isEmpty())
			return ResponseEntity.badRequest().body("Employee not found with id " +shiftDto.getEmployee_id() + "...");
		shift.setEmployee(employee.get());
		shiftRepo.save(shift);
		return ResponseEntity.ok().body(shift);
	}

	public ResponseEntity<?> findAllShiftAssign(Pageable pageable) {
		Page<ShiftAssign> shiftList = shiftRepo.findAll(pageable);
		List<ShiftAssignResponseDto> responseDtoList = new ArrayList<>();
		if(shiftList.isEmpty())
			return ResponseEntity.badRequest().body("No any shift assign is found");
		for(ShiftAssign shift: shiftList) {
			ShiftAssignResponseDto responseDto = new ShiftAssignResponseDto();
			responseDto.setShift_assign_id(shift.getId());
			responseDto.setDay(shift.getDay());
			responseDto.setShift_date(shift.getShiftDate());
			responseDto.setEmployee_id(shift.getEmployee().getId());
			responseDtoList.add(responseDto);
		}
		return ResponseEntity.ok().body(responseDtoList);
	}

	public ResponseEntity<?> findShiftAssignById(Long id) {
		Optional<ShiftAssign> optionalShiftAssign = shiftRepo.findById(id);
		if(optionalShiftAssign.isEmpty())
			return ResponseEntity.badRequest().body("Shift Assign is not found with id " +id + "..");
		ShiftAssign shift = optionalShiftAssign.get();
		ShiftAssignResponseDto responseDto = new ShiftAssignResponseDto();
		responseDto.setShift_assign_id(shift.getId());
		responseDto.setDay(shift.getDay());
		responseDto.setShift_date(shift.getShiftDate());
		responseDto.setEmployee_id(shift.getEmployee().getId());
		return ResponseEntity.ok().body(responseDto);
	}

	public ResponseEntity<?> deleteShiftAssign(Long id) {
		if(!shiftRepo.existsById(id)) 
			return ResponseEntity.badRequest().body("Shift Assign id " + id + " is not found.");
		shiftRepo.deleteById(id);
		return ResponseEntity.ok().body("Succesfully deleted the shift assign id " + id + ".");
	}

	public ResponseEntity<?> updateShiftAssign(Long id, @Valid ShiftAssignDto shiftDto) {
		Optional<ShiftAssign> optionalShiftAssign = shiftRepo.findById(id);
		if(optionalShiftAssign.isEmpty())
			return ResponseEntity.badRequest().body("Shift Assign is not found with id " +id + "..");
		ShiftAssign shift = optionalShiftAssign.get();
		shift.setShiftDate(shiftDto.getShift_date());
		shift.setDay(shiftDto.getShift_date().getDayOfWeek().toString().toLowerCase());
		Optional<EmployeeOfficialInfo> employee = empRepo.findById(shiftDto.getEmployee_id());
		if(employee.isEmpty())
			return ResponseEntity.badRequest().body("Employee not found with id " +shiftDto.getEmployee_id() + "...");
		shift.setEmployee(employee.get());
		shiftRepo.save(shift);
		return ResponseEntity.ok().body(shift);
	}
	
}
