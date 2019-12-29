package com.vineela.classregistrationsystem.controller;

import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;

import com.vineela.classregistrationsystem.repository.RoleRepository;
import com.vineela.classregistrationsystem.repository.UserRepository;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vineela.classregistrationsystem.message.request.LoginRequest;
import com.vineela.classregistrationsystem.message.request.SignUpRequest;
import com.vineela.classregistrationsystem.message.response.JwtResponse;
import com.vineela.classregistrationsystem.model.Role;
import com.vineela.classregistrationsystem.model.RoleName;
import com.vineela.classregistrationsystem.model.User;
import com.vineela.classregistrationsystem.security.jwt.JwtProvider;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
@Api(tags="Authentication", value="Class Registration System", description="Operations pertaining to authentication into Class Registration System")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtProvider jwtProvider;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateJwtToken(authentication);
        return ResponseEntity.ok(new JwtResponse(jwt));
    }

    @PostMapping("/signup")
    public ResponseEntity<String> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        if(userRepository.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity<>("Fail -> Username is already taken!",
                    HttpStatus.BAD_REQUEST);
        }

        if(userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity<>("Fail -> Email is already in use!",
                    HttpStatus.BAD_REQUEST);
        }

        // Creating user's account
        User user = new User(signUpRequest.getName(), signUpRequest.getUsername(),
                signUpRequest.getEmail(), encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        strRoles.forEach(role -> {
        	switch(role) {
	    		case "admin":
	    			Role adminRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
	                .orElseThrow(() -> new RuntimeException("Fail! -> Cause: Admin Role not find."));
	    			roles.add(adminRole);
	    			
	    			break;
	    		case "professor":
	            	Role professorRole = roleRepository.findByName(RoleName.ROLE_PROFESSOR)
	                .orElseThrow(() -> new RuntimeException("Fail! -> Cause: Professor Role not find."));
	            	roles.add(professorRole);
	            	
	    			break;
	    		default:
	        		Role studentRole = roleRepository.findByName(RoleName.ROLE_STUDENT)
	                .orElseThrow(() -> new RuntimeException("Fail! -> Cause: Student Role not find."));
	        		roles.add(studentRole);
        	}
        });
        
        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok().body("User registered successfully!");
    }
}