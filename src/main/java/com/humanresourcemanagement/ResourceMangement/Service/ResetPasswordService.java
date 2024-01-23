package com.humanresourcemanagement.ResourceMangement.Service;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.humanresourcemanagement.ResourceMangement.Entity.ResetPassword;
import com.humanresourcemanagement.ResourceMangement.Entity.User;
import com.humanresourcemanagement.ResourceMangement.Payload.requestDto.NewPasswordDto;
import com.humanresourcemanagement.ResourceMangement.Payload.requestDto.RestPasswordDto;
import com.humanresourcemanagement.ResourceMangement.Payload.responseDto.MessageResponse;
import com.humanresourcemanagement.ResourceMangement.Repository.ResetpasswordRepo;
import com.humanresourcemanagement.ResourceMangement.Repository.UserRepository;

import jakarta.validation.Valid;

@Service
public class ResetPasswordService {
	@Autowired
	UserRepository userRepo;


	@Autowired
	ResetpasswordRepo resetRepo; 
	
	@Autowired
	PasswordEncoder encoder;
	
	@Autowired
	JavaMailSender mailSender;
	
	
	private static final long EXPIRATION_TIME_MS = 60 * 60 * 1000;
	
	public ResponseEntity<?> generateToken(@Valid RestPasswordDto resetPasswordDto){
		String email = resetPasswordDto.getEmail();
		try {
			Optional<User> optionalUser = userRepo.findByEmail(email);
			if(optionalUser.isPresent()) {
				User user = optionalUser.get();
				String token = generateToken(user);
				sendPasswordResetEmail(user, token);
				return ResponseEntity.ok().body(new MessageResponse("Reset token generated and sent successfully!! Check the email"));
			} else {
				return ResponseEntity.badRequest().body(new MessageResponse("Error: User not found by email address: "+email));
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.badRequest().body(new MessageResponse("failed to genereated token"));
	}
	
	
	
	
	public ResponseEntity<?> reset(@Valid NewPasswordDto passwordDto){
		String token = passwordDto.getToken();
		String newPassword = passwordDto.getPassword();
		
		ResetPassword resetPassword = resetRepo.findByToken(token);
		if(resetPassword == null || resetPassword.isExpired()) {
			return ResponseEntity.ok().body(new MessageResponse("Error: Either token is not valid or the token is expired"));
		}
		User user = resetPassword.getUser();
		updatePassword(user, newPassword);
		expireToken(resetPassword);
		return ResponseEntity.ok().body(new MessageResponse("Reset Password succesfully!!"));
	}
	
	
	
	public String generateToken(User user) {
		String token = UUID.randomUUID().toString();
		Date expiryDate = new Date(System.currentTimeMillis() + EXPIRATION_TIME_MS);
		
		ResetPassword passwordReset = new ResetPassword();
		passwordReset.setToken(token);
		passwordReset.setExpireDate(expiryDate);
		passwordReset.setUser(user);
		
		resetRepo.save(passwordReset); 
		return token;
	}

	public void sendPasswordResetEmail(User user, String token) {
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setFrom("dipenlimboo564@gmail.com");
		mailMessage.setTo(user.getEmail());
		String subject = "Password Reset Request";
		String text = "To reset your password, click the following link: "
                + "http://localhost:8081/api/forgot-password/reset-password?token=" + token;
		mailMessage.setSubject(subject);
		mailMessage.setText(text);
		mailSender.send(mailMessage);
	}
	
	public void updatePassword(User user, String newPassword) {
		String hashedPassword = encoder.encode(newPassword);
		user.setPassword(hashedPassword);
		userRepo.save(user);
	}
	
	public void expireToken(ResetPassword resetPassword) {
		resetPassword.setExpireDate(new Date());
		resetPassword.setToken(null);
		resetRepo.save(resetPassword);
	}
}