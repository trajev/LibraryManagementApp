package com.example.librarymanagement.controller;


import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.librarymanagement.DTO.BookDTO;
import com.example.librarymanagement.entity.Book;
import com.example.librarymanagement.service.BookService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/books")
public class BookController {
	
	@Autowired
	private BookService bookService;
	
	@GetMapping("/")
	public ResponseEntity<List<Book>> getAllBooks(){
		return ResponseEntity.ok(bookService.getAllBooks());
	}
	
	@GetMapping("/{bookId}")
	public ResponseEntity<Book> getBookById( @PathVariable UUID bookId ){
		return ResponseEntity.ok( bookService.getBookById(bookId) );
	}
	
	@PostMapping("/")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Book> addBook( @Valid @RequestBody BookDTO book ){
		return ResponseEntity.status(HttpStatus.CREATED).body( bookService.addBook(book) );
	}
	
	
	@PutMapping("/{bookId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Book> updateBook( @PathVariable UUID bookId, @Valid @RequestBody BookDTO book  ){
		return ResponseEntity.ok( bookService.updateBook(bookId, book) );
	}

	
	@DeleteMapping("/{bookId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Void> deleteBook( @PathVariable UUID bookId ){
		bookService.deleteBook( bookId );
		return ResponseEntity.noContent().build();
	}
	
	
	
}
