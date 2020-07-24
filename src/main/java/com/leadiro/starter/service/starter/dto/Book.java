package com.leadiro.starter.service.starter.dto;

import lombok.Data;

@Data
public class Book {
	
	private String id;
	private String title;
	private String displayTitle;

	public String getTitle() {
		
		return displayTitle;
	}

}
