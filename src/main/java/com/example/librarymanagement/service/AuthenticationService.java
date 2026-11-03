package com.example.librarymanagement.service;

import java.util.HashSet;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.librarymanagement.DTO.LoginRequestDTO;
import com.example.librarymanagement.DTO.LoginResponseDTO;
import com.example.librarymanagement.DTO.RegisterRequestDTO;
import com.example.librarymanagement.config.JWTService;
import com.example.librarymanagement.entity.User;
import com.example.librarymanagement.repository.UserRepository;

@Service
public class AuthenticationService {

	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private JWTService jwtService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AuthenticationManager authenticationManager;

	public User registerNormalUser(RegisterRequestDTO registerRequestDto) {

		if (userRepo.findByUsername(registerRequestDto.getUsername()).isPresent()) {
			throw new RuntimeException("User already registered");
		}

		Set<String> roles = new HashSet<String>();
		roles.add("ROLE_USER");

		User user = new User();
		user.setUsername(registerRequestDto.getUsername());
		user.setEmail(registerRequestDto.getEmail());
		user.setPassword(passwordEncoder.encode(registerRequestDto.getPassword()));
		user.setRoles(roles);

		return userRepo.save(user);
	}

	public User registerAdminUser(RegisterRequestDTO registerRequestDto) {

		if (userRepo.findByUsername(registerRequestDto.getUsername()).isPresent()) {
			throw new RuntimeException("User already registered");
		}

		Set<String> roles = new HashSet<String>();
		roles.add("ROLE_ADMIN");
		roles.add("ROLE_USER");

		User user = new User();
		user.setUsername(registerRequestDto.getUsername());
		user.setEmail(registerRequestDto.getEmail());
		user.setPassword(passwordEncoder.encode(registerRequestDto.getPassword()));
		user.setRoles(roles);

		return userRepo.save(user);
	}
	
	public LoginResponseDTO login( LoginRequestDTO loginRequestDto ) {
		authenticationManager.authenticate( 
					new UsernamePasswordAuthenticationToken( loginRequestDto.getUsername() , loginRequestDto.getPassword() )
				);
		
		User user = userRepo.findByUsername( loginRequestDto.getUsername() ).orElseThrow( ()->new RuntimeException("User not found") );

		String token = jwtService.generateToken(user);
		
		return LoginResponseDTO.builder()
				.token(token)
				.username(user.getUsername())
				.roles( user.getRoles() )
				.build();
		
	}

}
