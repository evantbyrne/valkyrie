package com.beakerstudio.valkyrie.sql;

import java.lang.reflect.Field;
import java.util.Vector;

import com.almworks.sqlite4java.SQLiteException;
import com.almworks.sqlite4java.SQLiteStatement;
import com.beakerstudio.valkyrie.Connection;
import com.beakerstudio.valkyrie.Model;
import com.beakerstudio.valkyrie.Util;

/**
 * Select Class
 * @author Evan Byrne
 */
public class Select extends Where {

	/**
	 * Class
	 */
	protected Class<?> klass;
	
	/**
	 * Limit
	 */
	protected Integer limit;
	
	/**
	 * {@inheritDoc}
	 */
	public Select(String table) {
		
		this(table, null);
		
	}
	
	/**
	 * Select
	 * @param String Table
	 * @param Class<?> Class
	 */
	public Select(String table, Class<?> klass) {
		
		super(table);
		this.klass = klass;
		this.limit = null;
		
	}
	
	/**
	 * {@inheritDoc}
	 */
	public Select eql(String column, String value) {
		
		super.eql(column, value);
		return this;
		
	}
	
	/**
	 * {@inheritDoc}
	 */
	public Select not(String column, String value) {
		
		super.not(column, value);
		return this;
		
	}
	
	/**
	 * {@inheritDoc}
	 */
	public Select lt(String column, String value) {
		
		super.lt(column, value);
		return this;
		
	}
	
	/**
	 * {@inheritDoc}
	 */
	public Select lte(String column, String value) {
		
		super.lte(column, value);
		return this;
		
	}
	
	/**
	 * {@inheritDoc}
	 */
	public Select gt(String column, String value) {
		
		super.gt(column, value);
		return this;
		
	}
	
	/**
	 * {@inheritDoc}
	 */
	public Select gte(String column, String value) {
		
		super.gte(column, value);
		return this;
		
	}
	
	/**
	 * {@inheritDoc}
	 */
	public Select or() {
		
		super.or();
		return this;
		
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
	
	/**
	 * Fetch
	 * @return Vector<Object>
	 * @throws Exception 
	 * @throws SQLiteException 
	 */
	public <T> Vector<T> fetch() throws SQLiteException, Exception {
		
		this.build();
		SQLiteStatement st = Connection.get().prepare(this.sql(), this.params());
		Vector<T> res = new Vector<T>();
		
		try {
			
			while(st.step()) {
				
				T m = (T) this.klass.newInstance(); // TODO: Fix warning
				
				// Populate model object
				for(int i = 0; i < st.columnCount(); i++) {
					
					String col_name = st.getColumnName(i);
					String col_type = Model.get_column(this.klass, col_name).getClass().getSimpleName();
					Field col_field = m.getClass().getField(col_name);
					
					// Integer
					if(col_type.equals("IntegerColumn")) {
						
						col_field.set(m, new Integer(st.columnInt(i)));
						
					// Text
					} else if(col_type.equals("TextColumn")) {
						
						col_field.set(m, st.columnString(i));
						
					}
					
				}
				
				res.add(m);
		
			}
			
		} finally {
			
			st.dispose();
		
		}
		
		return res;
		
	}
	
}
