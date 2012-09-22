package com.beakerstudio.valkyrie.sql;

import java.util.Vector;
import com.beakerstudio.valkyrie.Util;

/**
 * Select Class
 * @author Evan Byrne
 */
public class Select extends Where {

	/**
	 * Constructor
	 * @param String Table
	 */
	public Select(String table) {
		
		super(table);
		
	}
	
	/**
	 * Build
	 * @return this
	 */
	public Select build() {
		
		this.reset_params();
		Vector<String> sql = new Vector<String>();
		sql.add(String.format("SELECT * FROM \"%s\"", this.table));
		
		// Where
		String where = this.sql_for_where();
		if(where != null) {
		
			sql.add(where);
		
		}
		
		this.set_sql(Util.join(sql.toArray(), " "));
		return this;
		
	}
	
}
