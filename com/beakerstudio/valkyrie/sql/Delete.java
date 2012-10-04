package com.beakerstudio.valkyrie.sql;

import java.util.Vector;
import com.beakerstudio.valkyrie.Util;

/**
 * Delete Class
 * @author Evan Byrne
 */
public class Delete extends Where {
	
	/**
	 * {@inheritDoc}
	 */
	public Delete(String table) {
		
		super(table);
		
	}
	
	/**
	 * Build
	 * @return this
	 */
	public Delete build() {
		
		this.reset_params();
		Vector<String> sql = new Vector<String>();
		sql.add(String.format("DELETE FROM \"%s\"", this.table));
		
		// Where
		String where = this.sql_for_where();
		if(where != null) {
		
			sql.add(where);
		
		}
		
		this.set_sql(Util.join(sql.toArray(), " "));
		return this;
		
	}
	
}