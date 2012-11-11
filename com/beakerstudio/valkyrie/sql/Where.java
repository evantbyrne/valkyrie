package com.beakerstudio.valkyrie.sql;

import java.util.HashMap;
import java.util.Vector;
import com.beakerstudio.valkyrie.Util;

/**
 * Abstract Where Class
 * @author Evan Byrne
 */
public abstract class Where extends Base {
	
	/**
	 * List of where clauses
	 */
	protected Vector<HashMap<String, String>> where_list;
	
	/**
	 * {@inheritDoc}
	 */
	public Where(String table) {
		
		super(table);
		this.where_list = new Vector<HashMap<String, String>>();
		
	}
	
	/**
	 * Add Where
	 * @param String Type EX: =, !=, <, <=, or, ...
	 * @param String Column
	 * @param String Value
	 */
	protected void add_where(String type, String column, String value) {
		
		HashMap<String, String> w = new HashMap<String, String>();
		w.put("type", type);
		w.put("column", column);
		w.put("value", value);
		this.where_list.add(w);
		
	}
	
	/**
	 * Equal
	 * @param String Column
	 * @param String Value
	 * @return this
	 */
	public Where eql(String column, String value) {
		
		this.add_where("=", column, value);
		return this;
		
	}
	
	/**
	 * Not Equal
	 * @param String Column
	 * @param String Value
	 * @return this
	 */
	public Where not(String column, String value) {
		
		this.add_where("<>", column, value);
		return this;
		
	}
	
	/**
	 * Less Than
	 * @param String Column
	 * @param String Value
	 * @return this
	 */
	public Where lt(String column, String value) {
		
		this.add_where("<", column, value);
		return this;
		
	}
	
	/**
	 * Less Than or Equal
	 * @param String Column
	 * @param String Value
	 * @return this
	 */
	public Where lte(String column, String value) {
		
		this.add_where("<=", column, value);
		return this;
		
	}
	
	/**
	 * Greater Than
	 * @param String Column
	 * @param String Value
	 * @return this
	 */
	public Where gt(String column, String value) {
		
		this.add_where(">", column, value);
		return this;
		
	}
	
	/**
	 * Greater Than or Equal
	 * @param String Column
	 * @param String Value
	 * @return this
	 */
	public Where gte(String column, String value) {
		
		this.add_where(">=", column, value);
		return this;
		
	}
	
	/**
	 * Or
	 * @return this
	 */
	public Where or() {
		
		HashMap<String, String> w = new HashMap<String, String>();
		w.put("type", "or");
		this.where_list.add(w);
		return this;
		
	}
	
	/**
	 * Sql For Where
	 * @return String Where portion of SQL, or null
	 */
	public String sql_for_where() {
		
		if(!this.where_list.isEmpty()) {
		
			Vector<String> sql = new Vector<String>();
			sql.add("WHERE");
			
			HashMap<String, String> previous = null;
			for(HashMap<String, String> w : this.where_list) {
				
				String type = w.get("type");
				
				// OR
				if(type.equals("or")) {
					
					sql.add("OR");
					
				} else {
					
					// AND
					if(previous != null && !previous.get("type").equals("or")) {
					
						sql.add("AND");
						
					}
					
					// Format column name
					String column = w.get("column");
					String table = this.table;
					
					if(column.indexOf('.') != -1) {
						
						String halves[] = column.split("\\.");
						table = halves[0];
						column = halves[1];
						
					}
					
					sql.add(String.format("\"%s\".\"%s\" %s ?", table, column, type));
					this.add_param(w.get("value"));
					
				}
				
				previous = w;
				
			}
		
			return Util.join(sql.toArray(), " ");
		
		}
		
		return null;
		
	}

}
