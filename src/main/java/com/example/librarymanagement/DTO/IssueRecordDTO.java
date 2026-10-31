package com.example.librarymanagement.DTO;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IssueRecordDTO {

	private LocalDate issueDate;
	private LocalDate dueDate;
	private LocalDate returnDate;
	private Boolean isReturned;
	
}
