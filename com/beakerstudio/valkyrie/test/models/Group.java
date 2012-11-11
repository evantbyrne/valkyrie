package com.beakerstudio.valkyrie.test.models;

import com.beakerstudio.valkyrie.Column;
import com.beakerstudio.valkyrie.ManyToMany;
import com.beakerstudio.valkyrie.Model;

public class Group extends Model {
	
	@Column(primary=true)
	public Integer id;
	
	@Column
	public String name;
	
	@Column(type="com.beakerstudio.valkyrie.test.models.User", middleman="com.beakerstudio.valkyrie.test.models.Membership")
	public ManyToMany<User> users;

}
