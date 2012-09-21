package com.beakerstudio.valkyrie;

import java.util.LinkedHashMap;
import com.beakerstudio.valkyrie.sql.Column;

/**
 * Model Class
 * @author Evan Byrne
 */
public abstract class Model {

	/**
	 * Columns
	 */
	protected LinkedHashMap<String, Column> columns;
	
	/**
	 * Constructor
	 */
	public Model() {
		
		this.columns = new LinkedHashMap<String, Column>();
		
	}
	
	/**
	 * SQLite Table
	 * @return String
	 */
	public String sqlite_table() {
		
		return getClass().getSimpleName().toLowerCase();
		
	}
	
	/**
	 * Column
	 * @param Column
	 * @return this
	 */
	public Model column(Column column) {
		
		this.columns.put(column.get_name(), column);
		return this;
		
	}
	
	/**
	 * Get Column
	 * @param String Column name
	 * @return Column
	 */
	public Column get_column(String name) {
		
		return this.columns.get(name);
		
	}
	
}
