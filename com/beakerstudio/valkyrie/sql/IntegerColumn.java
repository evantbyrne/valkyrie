package com.beakerstudio.valkyrie.sql;

/**
 * Integer Column Class
 * @author Evan Byrne
 */
public class IntegerColumn extends Column {
	
	/**
	 * @inheritdoc
	 */
	public IntegerColumn(String name) {
		
		super(name);
		
	}

	/**
	 * @inheritdoc
	 */
	public String build() {
		
		return String.format("`%s` INTEGER", this.get_name());
		
	}

}
