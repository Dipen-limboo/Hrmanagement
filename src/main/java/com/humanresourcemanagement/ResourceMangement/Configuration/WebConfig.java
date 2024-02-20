package com.humanresourcemanagement.ResourceMangement.Configuration;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.humanresourcemanagement.ResourceMangement.security.jwt.AuthEntryPointJwt;
import com.humanresourcemanagement.ResourceMangement.security.jwt.AuthTokenFilter;
import com.humanresourcemanagement.ResourceMangement.security.service.UserDetailsServiceImpl;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;



@Configuration
@EnableMethodSecurity
@SecurityScheme(
	    name = "Bearer Authentication",
	    type = SecuritySchemeType.HTTP,
	    bearerFormat = "JWT",
	    scheme = "bearer"
	)
//(securedEnabled = true,
//jsr250Enabled = true,
//prePostEnabled = true) // by default
public class WebConfig {
  

  
  @Autowired
  UserDetailsServiceImpl userDetailsService;

  @Autowired
  private AuthEntryPointJwt unauthorizedHandler;

  
  @Bean
  public AuthTokenFilter authenticationJwtTokenFilter() {
    return new AuthTokenFilter();
  }
  
  @Bean
  public DaoAuthenticationProvider authenticationProvider() {
      DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
       
      authProvider.setUserDetailsService(userDetailsService);
      authProvider.setPasswordEncoder(passwordEncoder());
   
      return authProvider;
  }
  
  @Bean
  public OpenAPI defineOpenApi() {
      Server server = new Server();
      server.setUrl("http://localhost:8081");
      server.setDescription("Development");

      Contact myContact = new Contact();
      myContact.setName("Dipen Limbu");
      myContact.setEmail("dipenlimboo564@gmail.com");

      Info information = new Info()
              .title("Human Resources Management System API")
              .version("1.0")
              .description("This API exposes endpoints to manage Human Resources.")
              .contact(myContact);
      return new OpenAPI().info(information).servers(List.of(server));
  }
 
  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
    return authConfig.getAuthenticationManager();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
  
   
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf(csrf -> csrf.disable())
        .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(auth -> 
        		auth
        		.requestMatchers("/swagger-ui.index","/v3/api-docs/**").permitAll()
        		.requestMatchers("/swagger-ui/**").permitAll()
        		.requestMatchers("/Document/**").permitAll()
                .requestMatchers("/Images/**").permitAll()
                .requestMatchers("/api/users/**").permitAll()
                .requestMatchers("/api/create/**").permitAll()
                .requestMatchers("/api/superAdmin/**").permitAll()
                .requestMatchers("/api/questionset/**").permitAll()
                .requestMatchers("/api/forgot-password/**").permitAll()
                .requestMatchers("/api/answer/**").permitAll()
                .requestMatchers("/h2-ui/**").permitAll()
        		.anyRequest().authenticated()
        		);
    
    
    http.authenticationProvider(authenticationProvider());

    http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    
    return http.build();
  }
  
 
}
