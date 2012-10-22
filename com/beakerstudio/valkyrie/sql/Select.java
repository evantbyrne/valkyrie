package com.beakerstudio.valkyrie.sql;

import java.lang.reflect.Field;
import java.util.Vector;

import com.almworks.sqlite4java.SQLiteException;
import com.almworks.sqlite4java.SQLiteStatement;
import com.beakerstudio.valkyrie.Connection;
import com.beakerstudio.valkyrie.ForeignKey;
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
	 * Offset
	 */
	protected Integer offset;
	
	/**
	 * Order List
	 */
	protected Vector<String[]> order_list;
	
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
		this.order_list = new Vector<String[]>();
		this.limit = null;
		this.offset = null;
		
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
	 * Offset
	 * @param int
	 * @return this
	 */
	public Select offset(int n) {
		
		this.offset = n;
		return this;
		
	}
	
	/**
	 * Order Ascending
	 * @param String Column
	 * @return this
	 */
	public Select order_asc(String column) {
		
		this.order_list.add(new String[] {"ASC", column});
		return this;
		
	}
	
	/**
	 * Order Descending
	 * @param String Column
	 * @return this
	 */
	public Select order_desc(String column) {
		
		this.order_list.add(new String[] {"DESC", column});
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
		
		// Order
		if(this.order_list.size() > 0) {
			
			sql.add("ORDER BY");
			Vector<String> order_sql = new Vector<String>();
			
			for(String[] o : this.order_list) {
				
				order_sql.add(String.format("\"%s\" %s", o[1], o[0]));
				
			}
			
			sql.add(Util.join(order_sql.toArray(), ", "));
			
		}
		
		// Limit
		if(this.limit != null) {
			
			sql.add("LIMIT ?");
			this.add_param(this.limit.toString());
			
			// Offset
			if(this.offset != null) {
				
				sql.add("OFFSET ?");
				this.add_param(this.offset.toString());
				
			}
			
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
	public <T, T2> Vector<T> fetch() throws SQLiteException, Exception {
		
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
						
						// Foreign Key
						if(col_field.getType().getSimpleName().equals("ForeignKey")) {
							
							// Get model class instance for foreign key
							String fk_type_name = col_field.getAnnotation(com.beakerstudio.valkyrie.Column.class).type();
							Model fk_model = (Model) Class.forName(fk_type_name).newInstance();
							fk_model.set_pk(new Integer(st.columnInt(i)));
							
							// Create foreign key instance
							ForeignKey<?> fk = (ForeignKey<?>) col_field.getType().newInstance();
							fk.set(fk_model);
							col_field.set(m, fk);
						
						// Regular Integer Columns
						} else {
						
							col_field.set(m, new Integer(st.columnInt(i)));
							
						}
						
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
