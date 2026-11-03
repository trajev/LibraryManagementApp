package com.example.librarymanagement.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {

	@NotBlank(message="Book title is required")
	private String title;
	
	@NotBlank(message="Book author is required")
	private String author;
	
	@NotBlank(message="Book ISBN is required")
	private String isbn;
	
	@NotNull(message="Book quantity is required")
	@Min(value=0, message="Book quantity cannot be negative" )
	private Integer quantity;
	
	@NotNull(message = "Book availability status is required")
	private Boolean isAvailable;
	
}
