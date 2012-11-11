package com.beakerstudio.valkyrie.test.models;

import com.beakerstudio.valkyrie.Column;
import com.beakerstudio.valkyrie.ManyToMany;
import com.beakerstudio.valkyrie.Model;

public class User extends Model {
	
	@Column(primary=true)
	public Integer id;
	
	@Column
	public String first_name;
	
	@Column
	public String last_name;
	
	@Column(type="com.beakerstudio.valkyrie.test.models.Group", middleman="com.beakerstudio.valkyrie.test.models.Membership")
	public ManyToMany<Group> groups;

}
