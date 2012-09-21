package com.beakerstudio.valkyrie.sql;

/**
 * Column Class
 * @author Evan Byrne
 */
public abstract class Column {
	
	/**
	 * Column name
	 */
	protected String name;
	
	/**
	 * Construct
	 * @param String Column name
	 */
	public Column(String name) {
		
		this.name = name;
		
	}
	
	/**
	 * Get Name
	 * @return String
	 */
	public String get_name() {
		
		return this.name;
		
	}
	
	/**
	 * Build
	 * @return String
	 */
	public abstract String build();

}
