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
import java.util.Map;
import java.util.TreeMap;

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
	 * Test the Item class
	 */
	@Test
	public void testItem() {
		// domestic item
		Item item = new Item("sample", 1.00);
		assertEquals(item.getDescription(), "sample");
		assertEquals(item.getPrice().toString(), "1");
		
		TaxCalculator tcalc = item.getTaxCalculator();
		OrderMoney tax = tcalc.computeTax(item.getPrice());
		
		assertEquals(tax.toString(), "0.1");
		
		// import item
		item = new Item("Imported sample", 1.00);
		tcalc = item.getTaxCalculator();
		tax = tcalc.computeTax(item.getPrice());
		
		assertEquals(tax.toString(), "0.15");
		
		// Special case that would fail with the initial 
		// version of code that did not ignore desc string case in
		// determining tax
		
		item = new Item("Caps IMPORTED test", 1.00);
		tcalc = item.getTaxCalculator();
		tax = tcalc.computeTax(item.getPrice());
		
		assertEquals(tax.toString(), "0.15");
	}
	
	/**
	 * Test the Order class
	 */
	@Test
	public void testOrder() {
		Order order = new Order("Testing Order");
		
		assertEquals(order.getName(), "Testing Order");
		
		assertEquals(order.size(), 0);
		
		Item item = new Item("Nine", 9.99);
		OrderLine orderline = null;
		try {
			orderline = new OrderLine(item, 2);
			
		} catch (Exception e) {
			fail("An exception was thrown in OrderLine creation");
			e.printStackTrace();
		}
		
		order.add(orderline);
		assertEquals(order.size(), 1);
		
		assertNotNull(order.get(0));
		
		order.clear();
		assertEquals(order.size(), 0);
	}
	
	/**
	 * Test of the XML OrderUnpacker
	 */
	@Test
	public void testOrderUnpacker() {
		Map<String, Order> OrdersMap = new TreeMap<String, Order>();
		
		OrderUnpacker orderUnpacker = new OrderUnpacker();
		try {
			orderUnpacker.unpackXML("resources/testingSet2.xml", OrdersMap);
		} catch (Exception e) {
			fail("The unpacker threw an exception parsing the test set file.");
			e.printStackTrace();
		}
		
		// check the second (of two) orderline of the second order
		assertTrue(OrdersMap.size() == 2);
		Order order = OrdersMap.get("Two Imports");
		
		assertNotNull(order);
		
		assertTrue(order.size() == 2);
		
		OrderLine oline = order.get(1);
		assertNotNull(oline);
		assertNotNull(oline.getItem());
		assertEquals(oline.getQuantity(), 2);
		
		Item item = oline.getItem();
		
		assertEquals(item.getDescription(), "Item imported of Two");
		
		// check the second (of three) orderline of the first order
		order = OrdersMap.get("One");
		assertNotNull(order);
		
		assertTrue(order.size() == 3);
		
		oline = order.get(1);
		assertNotNull(oline);
		assertNotNull(oline.getItem());
		assertEquals(oline.getQuantity(), 2);
		
		item = oline.getItem();
		OrderMoney price = item.getPrice();
		assertNotNull(price);
		
		assertEquals(price.toString(), "2.22");
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
			Foo.main(new String[] {"resources/testingSet1.xml"});
		} catch (Exception e) {
			fail("Foo.main threw an exception");
			e.printStackTrace();
		}
		assertTrue(expected.compareTo(outContent.toString()) == 0);
	}
			
}
