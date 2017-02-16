/**
 * 
 */
package com.tek.interview.question;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.io.PrintStream;
import java.io.ByteArrayOutputStream;

/**
 * @author stratiotes
 *
 */
public class testFoo {
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

	public testFoo() {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUpStreams() throws Exception {
	    System.setOut(new PrintStream(outContent));
	    System.setErr(new PrintStream(errContent));
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void cleanUpStreams() throws Exception {
	    System.setOut(null);
	    System.setErr(null);
	}

	/**
	 * Simple test of the Foo main method
	 * @throws java.lang.Exception
	 */

	@Test
	public void testFooMain() {
		String expected = "*******Order 1*******\n1 book: 13.74\n1 music CD: 16.49\n1 chocolate bar: 0.94\nSales Tax: 2.84\nTotal: 28.33\n";
		expected += "*******Order 2*******\n1 imported box of chocolate: 11.5\n1 imported bottle of perfume: 54.62\nSales Tax: 8.62\nTotal: 57.5\n";
		expected += "*******Order 3*******\n1 Imported bottle of perfume: 32.19\n1 bottle of perfume: 20.89\n1 packet of headache pills: 10.73\n1 box of imported chocolates: 12.94\nSales Tax: 8.77\nTotal: 67.98\nSum of orders: 153.81\n";
		try {
			Foo.main(null);
		} catch (Exception e) {
			fail("Foo.main threw and exception");
			e.printStackTrace();
		}
		assert(expected.compareTo(outContent.toString()) == 0);
	}

}
