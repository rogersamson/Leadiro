package com.leadiro.starter.utility;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class NameBuilder {

	private final String[] suffix= {"RAMBO","ED","DR","CSAR","THE","TERMINATOR","BSC","CERTIFIED","PROFESSIONAL","DIP","DIPED","MSC","MPH","PHD","MACA","ASSOC","PROF"};	

	public String[] process(String name) {
		log.debug("Parsing input name  [{}]",name);
		String first=null;
		String last=null;
		String regex1 = "^[a-zA-Z|,|'|-]*$";
		String regex2 = "[\\.$|,|;|'|~|\"|'|(|)|.|-]";
		String [] fullName=null;
		String[] names = name.split("\\s+");
		
		boolean reverse = false;
		for(int i=0;i<names.length;i++) {
			if(names[i].matches(regex1) ) {
				log.debug("Matching [{}] with acceptable characters [{}]", names[i] , regex1);
				if(names[i].contains(",") && first==null) {
					log.debug("Check if [{}] is a last name", names[i] );
					reverse = true;
				}
				String o = names[i].trim().replaceAll(regex2,"") ;
				log.debug("Clean [{}]  by removing characters [{}] results [{}]", names[i], regex2, o );
				List<String> list = Arrays.asList(suffix);
				if(!list.contains(o.toUpperCase()) && o.length()>2 ) {
					log.debug("Check if clean string [{}] is from list of suffixes [{}]", o, (Object) suffix );
					if(i==0 || first==null) {							
							first = convertName(o);	
					}else {
						if(last==null) {
							last = convertName(o);
						}						
					}
				}
			}
		}
		if(first!=null && last!=null) {
			if(reverse) {
				fullName = new String[]{last,first};
				log.debug("Composing name [{}] with last name at the beginning",  fullName );
			}else {
				fullName = new String[]{first,last};
				log.debug("Composing name [{}] with last name at the end",  fullName );
			}
			log.debug("Parsing input name  [{}] results [{}]",name, fullName);
		}
		return fullName;
	}
	
	private String convertName(String inputString) {
		log.debug("Converting name [{}] to Proper Case",inputString );
       
        if (inputString.length() == 1) {
            return inputString.toUpperCase();
        }
 
        StringBuffer resultPlaceHolder = new StringBuffer(inputString.length());
 
        Stream.of(inputString.split(" ")).forEach(stringPart -> 
        {
            if (stringPart.length() > 1)
                resultPlaceHolder.append(stringPart.substring(0, 1)
                                    .toUpperCase())
                                    .append(stringPart.substring(1)
                                    .toLowerCase());
            else
                resultPlaceHolder.append(stringPart.toUpperCase());
 
            resultPlaceHolder.append(" ");
        });
        log.debug("Converting name [{}] results [{}]",inputString, resultPlaceHolder.toString().trim());
        return resultPlaceHolder.toString().trim() ;
    }
}
