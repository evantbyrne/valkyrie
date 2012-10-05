package com.beakerstudio.valkyrie.sql;

import java.util.Vector;
import com.beakerstudio.valkyrie.Util;

/**
 * Update Class
 * @author Evan Byrne
 */
public class Update extends Where {

	/**
	 * Columns
	 */
	protected Vector<String> columns;
	
	/**
	 * Values
	 */
	protected Vector<String> values;
	
	/**
	 * {@inheritDoc}
	 */
	public Update(String table) {
		
		super(table);
		this.columns = new Vector<String>();
		this.values = new Vector<String>();
		
	}
	
	/**
	 * Set
	 * @param String Column
	 * @param String Value
	 * @return this
	 */
	public Update set(String column, String value) {
		
		this.columns.add(column);
		this.values.add(value);
		return this;
		
	}
	
	/**
	 * Build
	 * @return this
	 */
	public Update build() {
		
		this.reset_params();
		Vector<String> sql = new Vector<String>();
		sql.add(String.format("UPDATE \"%s\"", this.table));
		
		// Columns and values
		Vector<String> val_sql = new Vector<String>();
		for(int i = 0; i < this.columns.size(); i++) {
			
			val_sql.add(String.format("\"%s\" = ?", this.columns.get(i)));
			this.add_param(this.values.get(i));
			
		}
	
		sql.add(String.format("SET %s", Util.join(val_sql.toArray(), ", ")));
		
		// Where
		String where = this.sql_for_where();
		if(where != null) {
		
			sql.add(where);
		
		}
		
		this.set_sql(Util.join(sql.toArray(), " "));
		return this;
		
	}
	
}

