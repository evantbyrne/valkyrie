package com.beakerstudio.valkyrie.sql;

import java.util.Vector;

/**
 * Select Class
 * @author Evan Byrne
 */
public class Select {

	/**
	 * Constructor
	 * @param String Table
	 */
	public Select(String table) {
		
		// ...
		
	}
	
	/**
	 * 
	 * @param String Column
	 * @param String Value
	 * @return this
	 */
	public Select where(String column, String value) {
		
		return this;
		
	}
	
	/**
	 * Or
	 * @return this
	 */
	public Select or() {
		
		return this;
		
	}
	
	/**
	 * Sql
	 * @return String
	 */
	public String sql() {
		
		return "";
		
	}
	
	/**
	 * Values
	 * @return Vector<String>
	 */
	public Vector<String> values() {
		
		return new Vector<String>();
		
	}
	
}
