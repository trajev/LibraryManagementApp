package com.example.librarymanagement.service;

import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.librarymanagement.DTO.BookDTO;
import com.example.librarymanagement.entity.Book;
import com.example.librarymanagement.repository.BookRepository;

@Service
public class BookService {
	
	@Autowired
	private BookRepository bookRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	public List<Book> getAllBooks(  ) {
		return bookRepo.findAll();
	}
	
	public Book getBookById( UUID bookId ) {
		Book book = bookRepo.findById(bookId).orElseThrow( ()->new RuntimeException("Book not found") );
		return book;
	}
	
	public Book addBook( BookDTO bookDto ) {
		Book book = modelMapper.map( bookDto , Book.class );
		return bookRepo.save(book);
	}
	
	public Book updateBook( UUID bookId, BookDTO bookDto ) {
		Book existingBook = bookRepo.findById(bookId).orElseThrow( ()->new RuntimeException("Book not found") );
		modelMapper.map( bookDto, existingBook );
		return bookRepo.save(existingBook);
	}
	
	public void deleteBook( UUID bookId ) {
		bookRepo.deleteById(bookId);
	}

}
