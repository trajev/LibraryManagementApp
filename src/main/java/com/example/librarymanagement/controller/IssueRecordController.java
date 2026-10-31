package com.example.librarymanagement.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.librarymanagement.entity.IssueRecord;
import com.example.librarymanagement.service.IssueRecordService;

@RestController
@RequestMapping("/issuerecords")
public class IssueRecordController {

	@Autowired
	private IssueRecordService issueRecordService;
	
	@PostMapping("/issuebook/{bookId}")
	public ResponseEntity<IssueRecord> issueBook( @PathVariable UUID bookId ) {
		return ResponseEntity.status(HttpStatus.CREATED).body( issueRecordService.issueBook(bookId) );
	}
	
	@PostMapping("/returnbook/{issueRecordId}")
	public ResponseEntity<IssueRecord> returnBook( @PathVariable UUID issueRecordId ) {
		return ResponseEntity.status(HttpStatus.CREATED).body( issueRecordService.returnBook(issueRecordId) );
	}

	
}
