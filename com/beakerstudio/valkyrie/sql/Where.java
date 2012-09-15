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
	 * Constructor
	 */
	public Where(String table) {
		
		super(table);
		this.where_list = new Vector<HashMap<String, String>>();
		
	}
	
	/**
	 * 
	 * @param String Column
	 * @param String Value
	 * @return this
	 */
	public Where where(String column, String value) {
		
		HashMap<String, String> w = new HashMap<String, String>();
		w.put("type", "where_equal");
		w.put("column", column);
		w.put("value", value);
		this.where_list.add(w);
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
				
				// =
				if(type.equals("where_equal")) {
					
					// AND
					if(previous != null && !previous.get("type").equals("or")) {
					
						sql.add("AND");
						
					}
						
					sql.add(String.format("`%s`.`%s` = ?", this.table, w.get("column")));
					this.add_param(w.get("value"));
					
				// OR
				} else if(type.equals("or")) {
					
					sql.add("OR");
					
				}
				
				previous = w;
				
			}
		
			return Util.join(sql.toArray(), " ");
		
		}
		
		return null;
		
	}

}
