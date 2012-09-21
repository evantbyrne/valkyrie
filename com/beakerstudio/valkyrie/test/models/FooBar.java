package com.beakerstudio.valkyrie.test.models;

import com.beakerstudio.valkyrie.Model;
import com.beakerstudio.valkyrie.sql.IntegerColumn;
import com.beakerstudio.valkyrie.sql.TextColumn;

/**
 * Foo Bar Test Model
 * @author Evan Byrne
 */
public class FooBar extends Model {
	
	/**
	 * Constructor
	 */
	public FooBar() {
		
		super();
		this.column(new IntegerColumn("id"));
		this.column(new TextColumn("name"));
		
	}
	
}
