package com.beakerstudio.valkyrie.test;

import static org.junit.Assert.*;
import org.junit.Test;

public class Util {

	/**
	 * Test Join
	 */
	@Test
	public void test_join() {
		
		String[] x = {"foo"};
		assertEquals("foo", com.beakerstudio.valkyrie.Util.join(x, ""));
		assertEquals("foo", com.beakerstudio.valkyrie.Util.join(x, ", "));
		
		String[] y = {"foo", "bar", "baz"};
		assertEquals("foobarbaz", com.beakerstudio.valkyrie.Util.join(y, ""));
		assertEquals("foo bar baz", com.beakerstudio.valkyrie.Util.join(y, " "));
		assertEquals("foo, bar, baz", com.beakerstudio.valkyrie.Util.join(y, ", "));
		
	}

}
