package com.example.librarymanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.librarymanagement.DTO.RegisterRequestDTO;
import com.example.librarymanagement.entity.User;
import com.example.librarymanagement.service.AuthenticationService;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
	
	@Autowired
	private AuthenticationService authService;
	
	
	@PostMapping("/registeradminuser")
	public ResponseEntity<User> registerAdminUser( @RequestBody RegisterRequestDTO registerRequestDto ) {
		return ResponseEntity.ok( authService.registerAdminUser(registerRequestDto) );
	}

	
}
