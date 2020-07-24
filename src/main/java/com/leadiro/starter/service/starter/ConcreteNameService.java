package com.leadiro.starter.service.starter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leadiro.starter.exceptions.InvalidException;
import com.leadiro.starter.service.NameService;
import com.leadiro.starter.service.starter.dto.Name;
import com.leadiro.starter.utility.NameBuilder;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ConcreteNameService implements NameService {
	
	@Autowired
	private NameBuilder nameBuilder;

	@Override
	public Name process(String name)  {	
		log.debug("Parsing name  [{}]", name);
		String[] fullName =	nameBuilder.process(name);
		if(fullName==null) {
			log.debug("Parsing input name  [{}] results [{}]",name, "Unable to Process Name");
			throw new InvalidException("Unable to Process Name");
		}else {
			Name convertedName = new Name(fullName[0], fullName[1]);
			log.debug("Parsing input name  [{}] results [{}]",name, (Object) convertedName);
			return convertedName;	
		}
	}
	


}
