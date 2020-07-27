package com.leadiro.starter.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.leadiro.starter.service.starter.dto.Book;
import com.leadiro.starter.service.starter.dto.Email;
import com.leadiro.starter.service.starter.dto.Name;
import com.leadiro.starter.service.starter.dto.PostCode;

public class MuseumControllerTest extends AbstractTest {
	@Override
	@Before
	public void setUp() {super.setUp();}
	
	@Test
	public void testSearchByKeywords_ValidKeyword() throws Exception {			
		String uri = "/museum";
		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.get(uri).
				param("keyword","icon").
				param("keyword","india").
				accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		Book[] books = super.mapFromJson(content, Book[].class);
		assertNotNull(books);		
	}

	@Test
	public void testSearchByKeywords_InvalidKeyword() throws Exception {
		String uri = "/museum";
		mvc.perform(
				MockMvcRequestBuilders.get(uri).
				param("keyword","rogersamson").
				accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isNotFound());
	}

	@Test
	public void testSearchByID_ValidId() throws Exception {
		String uri = "/museum/items/81186";
		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.get(uri).				
				accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		Book books = super.mapFromJson(content, Book.class);
		assertNotNull(books);
	}

	@Test
	public void testSearchByID_InvalidId() throws Exception {
		String uri = "/museum/items/81182";
		mvc.perform(
				MockMvcRequestBuilders.get(uri).				
				accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isNotFound() );
	}
	
	@Test
	public void testValidatePostCode_ValidPostCode() throws Exception {
		String uri = "/validate/postcode";
		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.get(uri).
				param("postcode", "EC2Y9DT").
				accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		PostCode postCode = super.mapFromJson(content, PostCode.class);
		assertNotNull(postCode);
		assertEquals("EC2Y9DT : London", "London", postCode.getRegion());
	}
	
	@Test
	public void testValidatePostCode_InvalidPostCode() throws Exception {
		String uri = "/validate/postcode";
		mvc.perform(
				MockMvcRequestBuilders.get(uri).
				param("postcode", "LEAD IRO").
				accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isNotFound() );	
	}
	
	@Test
	public void testValidateEmail_ValidEmail() throws Exception {
		String uri = "/validate/email";
		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.get(uri).
				param("email","rogelio.samson@gmail.com").
				accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		Email email = super.mapFromJson(content, Email.class);
		assertNotNull(email);
		assertEquals("NAME : Roger Samson", "rogelio.samson@gmail.com", email.getEmail() );
		assertEquals("NAME : Roger Samson is Valid", true ,email.isValid());
	}
	
	@Test
	public void testValidateEmail_InvalidEmail() throws Exception {
		String uri = "/validate/email";
		mvc.perform(
				MockMvcRequestBuilders.get(uri).
				param("email","rogelio.samson@gmail").
				accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isBadRequest() );
	}
	
	@Test
	public void testProcessName_ValidName() throws Exception {
		String uri = "/parse/name";
		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.get(uri).
				param("name","Roger Samson").
				accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		Name name = super.mapFromJson(content, Name.class);
		assertNotNull(name);
		assertEquals("NAME : Roger Samson", "Roger", name.getFirst());
		assertEquals("NAME : Roger Samson", "Samson", name.getLast());
	}
	
	@Test
	public void testProcessName_InvalidName() throws Exception {
		String uri = "/parse/name";
		mvc.perform(
				MockMvcRequestBuilders.get(uri).
				param("name","Roger -").
				accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isBadRequest() );
	}
}
