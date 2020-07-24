package com.leadiro.starter.service.starter;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.leadiro.starter.exceptions.InvalidException;
import com.leadiro.starter.exceptions.RecordNotFoundException;
import com.leadiro.starter.service.ValidateService;
import com.leadiro.starter.service.starter.dto.Email;
import com.leadiro.starter.service.starter.dto.PostCode;

@SpringBootTest
public class ValidateServiceTest {
	
	@Autowired
	private ValidateService validateService;

	@Test
	public void testValidateEmail_ValidEmail() {
		Email expectedEmail = new Email();
		expectedEmail.setEmail("rogelio.samson@gmail.com");
		expectedEmail.setValid(true);

		Email resultEmail = validateService.validateEmail("rogelio.samson@gmail.com");
		Assert.assertNotNull(resultEmail);
		Assert.assertEquals(expectedEmail, resultEmail);
	}

	@Test
	public void testValidateEmail_InvalidEmail() {
		InvalidException exception = Assert.assertThrows(InvalidException.class, () -> {
			validateService.validateEmail("rogelio.samson@gmail");
		});
		Assert.assertEquals("Invalid Email", exception.getMessage());
	}

	@Test
	public void validatePostCode_ValidPostCode() {
		PostCode expectedPostCode = new PostCode("London", "EC2Y 9DT");
		PostCode resultPostCode = validateService.validatePostCode("EC2Y9DT");
		Assert.assertNotNull(resultPostCode);
		Assert.assertEquals(expectedPostCode, resultPostCode);
	}

	@Test
	public void validatePostCode_InvalidPostCode() {
		RecordNotFoundException exception = Assert.assertThrows(RecordNotFoundException.class, () -> {
			validateService.validatePostCode("LEAD IRO");
		});
		Assert.assertEquals("Postal Code Not Found", exception.getMessage());
	}

}
