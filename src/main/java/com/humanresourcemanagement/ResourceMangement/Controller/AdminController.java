package com.humanresourcemanagement.ResourceMangement.Controller;

import java.io.IOException;

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
import org.springframework.web.multipart.MultipartFile;

import com.humanresourcemanagement.ResourceMangement.Payload.requestDto.BankDto;
import com.humanresourcemanagement.ResourceMangement.Payload.requestDto.BranchDto;
import com.humanresourcemanagement.ResourceMangement.Payload.requestDto.DepartmentDto;
import com.humanresourcemanagement.ResourceMangement.Payload.requestDto.DesignationDto;
import com.humanresourcemanagement.ResourceMangement.Payload.requestDto.GradeDto;
import com.humanresourcemanagement.ResourceMangement.Payload.requestDto.JobTypeDto;
import com.humanresourcemanagement.ResourceMangement.Payload.requestDto.LeaveUpdateDto;
import com.humanresourcemanagement.ResourceMangement.Payload.requestDto.RoasterUpdateDto;
import com.humanresourcemanagement.ResourceMangement.Payload.requestDto.SubDepartmentDto;
import com.humanresourcemanagement.ResourceMangement.Payload.requestDto.TransferUpdateDto;
import com.humanresourcemanagement.ResourceMangement.Payload.requestDto.WeekendUpdateDto;
import com.humanresourcemanagement.ResourceMangement.Payload.requestDto.WorkTypeDto;
import com.humanresourcemanagement.ResourceMangement.Service.AdminService;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/superAdmin")
public class AdminController {

	@Autowired
	AdminService service;
	
	
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
	
	@GetMapping("/getWorkingTypeLists")
	@PreAuthorize("hasRole('ADMIN') or hasRole('SUPERADMIN')")
	public ResponseEntity<?> listOfWorkingType(){
		return service.findWorkingType();
	}
	
	@GetMapping("/getJobTypeLists")
	@PreAuthorize("hasRole('ADMIN') or hasRole('SUPERADMIN')")
	public ResponseEntity<?> listOfJobType(){
		return service.findJobType();
	}
	
	@GetMapping("/getOrganizationList")
	@PreAuthorize("hasRole('ADMIN') or hasRole('SUPERADMIN')")
	public ResponseEntity<?> listOfOrganization(){
		return service.findOrg();
	}
	
