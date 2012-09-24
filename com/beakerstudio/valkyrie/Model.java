package com.beakerstudio.valkyrie;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;

import com.almworks.sqlite4java.SQLiteException;
import com.almworks.sqlite4java.SQLiteStatement;
import com.beakerstudio.valkyrie.sql.Column;
import com.beakerstudio.valkyrie.sql.CreateTable;
import com.beakerstudio.valkyrie.sql.IntegerColumn;
import com.beakerstudio.valkyrie.sql.Select;
import com.beakerstudio.valkyrie.sql.TextColumn;

/**
 * Model Class
 * @author Evan Byrne
 */
public abstract class Model {

	/**
	 * Columns
	 */
	protected static LinkedHashMap<Class<?>, LinkedHashMap<String, Column>> columns = new LinkedHashMap<Class<?>, LinkedHashMap<String, Column>>();
	
	/**
	 * Column
	 * @param Class<?>
	 * @param Column
	 * @return this
	 */
	public static void add_column(Class<?> klass, Column column) {
		
		columns.get(klass).put(column.get_name(), column);
		
	}
	
	/**
	 * Get Column
	 * @param Class<?>
	 * @param String Column name
	 * @return Column
	 */
	public static Column get_column(Class<?> klass, String name) {
		
		return columns.get(klass).get(name);
		
	}
	
	/**
	 * Constructor
	 */
	public Model() {
		
		// Populate columns once
		Class<?> klass = this.getClass();
		if(!columns.containsKey(klass)) {
			
			columns.put(klass, new LinkedHashMap<String, Column>());
			for(Field f : klass.getDeclaredFields()) {
				
				if(f.isAnnotationPresent(com.beakerstudio.valkyrie.Column.class)) {
					
					String t = f.getType().getSimpleName();
					
					// Integer
					if(t.equals("Integer")) {
						add_column(klass, new IntegerColumn(f.getName()));
						
					// String
					} else if(t.equals("String")) {
						
						add_column(klass, new TextColumn(f.getName()));
						
					}
					
				}
				
			}
			
		}
		
	}
	
	/**
	 * SQLite Table
	 * @return String
	 */
	public String sqlite_table() {
		
		return getClass().getSimpleName().toLowerCase();
		
	}
	
	/**
	 * Create Table
	 * @return this
	 * @throws SQLiteException
	 * @throws Exception
	 */
	public Model create_table() throws SQLiteException, Exception {
		
		CreateTable t = new CreateTable(this.sqlite_table());
		Class<?> klass = this.getClass();
		for(String col_name : columns.get(klass).keySet()) {
			
			t.add_column(get_column(klass, col_name));
			
		}
		
		Connection.get().execute(t.build().sql());
		
		return this;
		
	}
	
	/**
	 * Get
	 * @return this
	 * @throws Exception 
	 * @throws SQLiteException 
	 */
	public Model get() throws SQLiteException, Exception {
		
		Select s = new Select(this.sqlite_table());
		s.limit(1);
		
		// Build where
		Class<?> klass = this.getClass();
		for(String col : columns.get(klass).keySet()) {
			
			try {
				
				Object value = klass.getDeclaredField(col).get(this);
				if(value != null) {
					
					s.where(col, value.toString());
					
				}
				
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		// Fetch row
		s.build();
		SQLiteStatement st = Connection.get().prepare(s.sql(), s.params());
		
		try {
			
			while(st.step()) {
				
				// Populate model object
				for(int i = 0; i < st.columnCount(); i++) {
					
					String col_name = st.getColumnName(i);
					String col_type = get_column(klass, col_name).getClass().getSimpleName();
					Field col_field = klass.getField(col_name);
					
					// Integer
					if(col_type.equals("IntegerColumn")) {
						
						col_field.set(this, new Integer(st.columnInt(i)));
						
					// Text
					} else if(col_type.equals("TextColumn")) {
						
						col_field.set(this, st.columnString(i));
						
					}
					
				}
		
			}
			
		} finally {
			
			st.dispose();
		
		}
		
		return this;
		
	}
	
}
