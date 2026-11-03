package com.example.librarymanagement.config;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.librarymanagement.repository.UserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JWTAuthenticationFilter extends OncePerRequestFilter{

	@Autowired
	private final JWTService jwtService;
	
	@Autowired
	private final UserRepository userRepo;

	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		final String authHeader = request.getHeader("Authorization");
		final String jwtToken;
		final String username;
		
		// check if authorization header is present and starts with "Bearer"
		if( authHeader==null || authHeader.startsWith("BEARER ") ) {
			filterChain.doFilter(request, response);
			return;
		}
		
		//Extract JWT token from header
		jwtToken = authHeader.substring(7);
		username = jwtService.extractUsername(jwtToken);
		
		// check if we have a username and no authentication exists yet
		if( username!=null && SecurityContextHolder.getContext().getAuthentication()==null ) {
		
			//get the user details from database
			var userDetails = userRepo.findByUsername(username).orElseThrow( ()->new RuntimeException("User not found") );
			
			// validate the token
			if( jwtService.isTokenValid(jwtToken, userDetails) ) {
				
				// create the authentication with user roles
				List<SimpleGrantedAuthority> authorites = userDetails.getRoles().stream().map( SimpleGrantedAuthority::new ).collect( Collectors.toList() );
				
				// set authentication details
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,null, authorites);
				authToken.setDetails( new WebAuthenticationDetailsSource().buildDetails(request) );
				
				// update security context with authentication
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
			
			
		}
		
		filterChain.doFilter(request, response);		
		
	}	
	
	
	
}
