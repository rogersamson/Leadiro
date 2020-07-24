package com.leadiro.starter.service.starter;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.leadiro.starter.exceptions.RecordNotFoundException;
import com.leadiro.starter.service.BookService;
import com.leadiro.starter.service.starter.dto.Book;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class BookServiceTest {
	
	@Autowired
	private BookService bookService;

	@Test
	public void testSearchByKeywords_ValidKeyword() {
		Book[] resultBook = bookService.searchByKeywords(new String[] { "india", "coin" });
		Assert.assertNotNull(resultBook);
		log.info("RESULTS : [{}]", (Object) resultBook);
	}

	@Test
	public void testSearchByKeywords_InvalidKeyword() {
		RecordNotFoundException exception = Assert.assertThrows(RecordNotFoundException.class, () -> {
			bookService.searchByKeywords(new String[] { "india", "coin" });
		});
		Assert.assertEquals("Record Not Found", exception.getMessage());
	}

	@Test
	public void testSearchByID_ValidId() {
		Book resultBook = bookService.searchByID("items", "1368212");
		Assert.assertNotNull(resultBook);
		log.info("RESULTS : [{}]", (Object) resultBook);
	}

	@Test
	public void testSearchByID_InvalidId() {
		RecordNotFoundException exception = Assert.assertThrows(RecordNotFoundException.class, () -> {
			bookService.searchByID("items", "136821");
		});
		Assert.assertEquals("Record Not Found", exception.getMessage());
	}

}
