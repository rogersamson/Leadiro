package com.leadiro.starter.service;

import com.leadiro.starter.service.starter.dto.Email;
import com.leadiro.starter.service.starter.dto.PostCode;

public interface ValidateService {

	Email validateEmail(String email) ;

	PostCode validatePostCode(String postcode) ;

}
