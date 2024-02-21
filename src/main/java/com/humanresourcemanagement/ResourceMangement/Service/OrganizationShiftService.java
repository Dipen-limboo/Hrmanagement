package com.humanresourcemanagement.ResourceMangement.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.humanresourcemanagement.ResourceMangement.Entity.OrgShift;
import com.humanresourcemanagement.ResourceMangement.Payload.requestDto.OrgShiftDto;
import com.humanresourcemanagement.ResourceMangement.Payload.responseDto.OrgShiftResponseDto;
import com.humanresourcemanagement.ResourceMangement.Repository.OrgShiftRepo;

import jakarta.validation.Valid;

@Service
public class OrganizationShiftService {

	@Autowired
	OrgShiftRepo orgRepo;
	
	public ResponseEntity<?> saveOrgShift(@Valid OrgShiftDto orgDto) {
		OrgShift org = new OrgShift();
		if((orgDto.isDefaults()==true && orgDto.isNight()==true)||(orgDto.isDefaults()==false && orgDto.isNight()==false))
			return ResponseEntity.badRequest().body("Select one shift: Night or Default ");
		org.setInStart(orgDto.getIn_start());
		org.setInEnd(orgDto.getIn_end());
		org.setOutStart(orgDto.getOut_start());
		org.setOutEnd(orgDto.getOut_end());
		if(orgDto.isDefaults()==true || orgDto.isNight() == false) {
			org.setDefaults(true);
			org.setNight(false);
		} else {
			org.setDefaults(false);
			org.setNight(true);
		}
		orgRepo.save(org);
		return ResponseEntity.ok().body(org);
	}

	public ResponseEntity<?> findAllOrganizationShift(Pageable pageable) {
		Page<OrgShift> orgShiftList = orgRepo.findAll(pageable);
		List<OrgShiftResponseDto> responseDtoList = new ArrayList<>();
		if(orgShiftList.isEmpty())
			return ResponseEntity.badRequest().body("No any shift is in the database");
		for(OrgShift shift: orgShiftList ) {
			OrgShiftResponseDto responseDto = new OrgShiftResponseDto();
			responseDto.setOrg_shift_id(shift.getId());
			responseDto.setIn_start(shift.getInStart());
			responseDto.setIn_end(shift.getInEnd());
			responseDto.setOut_end(shift.getOutEnd());
			responseDto.setOut_start(shift.getOutStart());
			responseDto.setDefaults(shift.isDefaults());
			responseDto.setNight(shift.isNight());
			responseDtoList.add(responseDto);
		}
		return ResponseEntity.ok().body(responseDtoList);
	}

	public ResponseEntity<?> findOrgShiftById(Long id) {
		Optional<OrgShift> optionalOrgShift = orgRepo.findById(id);
		if(optionalOrgShift.isEmpty())
			return ResponseEntity.badRequest().body("Organization Shift is not present with organization_shift id "+id+ ".");
		OrgShift shift = optionalOrgShift.get();
		OrgShiftResponseDto responseDto = new OrgShiftResponseDto();
		responseDto.setOrg_shift_id(shift.getId());
		responseDto.setIn_start(shift.getInStart());
		responseDto.setIn_end(shift.getInEnd());
		responseDto.setOut_end(shift.getOutEnd());
		responseDto.setOut_start(shift.getOutStart());
		responseDto.setDefaults(shift.isDefaults());
		responseDto.setNight(shift.isNight());
		return ResponseEntity.ok().body(responseDto);
	}

	public ResponseEntity<?> deleteOrgShift(Long id) {
		if(!orgRepo.existsById(id))
			return ResponseEntity.badRequest().body("Organization shift not found with id " +id + ".");
		orgRepo.deleteById(id);
		return ResponseEntity.ok().body("Deled the organization shift with id " + id + " successfully!!");
	}

	public ResponseEntity<?> updateOrganizationShift(Long id, @Valid OrgShiftDto orgDto) {
		Optional<OrgShift> optionalOrgShift = orgRepo.findById(id);
		if(optionalOrgShift.isEmpty())
			return ResponseEntity.badRequest().body("Organization Shift is not present with organization_shift id "+id+ ".");
		OrgShift org = optionalOrgShift.get();
		if((orgDto.isDefaults()==true && orgDto.isNight()==true)||(orgDto.isDefaults()==false && orgDto.isNight()==false))
			return ResponseEntity.badRequest().body("Select one shift: Night or Default ");
		org.setInStart(orgDto.getIn_start());
		org.setInEnd(orgDto.getIn_end());
		org.setOutStart(orgDto.getOut_start());
		org.setOutEnd(orgDto.getOut_end());
		if(orgDto.isDefaults()==true || orgDto.isNight() == false) {
			org.setDefaults(true);
			org.setNight(false);
		} else {
			org.setDefaults(false);
			org.setNight(true);
		}
		orgRepo.save(org);
		return ResponseEntity.ok().body(org);
	}
	
}
