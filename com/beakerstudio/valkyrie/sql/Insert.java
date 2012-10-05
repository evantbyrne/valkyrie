package com.beakerstudio.valkyrie.sql;

import java.util.Vector;
import com.beakerstudio.valkyrie.Util;

/**
 * Insert Class
 * @author Evan Byrne
 */
public class Insert extends Base {

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
	public Insert(String table) {
		
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
	public Insert set(String column, String value) {
		
		this.columns.add(column);
		this.values.add(value);
		return this;
		
	}
	
	/**
	 * Build
	 * @return this
	 */
	public Insert build() {
		
		this.reset_params();
		Vector<String> sql = new Vector<String>();
		sql.add(String.format("INSERT INTO \"%s\"", this.table));
		
		// Columns and values
		if(this.columns.size() >= 1) {
		
			Vector<String> col_sql = new Vector<String>();
			Vector<String> val_sql = new Vector<String>();
			for(int i = 0; i < this.columns.size(); i++) {
				
				col_sql.add(String.format("\"%s\"", this.columns.get(i)));
				val_sql.add("?");
				this.add_param(this.values.get(i));
				
			}
		
			sql.add(String.format("(%s)", Util.join(col_sql.toArray(), ", ")));
			sql.add(String.format("VALUES (%s)", Util.join(val_sql.toArray(), ", ")));
			this.set_sql(Util.join(sql.toArray(), " "));
			
		} else {
			
			this.set_sql(sql.get(0));
			
		}
		
		return this;
		
	}
	
}

