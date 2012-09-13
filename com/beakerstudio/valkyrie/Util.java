package com.beakerstudio.valkyrie;

/**
 * Util Class
 * @author Evan Byrne
 */
public class Util {
	
	/**
	 * Join
	 * @param Object[] List of strings
	 * @param String Separator
	 * @return String
	 */
	public static String join(Object[] list, String separator) {
		
		if(list.length == 0) {
			
			return "";
			
		}
		
		String result = "";
		for(int i = 0; i < list.length; i++) {
			
			if(i != 0) {
				
				result += separator;
				
			}
			
			result += list[i].toString();
			
		}
		
		return result;
		
	}

}
