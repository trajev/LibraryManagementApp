package com.example.librarymanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.librarymanagement.DTO.LoginRequestDTO;
import com.example.librarymanagement.DTO.LoginResponseDTO;
import com.example.librarymanagement.DTO.RegisterRequestDTO;
import com.example.librarymanagement.entity.User;
import com.example.librarymanagement.service.AuthenticationService;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	private AuthenticationService authService;

	@PostMapping("/registernormaluser")
	public ResponseEntity<User> registerNormalUser( @RequestBody RegisterRequestDTO registerRequestDto) {
		return ResponseEntity.status(HttpStatus.CREATED).body(authService.registerNormalUser(registerRequestDto));
	}
	
	@PostMapping("/login")
	public ResponseEntity<LoginResponseDTO> loginUser( @RequestBody LoginRequestDTO loginRequestDto ) {
		return ResponseEntity.status(HttpStatus.CREATED).body( authService.login(loginRequestDto) );
	}
	
	
	
	
}
