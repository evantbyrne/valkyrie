package com.beakerstudio.valkyrie.test;

import static org.junit.Assert.*;
import org.junit.Test;
import com.beakerstudio.valkyrie.sql.Select;

/**
 * SQL Select Tests
 * @author Evan Byrne
 */
public class SqlSelect {

	/**
	 * Test All
	 */
	@Test
	public void test_all() {
		
		Select s = new Select("mytable");
		assertEquals("SELECT * FROM \"mytable\"", s.build().sql());
		
	}
	
	/**
	 * Test Where
	 */
	@Test
	public void test_where() {
		
		Select s = new Select("mytable");
		s.where("id", "123");
		assertEquals("SELECT * FROM \"mytable\" WHERE \"mytable\".\"id\" = ?", s.build().sql());
		assertTrue(s.params().indexOf("123") == 0);
		
		// AND
		s.where("name", "Evan");
		assertEquals("SELECT * FROM \"mytable\" WHERE \"mytable\".\"id\" = ? AND \"mytable\".\"name\" = ?", s.build().sql());
		assertTrue(s.params().indexOf("123") == 0);
		assertTrue(s.params().indexOf("Evan") == 1);
		
		// OR
		s = new Select("mytable");
		s.where("id", "321").or().where("name", "Byrne");
		assertEquals("SELECT * FROM \"mytable\" WHERE \"mytable\".\"id\" = ? OR \"mytable\".\"name\" = ?", s.build().sql());
		assertTrue(s.params().indexOf("321") == 0);
		assertTrue(s.params().indexOf("Byrne") == 1);
		
	}

}
