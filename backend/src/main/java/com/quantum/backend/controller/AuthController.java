package com.quantum.backend.controller;

import java.util.List;
import java.util.stream.Collectors;

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

import com.quantum.backend.auth.jwt.JwtUtils;
import com.quantum.backend.auth.payload.JwtResponse;
import com.quantum.backend.auth.payload.LoginRequest;
import com.quantum.backend.auth.service.UserDetailsImpl;
import com.quantum.backend.global.CommonFunction;
import com.quantum.backend.model.RoleType;
import com.quantum.backend.repository.UserRepository;

@RestController
@CrossOrigin
@RequestMapping(path="api/auth")
public class AuthController {
    @Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	@PostMapping("login")
    public ResponseEntity<JwtResponse> authenticateUser(@RequestBody LoginRequest loginRequest){
		
		CommonFunction.printlnTime("authenticating: " + loginRequest.getEmail() + " & " + loginRequest.getPassword());
		
        Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();		
		String userTypeStr = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.findFirst()
				.get();

		RoleType userType = RoleType.valueOf(userTypeStr);
		
		JwtResponse response = new JwtResponse(jwt, userDetails.getUserId(), userDetails.getUsername(), userDetails.getEmail(), userType);
		return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
