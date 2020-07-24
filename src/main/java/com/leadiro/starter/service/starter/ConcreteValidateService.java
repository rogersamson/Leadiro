package com.leadiro.starter.service.starter;

import java.util.Arrays;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leadiro.starter.exceptions.InvalidException;
import com.leadiro.starter.exceptions.RecordNotFoundException;
import com.leadiro.starter.service.ValidateService;
import com.leadiro.starter.service.starter.dto.Email;
import com.leadiro.starter.service.starter.dto.PostCode;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ConcreteValidateService implements ValidateService {

	@Override
	public Email validateEmail(String email)  {
		log.debug("Validating email [{}]", email);
		String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
		boolean isValidEmail = email.matches(regex);
		if (!isValidEmail) {
			log.debug("Validating email [{}] results [{}].", email, "Invalid Email");
			throw new InvalidException("Invalid Email");
		}
		Email validEmail = new Email();
		validEmail.setEmail(email);
		validEmail.setValid(isValidEmail);
		log.debug("Validating email [{}] results [{}].", email, "Valid Email");
		return validEmail;
	}
	@Override
	public PostCode validatePostCode(String postcode)  {
		log.debug("Validating Postal Code [{}].", postcode, "Invalid Email");
		PostCode postCode=null;		
		try {
			String uri = "http://api.postcodes.io/postcodes/" + postcode;
			ObjectMapper mapper = new ObjectMapper()
					.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			RestTemplate restTemplate = new RestTemplate();				
			HttpHeaders headers = new HttpHeaders();
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);	
			
			ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET,entity,String.class);
			JsonNode region;
			region = mapper.readTree(response.getBody()).findValue("region");
			JsonNode postalCode = mapper.readTree(response.getBody()).findValue("postcode") ;
			postCode = new PostCode(region.toString(), postalCode.toString() );	
			log.debug("Validating Postal Code [{}] results [{}].", postcode, "Valid Code");
		} catch (Exception e) {
			log.debug("Validating Postal Code [{}] results [{}].", postcode, "Invalid Code");
			throw new RecordNotFoundException("Postal Code Not Found");
		}
		return postCode ;
	}

}
