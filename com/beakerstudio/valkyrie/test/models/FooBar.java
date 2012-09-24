package com.beakerstudio.valkyrie.test.models;

import com.beakerstudio.valkyrie.Column;
import com.beakerstudio.valkyrie.Model;

/**
 * Foo Bar Test Model
 * @author Evan Byrne
 */
public class FooBar extends Model {
	
	@Column
	public Integer id;
	
	@Column
	public String name;
	
}
