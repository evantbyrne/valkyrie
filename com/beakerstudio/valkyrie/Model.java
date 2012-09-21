package com.beakerstudio.valkyrie;

/**
 * Model Class
 * @author Evan Byrne
 */
public class Model {

	/**
	 * SQLite Table
	 * @return String
	 */
	public String sqlite_table() {
		
		return getClass().getSimpleName().toLowerCase();
		
	}
	
}
