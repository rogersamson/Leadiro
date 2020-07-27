package com.leadiro.starter.service;

import com.leadiro.starter.service.starter.dto.Book;

public interface BookService {
	
	static final String userAgent ="Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36";

	Book[] searchByKeywords(String[] keyword) throws Exception;

	Book searchByID(String type, String id) throws Exception;
	
}
