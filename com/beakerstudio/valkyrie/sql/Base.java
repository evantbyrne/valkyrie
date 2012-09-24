package com.beakerstudio.valkyrie.sql;

import java.util.Vector;

/**
 * Abstract Base Class
 * @author Evan Byrne
 */
public abstract class Base {
	
	/**
	 * SQLite table name
	 */
	protected String table;
	
	/**
	 * Values to be bound to prepared statement
	 */
	protected Vector<String> params;
	
	/**
	 * SQL for prepared statement, minus values that are to be bound to it
	 */
	protected String sql;
	
	/**
	 * Constructor
	 * @param String SQLite table name
	 */
	public Base(String sqlite_table) {
		
		this.table = sqlite_table;
		this.params = new Vector<String>();
		this.sql = "";
		
	}
	
	/**
	 * Reset Params
	 * @return this
	 */
	public Base reset_params() {
		
		this.params = new Vector<String>();
		return this;
		
	}
	
	/**
	 * Add Param
	 * @param String
	 * @return this
	 */
	public Base add_param(String value) {
		
		this.params.add(value);
		return this;
		
	}
	
	/**
	 * Params
	 * @return Vector<String>
	 */
	public Vector<String> params() {
		
		return this.params;
		
	}
	
	/**
	 * Set SQL
	 * @param String
	 * @return this
	 */
	public Base set_sql(String value) {
		
		this.sql = value;
		return this;
		
	}
	
	/**
	 * SQL
	 * @return String
	 */
	public String sql() {
		
		return this.sql;
		
	}
	
	/**
	 * Build
	 * @return Base
	 */
	public abstract Base build();

}
