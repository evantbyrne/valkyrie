package com.beakerstudio.valkyrie.sql;

import java.util.Vector;

import com.beakerstudio.valkyrie.Util;

/**
 * Create Table Class
 * @author Evan Byrne
 */
public class CreateTable extends Base {

	/**
	 * List of columns
	 */
	private Vector<Column> columns;
	
	/**
	 * @inheritDoc
	 */
	public CreateTable(String sqlite_table) {
		
		super(sqlite_table);
		this.columns = new Vector<Column>();
		
	}
	
	/**
	 * Add Column
	 * @param Column
	 * @return this
	 */
	public CreateTable add_column(Column col) {
		
		this.columns.add(col);
		return this;
		
	}

	/**
	 * @inheritDoc
	 */
	@Override
	public CreateTable build() {
		
		Vector<String> sql = new Vector<String>();
		for(Column col : this.columns) {
			
			sql.add(col.build());
			
		}
		
		this.set_sql(String.format("CREATE TABLE \"%s\" (\n%s\n);", this.table, Util.join(sql.toArray(), ",\n")));
		return this;
		
	}

	
}
