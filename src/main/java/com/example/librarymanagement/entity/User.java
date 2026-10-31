package com.example.librarymanagement.entity;

import java.util.Set;
import java.util.UUID;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;


@Entity
@Table(name="users")
@Data
public class User {
	
	@Id
	@GeneratedValue(strategy=GenerationType.UUID)
	private UUID id;
	private String username;
	private String email;
	private String password;
	
	@ElementCollection(fetch=FetchType.EAGER)
	private Set<String> roles;

}
