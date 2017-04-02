package com.html;

import org.apache.commons.validator.routines.UrlValidator;

public class Helper {
	
	public static String getValidURI(String uri){
		uri = uri.toLowerCase();
		if(!uri.startsWith("http://") && !uri.startsWith("https://")){
			uri = "http://"+uri;
		}
		return uri;
	}
	
	public static boolean isValidURI(String uri){
		String[] schemes = {"http","https"};
	    UrlValidator urlValidator = new UrlValidator(schemes);
	    if (urlValidator.isValid(uri)) {
	    	return true;
	    }
	    return false;
	}
}