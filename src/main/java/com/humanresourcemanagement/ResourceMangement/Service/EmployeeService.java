package com.humanresourcemanagement.ResourceMangement.Service;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.humanresourcemanagement.ResourceMangement.Entity.Employee;
import com.humanresourcemanagement.ResourceMangement.Payload.requestDto.EmployeeRegisterDto;
import com.humanresourcemanagement.ResourceMangement.Payload.responseDto.MessageResponse;
import com.humanresourcemanagement.ResourceMangement.Repository.EmployeeRepo;
import com.humanresourcemanagement.ResourceMangement.Utility.Utility;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import net.bytebuddy.utility.RandomString;

@Service
public class EmployeeService {
	
	@Autowired
	EmployeeRepo empRepo;
	
	@Autowired
    private JavaMailSender mailSender;
	
	//save employee
	public ResponseEntity<?> saveEmployee(@Valid EmployeeRegisterDto employeeDto, Authentication auth, HttpServletRequest request) throws UnsupportedEncodingException, MessagingException {
		if(empRepo.existsByEmail(employeeDto.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: "+employeeDto.getEmail() +" already exists."));
		}
		Employee emp = new Employee();
		emp.setFirstName(employeeDto.getFirstName());
		emp.setMiddleName(employeeDto.getMiddleName());
		emp.setLastName(employeeDto.getLastName());
		emp.setEmail(employeeDto.getEmail());
		emp.setPassword(employeeDto.getPassword());
		String token = RandomString.make(30);
		emp.setToken(token); 
		String email = employeeDto.getEmail();
		String passwordLink = Utility.getSiteURL(request) + "/reset_password?token="+token;
		sendTokenToEmail(passwordLink, email);
		empRepo.save(emp);
		return null;
	}

	private void sendTokenToEmail(String passwordLink, String email) throws UnsupportedEncodingException, MessagingException{
		MimeMessage message = mailSender.createMimeMessage(); 
    	MimeMessageHelper mhelper = new MimeMessageHelper(message);
    	
    	mhelper.setFrom("dipenlimboo564@gmail.com", "Limbo Dipen");
    	
    	mhelper.setTo(email);
    	
    	String subject = "Here's the Link to set Your Password ";
    	
    	String content = " <p> Hello" + email + " </p>"
    			+"<p>You have to set your passowrd</p>"
    			+"<p>Click the link below to change your password</p>"
    			+"<p><a href=\"" +passwordLink+ "\"> Change your passowrd</p>";
    	mhelper.setSubject(subject);
    	mhelper.setText(content, true);
    	mailSender.send(message);
	}
	
}
