package com.beakerstudio.valkyrie.test;

import static org.junit.Assert.*;
import org.junit.Test;

public class Model {

	/**
	 * Test SQLite Table
	 */
	@Test
	public void test_sqlite_table() {
		
		com.beakerstudio.valkyrie.Model m = new com.beakerstudio.valkyrie.Model();
		assertEquals("model", m.sqlite_table());
		
		com.beakerstudio.valkyrie.test.models.Foo f = new com.beakerstudio.valkyrie.test.models.Foo();
		assertEquals("foo", f.sqlite_table());
		
		com.beakerstudio.valkyrie.test.models.FooBar fb = new com.beakerstudio.valkyrie.test.models.FooBar();
		assertEquals("foobar", fb.sqlite_table());
		
	}

}
