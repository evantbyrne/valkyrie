package com.beakerstudio.valkyrie.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.beakerstudio.valkyrie.sql.Insert;

/**
 * SQL Insert Tests
 * @author Evan Byrne
 */
public class SqlInsert {

	/**
	 * Test No Columns
	 */
	@Test
	public void test_no_columns() {
		
		Insert i = new Insert("foo");
		assertEquals("INSERT INTO \"foo\"", i.build().sql());
		
	}
	
	/**
	 * Test Basic
	 */
	@Test
	public void test_basic() {
		
		Insert i = new Insert("foo");
		i.set("name", "Evan Byrne");
		assertEquals("INSERT INTO \"foo\" (\"name\") VALUES (?)", i.build().sql());
		assertTrue(i.params().indexOf("Evan Byrne") == 0);
		
		i.set("awesome", "123");
		assertEquals("INSERT INTO \"foo\" (\"name\", \"awesome\") VALUES (?, ?)", i.build().sql());
		assertTrue(i.params().indexOf("Evan Byrne") == 0);
		assertTrue(i.params().indexOf("123") == 1);
		
	}

}
