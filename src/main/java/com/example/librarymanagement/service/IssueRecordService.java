package com.example.librarymanagement.service;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.librarymanagement.entity.Book;
import com.example.librarymanagement.entity.IssueRecord;
import com.example.librarymanagement.entity.User;
import com.example.librarymanagement.repository.BookRepository;
import com.example.librarymanagement.repository.IssueRecordRepository;
import com.example.librarymanagement.repository.UserRepository;

@Service
public class IssueRecordService {
	
	@Autowired
	private IssueRecordRepository issueRecordRepo;
	
	@Autowired
	private BookRepository bookRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	
	
	public IssueRecord issueBook( UUID bookId ) {
		Book existingBook = bookRepo.findById(bookId).orElseThrow( ()->new RuntimeException("Book not found") );
		
		if( existingBook.getQuantity()<=0 || !existingBook.getIsAvailable() ) {
			throw new RuntimeException( "Book not available" );
		}
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		User existingUser = userRepo.findByUsername(username).orElseThrow( ()->new RuntimeException("User not found") );
		
		IssueRecord issueRecord = new IssueRecord();
		issueRecord.setIssueDate(LocalDate.now());
		issueRecord.setDueDate(LocalDate.now().plusDays(14));
		issueRecord.setIsReturned(false);
		issueRecord.setUser(existingUser);
		issueRecord.setBook(existingBook);
		
		existingBook.setQuantity( existingBook.getQuantity()-1 );
		if( existingBook.getQuantity()==0 ) {
			existingBook.setIsAvailable(false);
		}
		
		bookRepo.save(existingBook);
		
		return issueRecordRepo.save(issueRecord);		
	}
	
	
	
	public IssueRecord returnBook( UUID issueRecordId ) {
		
		IssueRecord issueRecord = issueRecordRepo.findById(issueRecordId).orElseThrow( ()->new RuntimeException("Issue Record not found") );
		
		if( issueRecord.getIsReturned() ) {
			throw new RuntimeException("Book is already returned");
		}
		
		Book book = issueRecord.getBook();
		book.setQuantity( book.getQuantity()+1 );
		book.setIsAvailable(true);
		bookRepo.save(book);
		
		issueRecord.setReturnDate(LocalDate.now());
		issueRecord.setIsReturned(true);
		
		return issueRecordRepo.save(issueRecord);
	}

}
