package com.beakerstudio.valkyrie.test;

import static org.junit.Assert.*;
import org.junit.Test;
import com.beakerstudio.valkyrie.sql.Select;

public class SqlSelect {

	/**
	 * Test All
	 */
	@Test
	public void test_all() {
		
		Select s = new Select("mytable");
		assertEquals(s.sql(), "SELECT * FROM `mytable`");
		
	}
	
	/**
	 * Test Where
	 */
	@Test
	public void test_where() {
		
		Select s = new Select("mytable");
		s.where("id", "123");
		assertEquals(s.sql(), "SELECT * FROM `mytable` WHERE `mytable`.`id` = ?");
		assertTrue(s.values().indexOf("123") == 0);
		
		// AND
		s.where("name", "Evan");
		assertEquals(s.sql(), "SELECT * FROM `mytable` WHERE `mytable`.`id` = ? AND `mytable`.`name` = ?");
		assertTrue(s.values().indexOf("123") == 0);
		assertTrue(s.values().indexOf("Evan") == 1);
		
		// OR
		s = new Select("mytable");
		s.where("id", "321").or().where("name", "Byrne");
		assertEquals(s.sql(), "SELECT * FROM `mytable` WHERE `mytable`.`id` = ? OR `mytable`.`name` = ?");
		assertTrue(s.values().indexOf("321") == 0);
		assertTrue(s.values().indexOf("Byrne") == 1);
		
	}

}
