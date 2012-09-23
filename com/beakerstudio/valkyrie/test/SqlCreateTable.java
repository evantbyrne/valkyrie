package com.beakerstudio.valkyrie.test;

import static org.junit.Assert.*;
import org.junit.Test;
import com.beakerstudio.valkyrie.sql.CreateTable;
import com.beakerstudio.valkyrie.sql.IntegerColumn;
import com.beakerstudio.valkyrie.sql.TextColumn;

/**
 * SQL Create Table Tests
 * @author Evan Byrne
 */
public class SqlCreateTable {

	/**
	 * Test Create Table
	 */
	@Test
	public void test_create_table() {
		
		CreateTable t = new CreateTable("foo");
		assertEquals("CREATE TABLE \"foo\" (\n\n);", t.build().sql());
		
		t.add_column(new IntegerColumn("id"));
		assertEquals("CREATE TABLE \"foo\" (\n\"id\" INTEGER\n);", t.build().sql());
		
		t.add_column(new TextColumn("name"));
		assertEquals("CREATE TABLE \"foo\" (\n\"id\" INTEGER,\n\"name\" TEXT\n);", t.build().sql());
		
	}

}