	@GetMapping("/getGradeList")
	@PreAuthorize("hasRole('ADMIN') or hasRole('SUPERADMIN')")
	public ResponseEntity<?> listOfGrade(@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "10") int size,
			@RequestParam(name = "sort", required = false, defaultValue = "id") String id,
			@RequestParam(name = "order", required = false, defaultValue = "desc") String sortDir
		) {
			Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(id).ascending() : Sort.by(id).descending();
			Pageable pageable = PageRequest.of(page -1, size, sort);
		return service.findGrade(pageable);
	}
	
	@GetMapping("/accountLists")
	@PreAuthorize("hasRole('ADMIN') or hasRole('SUPERADMIN')")
	public ResponseEntity<?> listOfaccounts(@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "10") int size,
			@RequestParam(name = "sort", required = false, defaultValue = "id") String id,
			@RequestParam(name = "order", required = false, defaultValue = "desc") String sortDir
		) {
			Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(id).ascending() : Sort.by(id).descending();
			Pageable pageable = PageRequest.of(page -1, size, sort);
		return service.findAllBank(pageable);
	}
	
	@GetMapping("/getEmpBankList")
	@PreAuthorize("hasRole('ADMIN') or hasRole('SUPERADMIN')")
	public ResponseEntity<?> listOfEmpBank(@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "10") int size,
			@RequestParam(name = "sort", required = false, defaultValue = "id") String id,
			@RequestParam(name = "order", required = false, defaultValue = "desc") String sortDir
		) {
			Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(id).ascending() : Sort.by(id).descending();
			Pageable pageable = PageRequest.of(page -1, size, sort);
		return service.findEmpBank(pageable);
	}
	
	@GetMapping("/getbranchList")
	@PreAuthorize("hasRole('ADMIN') or hasRole('SUPERADMIN')")
	public ResponseEntity<?> listOfBranch(){
		return service.findBranch();
	}
	
	@GetMapping("/getTransferList")
	@PreAuthorize("hasRole('ADMIN') or hasRole('SUPERADMIN')")
	public ResponseEntity<?> listOfTransferEmp(@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "10") int size,
			@RequestParam(name = "sort", required = false, defaultValue = "id") String id,
			@RequestParam(name = "order", required = false, defaultValue = "desc") String sortDir
		) {
			Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(id).ascending() : Sort.by(id).descending();
			Pageable pageable = PageRequest.of(page -1, size, sort);
		return service.findTransfer(pageable);
	}
	
	
	@GetMapping("/getRoasterList")
	@PreAuthorize("hasRole('ADMIN') or hasRole('SUPERADMIN')")
	public ResponseEntity<?> listOfTimeSheet(@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "10") int size,
			@RequestParam(name = "sort", required = false, defaultValue = "id") String id,
			@RequestParam(name = "order", required = false, defaultValue = "desc") String sortDir
		) {
			Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(id).ascending() : Sort.by(id).descending();
			Pageable pageable = PageRequest.of(page -1, size, sort);
		return service.findTimeSheet(pageable);
	}
	
	@GetMapping("/getLeaveLists")
	@PreAuthorize("hasRole('ADMIN') or hasRole('SUPERADMIN')")
	public ResponseEntity<?> listOfLeaveType(){
		return service.findAllLeave();
	}
	
	@GetMapping("/getWeekendList")
	@PreAuthorize("hasRole('ADMIN') or hasRole('SUPERADMIN')")
	public ResponseEntity<?> listOfWeekend(@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "10") int size,
			@RequestParam(name = "sort", required = false, defaultValue = "id") String id,
			@RequestParam(name = "order", required = false, defaultValue = "desc") String sortDir
		) {
			Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(id).ascending() : Sort.by(id).descending();
			Pageable pageable = PageRequest.of(page -1, size, sort);
		return service.findAllWeekend(pageable);
	}
	
	
	
	
	//delete
	@DeleteMapping("deleteDepartment/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('SUPERADMIN')")
	public ResponseEntity<?> deleteDepartment(@PathVariable Long id){
		return service.deleteDepartmentById(id);
	}
	
	@DeleteMapping("deleteDesignation/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('SUPERADMIN')")
	public ResponseEntity<?> deleteDesignation(@PathVariable Long id){
		return service.deleteDesignationById(id);
	}
	
	@DeleteMapping("deleteSubDepartment/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('SUPERADMIN')")
	public ResponseEntity<?> deleteSubDepartment(@PathVariable Long id){
		return service.deleteSubDepartmentById(id);
	}
	
	@DeleteMapping("/deleteWorkType/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('SUPERADMIN')")
	public ResponseEntity<?> deleteWorkType(@PathVariable Long id){
		return service.deleteWorkTypeById(id);
	}
	
	@DeleteMapping("/deleteJobType/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('SUPERADMIN')")
	public ResponseEntity<?> deleteJobType(@PathVariable Long id){
		return service.deleteJobTypeById(id);
	}
	
	@DeleteMapping("/deleteOrganization/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('SUPERADMIN')")
	public ResponseEntity<?> deleteOrganization(@PathVariable Long id){
		return service.deleteOrgById(id);
	}
	
	@DeleteMapping("/deleteGrade/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('SUPERADMIN')")
	public ResponseEntity<?> deleteGrade(@PathVariable Long id){
		return service.deleteGradeById(id);
	}
	
	@DeleteMapping("/deleteAccount/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('SUPERADMIN') ")
	@Transactional
	public ResponseEntity<?> deleteAccount(@PathVariable Long id){
		return service.delete(id);
	}
	
	@DeleteMapping("/deleteBranch/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('SUPERADMIN')")
	@Transactional
	public ResponseEntity<?> deleteBranch(@PathVariable Long id){
		return service.deleteBranchById(id);
	}
	
	@DeleteMapping("/deleteTransfer/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('SUPERADMIN')")
	@Transactional
	public ResponseEntity<?> deleteTransfer(@PathVariable Long id){
		return service.deleteTransferById(id);
	}
	
	@DeleteMapping("/deleteRoaster/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('SUPERADMIN')")
	public ResponseEntity<?> deleteTimeSheet(@PathVariable Long id){
		return service.deleteTimeSheetById(id);
	}
	
	@DeleteMapping("/deleteLeaveInfo/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('SUPERADMIN')")
	public ResponseEntity<?> deleteleaveInfo(@PathVariable Long id){
		return service.deleteLeaveInfoById(id);
	}
	
	@DeleteMapping("/deleteWeekend/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('SUPERADMIN')")
	public ResponseEntity<?> deleteWeekend(@PathVariable Long id){
		return service.deleteWeekendById(id);
	}

	
	
	
	//getById
	@GetMapping("/getDepartment/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('SUPERADMIN')")
	public ResponseEntity<?> getDepartment(@PathVariable Long id){
		return service.getDepartmentById(id);
	}
	
	@GetMapping("/getDesignation/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('SUPERADMIN')")
	public ResponseEntity<?> getDesignation(@PathVariable Long id){
		return service.getDesignationById(id);
	}
	
	@GetMapping("/getSubDepartment/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('SUPERADMIN')")
	public ResponseEntity<?> getSubDepartment(@PathVariable Long id){
		return service.getSubDepartmentById(id);
	}
	
	@GetMapping("/getWorkType/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('SUPERADMIN')")
	public ResponseEntity<?> getWorkType(@PathVariable Long id){
		return service.getWorkTypeId(id);
	}
	
	@GetMapping("/getJobType/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('SUPERADMIN')")
	public ResponseEntity<?> getJobType(@PathVariable Long id){
		return service.getJobTypeId(id);
	}
	
	@GetMapping("/getOrganization/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('SUPERADMIN')")
	public ResponseEntity<?> getOrganization(@PathVariable Long id){
		return service.getOrganizationById(id);
	}
	
	@GetMapping("/getGrade/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('SUPERADMIN')")
	public ResponseEntity<?> getGrade(@PathVariable Long id){
		return service.getGradeById(id);
	}

	@GetMapping("/getAccount/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('SUPERADMIN')")
	@Transactional
	public ResponseEntity<?> getAccountById(@PathVariable Long id){
		return service.getAccount(id);
	}
	
	@GetMapping("/getBranch/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('SUPERADMIN')")
	@Transactional
	public ResponseEntity<?> getBranch(@PathVariable Long id){
		return service.getBranchById(id);
	}

	@GetMapping("/getTransfer/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('SUPERADMIN')")
	@Transactional
	public ResponseEntity<?> getTransfer(@PathVariable Long id){
		return service.getTransferById(id);
	}
	
	@GetMapping("/getRoaster/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('SUPERADMIN')")
	@Transactional
	public ResponseEntity<?> getTimeSheet(@PathVariable Long id){
		return service.getTimeSheetById(id);
	}
	
	@GetMapping("/getLeaveInfo/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('SUPERADMIN')")
	public ResponseEntity<?> getleaveInfo(@PathVariable Long id){
		return service.getLeaveInfoById(id);
	}
	
	@GetMapping("/getWeekend/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('SUPERADMIN')")
	public ResponseEntity<?> getWeekend(@PathVariable Long id){
		return service.getWeekendById(id);
	}
	
	
	
	
	//update
	@PutMapping("/getDepartment/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('SUPERADMIN')")
	public ResponseEntity<?> updateDepartmentById(@PathVariable Long id, @RequestBody DepartmentDto departmentDto){
		return service.updateDepartment(id, departmentDto);
	}
	
	@PutMapping("/getDesignation/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('SUPERADMIN')")
	public ResponseEntity<?> updateDesignationById(@PathVariable Long id, @RequestBody DesignationDto designationDto){
		return service.updateDesignation(id, designationDto);
	}
	
	@PutMapping("/getSubDepartment/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('SUPERADMIN')")
	public ResponseEntity<?> updateSubDepartmentById(@PathVariable Long id, @RequestBody SubDepartmentDto subDepartmentDto){
		return service.updateSubDepartment(id, subDepartmentDto);
	}
	
	@PutMapping("/getWorkType/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('SUPERADMIN')")
	public ResponseEntity<?> updateWorkType(@PathVariable Long id, @RequestBody WorkTypeDto workTypeDto){
		return service.updateWorkTypeById(id, workTypeDto);
	}
	
	@PutMapping("/getJobType/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('SUPERADMIN')")
	public ResponseEntity<?> updateJObType(@PathVariable Long id, @RequestBody JobTypeDto jobTypeDto){
		return service.updateJobTypeById(id, jobTypeDto);
	}
	
	@PutMapping("/getOrganization/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> saveOrganization(@PathVariable Long id, @Valid @RequestParam(required=false) String org_name,
			@RequestParam(required=false) String org_address,
			@RequestParam(required=false) String org_email,
			@RequestParam(required=false) String org_phone,
			@RequestParam(required=false) String org_website,
			@RequestParam(required=false) MultipartFile org_logo_path) throws IOException{
		return service.UpdateOrgById(id, org_name, org_address, org_email, org_phone, org_website, org_logo_path);
	}
	
	@PutMapping("/getGrade/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('SUPERADMIN')")
	public ResponseEntity<?> updateGrade(@PathVariable Long id, @RequestBody GradeDto gradeDto){
		return service.updateGradeById(id, gradeDto);
	}
	
	@PutMapping("/getAccount/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('SUPERADMIN') ")
	public ResponseEntity<?> updateAccountById(@PathVariable Long id, @RequestBody BankDto bankDto){
		return service.updateAccount(id, bankDto);
	}
	
	@PutMapping("/getBranch/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('SUPERADMIN') ")
	public ResponseEntity<?> updateBranchById(@PathVariable Long id, @RequestBody BranchDto branchDto){
		return service.updateBranch(id, branchDto);
	}
	
	@PutMapping("/getTransfer/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('SUPERADMIN')")
	@Transactional
	public ResponseEntity<?> updateTransferWithId(@PathVariable Long id, @Valid @RequestBody TransferUpdateDto transferDto ){
		return service.updateTransfer(id, transferDto);
	}
	
	@PutMapping("/getRoaster/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('SUPERADMIN') ")
	public ResponseEntity<?> updateTimeSheetById(@PathVariable Long id, @RequestBody RoasterUpdateDto timeDto){
		return service.updateTimeSheet(id, timeDto);
	}
	
	@PutMapping("/getLeaveInfo/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('SUPERADMIN')")
	public ResponseEntity<?> updateLeaveInfoById(@PathVariable Long id, @RequestBody LeaveUpdateDto updateDto){
		return service.updateLeaveInfo(id, updateDto);
	}
	
	@PutMapping("/getWeekend/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('SUPERADMIN')")
	public ResponseEntity<?> updateWeekendById(@PathVariable Long id, @RequestBody WeekendUpdateDto updateDto){
		return service.updateWeekend(id, updateDto);
	}

}
