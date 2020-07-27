package com.leadiro.starter.service.starter;

import java.util.Arrays;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leadiro.starter.exceptions.RecordNotFoundException;
import com.leadiro.starter.service.BookService;
import com.leadiro.starter.service.starter.dto.Book;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ConcreteBookService implements BookService {
	
	private final String baseUri = "https://collections.museumsvictoria.com.au/api/";
	
	@Override
	public Book[] searchByKeywords(String[] keyword) throws Exception  {
		log.debug("Search books by keyword [{}]", (Object) keyword);
		Book[] books;
		
			ObjectMapper mapper = new ObjectMapper()
					.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			RestTemplate restTemplate = new RestTemplate();
			
			HttpHeaders headers = new HttpHeaders();
		
			UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(baseUri + "search");
			for(int i=0;i<keyword.length;i++) {
				builder.queryParam("query", keyword[i]);
			}			
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			headers.add("user-agent", userAgent);
			HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
			
			ResponseEntity<String> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET,entity, String.class);
			books = mapper.readValue(response.getBody().toString() , Book[].class);
			if(books.length>0) {
				log.debug("Search books by keyword [{}] results [{}]",  (Object) keyword, "Record Found");
			}else {
				log.debug("Search books by keyword [{}] results [{}]",  (Object) keyword, "Record Not Found");
				throw new RecordNotFoundException("Record Not Found");
			}
		
		return books;
	}
	
	@Override
	public Book searchByID(String type, String id) {
		log.debug("Search books by id [{}]", type+"/"+id);
		Book book = null;
		try {
			ObjectMapper mapper = new ObjectMapper()
				.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			RestTemplate restTemplate = new RestTemplate();
				
			HttpHeaders headers = new HttpHeaders();
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			headers.add("user-agent",userAgent);
			HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
				
			ResponseEntity<String> response = restTemplate.exchange(baseUri + type + "/" + id, HttpMethod.GET,entity,String.class);
			book = mapper.readValue(response.getBody().toString() , Book.class);
			log.debug("Search books by id [{}]", type+"/"+id,"Record Found");
		} catch (Exception e) {
			log.debug("Search books by id [{}]", type+"/"+id,"Record Not Found");
			throw new RecordNotFoundException("Record Not Found");
		}
			
		return book;
	}

}
