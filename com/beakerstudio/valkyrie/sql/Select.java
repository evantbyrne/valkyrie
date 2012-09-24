package com.beakerstudio.valkyrie.sql;

import java.util.Vector;
import com.beakerstudio.valkyrie.Util;

/**
 * Select Class
 * @author Evan Byrne
 */
public class Select extends Where {

	/**
	 * Limit
	 */
	protected Integer limit;
	
	/**
	 * {@inheritDoc}
	 */
	public Select(String table) {
		
		super(table);
		this.limit = null;
		
	}
	
	/**
	 * Limit
	 * @param int
	 * @return this
	 */
	public Select limit(int n) {
		
		this.limit = n;
		return this;
		
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
		
		// Limit
		if(this.limit != null) {
			
			sql.add("LIMIT ?");
			this.add_param(this.limit.toString());
			
		}
		
		this.set_sql(Util.join(sql.toArray(), " "));
		return this;
		
	}
	
}
