package com.leadiro.starter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.leadiro.starter.service.starter.ConcreteBookService;
import com.leadiro.starter.service.starter.ConcreteNameService;
import com.leadiro.starter.service.starter.ConcreteValidateService;
import com.leadiro.starter.service.starter.dto.Book;
import com.leadiro.starter.service.starter.dto.Email;
import com.leadiro.starter.service.starter.dto.Name;
import com.leadiro.starter.service.starter.dto.PostCode;

@RestController()
public class MuseumController {
	
	@Autowired
	private ConcreteNameService nameService;	
	
	@Autowired
	private ConcreteValidateService validateService;
	
	@Autowired
	private ConcreteBookService bookService;
	
	@GetMapping(value={"/museum/{type}/{id}"} )
	private Book searchByID(@PathVariable String type, @PathVariable String id) throws Exception{
		
		return bookService.searchByID(type, id);
	}	
	
	@GetMapping("/museum")
	private Book[] searchKeyWord(String[] keyword) throws Exception{
		return bookService.searchByKeywords(keyword);
	
	}	
	
	@GetMapping("/validate/email") 
	 public Email validateEmail(@RequestParam String email)  throws Exception {
		return  validateService.validateEmail(email);
	 }	
	
	@GetMapping("/parse/name")
	public Name parseName(String name) throws Exception{	   
		return nameService.process(name);
	}
	    
	 @GetMapping("/validate/postcode")
	  public PostCode validatePostCode(@RequestParam String postcode) throws Exception {
		  return validateService.validatePostCode(postcode);
	 }

}
