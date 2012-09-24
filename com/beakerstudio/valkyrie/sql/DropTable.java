package com.beakerstudio.valkyrie.sql;

public class DropTable extends Base {

	/**
	 * {@inheritDoc}
	 */
	public DropTable(String sqlite_table) {
		
		super(sqlite_table);
		
	}
	
	/**
	 * {@inheritDoc}
	 */
	public DropTable build() {
		
		this.set_sql(String.format("DROP TABLE \"%s\";", this.table));
		return this;
		
	}

}
