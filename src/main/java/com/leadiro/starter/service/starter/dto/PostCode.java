package com.leadiro.starter.service.starter.dto;

import lombok.Data;

@Data
public class PostCode {
	private String postcode;
	private String region;

	public PostCode(String region, String postcode) {
		this.region = cleanCharacter(region);
		this.postcode = cleanCharacter(postcode);
	}
	private String cleanCharacter(String input) {
		return input.replace("\"", "");
	}
}
