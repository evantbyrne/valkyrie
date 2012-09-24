package com.beakerstudio.valkyrie.test;

import static org.junit.Assert.*;
import org.junit.Test;
import com.beakerstudio.valkyrie.sql.DropTable;

/**
 * SQL Drop Table Tests
 * @author Evan Byrne
 */
public class SqlDropTable {

	/**
	 * Test Drop Table
	 */
	@Test
	public void test_drop_table() {
		
		DropTable t = new DropTable("foo");
		assertEquals("DROP TABLE \"foo\";", t.build().sql());
		
	}

}
