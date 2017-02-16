package com.tek.interview.question;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/* ****************************************************************************************
 
Please remove all bugs from the code below to get the following output:

<pre>

*******Order 1*******
1 book: 13.74
1 music CD: 16.49
1 chocolate bar: 0.94
Sales Tax: 2.84
Total: 28.33
*******Order 2*******
1 imported box of chocolate: 11.5
1 imported bottle of perfume: 54.62
Sales Tax: 8.62
Total: 57.5
*******Order 3*******
1 Imported bottle of perfume: 32.19
1 bottle of perfume: 20.89
1 packet of headache pills: 10.73
1 box of imported chocolates: 12.94
Sales Tax: 8.77
Total: 67.98
Sum of orders: 153.81
 
</pre>
 
******************************************************************************************** */

/*
 * represents an item, contains a price and a description.
 *
 */
class Item {
	// Class members
	static final BigDecimal IMPORT_TAX = new BigDecimal("0.15");
	static final BigDecimal DOMESTIC_TAX = new BigDecimal("0.10");

	static final Currency USD = Currency.getInstance("USD");
	static final RoundingMode DEFAULT_ROUNDING = RoundingMode.HALF_EVEN;

	// Class init
	{
		// set the scale for each since legal requirements could require each be different
		IMPORT_TAX.setScale(Item.USD.getDefaultFractionDigits(), Item.DEFAULT_ROUNDING);
		DOMESTIC_TAX.setScale(Item.USD.getDefaultFractionDigits(), Item.DEFAULT_ROUNDING);
	}

	// Object members
	private String description;
	private BigDecimal price;
	
	public Item(String description, double price) {
		super();
		this.description = description;
		this.price = new BigDecimal(Double.toString(price));
		this.price.setScale(Item.USD.getDefaultFractionDigits(), Item.DEFAULT_ROUNDING);
	}

	public String getDescription() {
		return description;
	}

	public BigDecimal getPrice() {
		return price;
	}
	
	public BigDecimal computeTax() {
		if(getDescription().contains("Import") || getDescription().contains("import")) {
			return (getPrice().multiply(IMPORT_TAX)).setScale(Item.USD.getDefaultFractionDigits(), Item.DEFAULT_ROUNDING);
		}
		else {
			return (getPrice().multiply(DOMESTIC_TAX).setScale(Item.USD.getDefaultFractionDigits(), RoundingMode.CEILING));
		}
	}
	
	@Override
	public String toString() {
		return getPrice().toString();
	}
}

/*
 * represents an order line which contains the @link Item and the quantity.
 *
 */
class OrderLine {

	private int quantity;
	private Item item; 

	/*
	 * @param item Item of the order
	 * 
	 * @param quantity Quantity of the item
	 */
	public OrderLine(Item item, int quantity) throws Exception {
		if (item == null) {
			System.err.println("ERROR - Item is NULL");
			throw new Exception("Item is NULL");
		}
		assert quantity > 0;
		this.item = item;
		this.quantity = quantity;
	}

	public Item getItem() {
		return item;
	}

	public int getQuantity() {
		return quantity;
	}
}

/*
 * represents an order which wraps a list of @link OrderLine objects.
 *
 */
class Order {

	private List<OrderLine> orderLines;

	public void add(OrderLine o) throws IllegalArgumentException {
		if (o == null) {
			System.err.println("ERROR - Order is NULL");
			throw new IllegalArgumentException("Order is NULL");
		}
		if (orderLines == null) {
			orderLines = new ArrayList<OrderLine>();
		}
		orderLines.add(o);
	}

	public int size() {
		return orderLines.size();
	}

	public OrderLine get(int i) {
		return orderLines.get(i);
	}

	public void clear() {
		this.orderLines.clear();
	}
}

/*
 * represents the algorithm to calculate taxes depending on if the order item is imported or not
 * After calculation, it displays in the console the cost of each item with subtotal for each order
 * followed by the grand total of all the items
 */
class calculator {

	/**
	 * receives a collection of orders. For each order, iterates on the order lines and calculate the total price which
	 * is the item's price * quantity * taxes.
	 * 
	 * For each order, print the total Sales Tax paid and Total price without taxes for this order
	 */
	public void calculate(Map<String, Order> o) {

		BigDecimal grandtotal = new BigDecimal("0.00");
		grandtotal.setScale(Item.USD.getDefaultFractionDigits(), Item.DEFAULT_ROUNDING);
		
		// Iterate through the orders
		for (Map.Entry<String, Order> entry : o.entrySet()) {
			System.out.println("*******" + entry.getKey() + "*******");

			Order r = entry.getValue();

			BigDecimal totalTax = new BigDecimal("0.00");
			BigDecimal total = new BigDecimal("0.00");

			// Iterate through the items in the order
			for (int i = 0; i < r.size(); i++) {

				// Calculate the taxes
				Item it = r.get(i).getItem();
				BigDecimal tax = it.computeTax();
				
				
				// Calculate the total price
				BigDecimal totalprice = it.getPrice().add(tax);

				// Print out the item's total price
				System.out.println(r.get(i).getQuantity() + " " + it.getDescription() + ": " + totalprice);

				// Keep a running total
				totalTax = totalTax.add(tax);
				total = total.add(it.getPrice());
			}

			// Print out the total taxes
			totalTax.setScale(Item.USD.getDefaultFractionDigits(), Item.DEFAULT_ROUNDING);
			System.out.println("Sales Tax: " + totalTax);

			// Print out the total amount
			System.out.println("Total: " + total);
			grandtotal = grandtotal.add(total);
			
		}

		System.out.println("Sum of orders: " + grandtotal);
	}
}

/*
 * The executable main class for creating orders that contain orderlines and calculating the 
 * total cost for the orders.
 */
public class Foo {

	/*
	 * The main method - no parameters required
	 */
	public static void main(String[] args) throws Exception {

		Map<String, Order> o = new TreeMap<String, Order>();

		Order c = new Order();


		c.add(new OrderLine(new Item("book", (double) 12.49), 1));
		c.add(new OrderLine(new Item("music CD", (double) 14.99), 1));
		c.add(new OrderLine(new Item("chocolate bar", (double) 0.85), 1));

		o.put("Order 1", c);

		// Reuse cart for an other order
		//c.clear();
		c = new Order();
		
		c.add(new OrderLine(new Item("imported box of chocolate", 10), 1));
		c.add(new OrderLine(new Item("imported bottle of perfume", (double) 47.50), 1));

		o.put("Order 2", c);

		// Reuse cart for an other order
		//c.clear();
		c = new Order();
		c.add(new OrderLine(new Item("Imported bottle of perfume", (double) 27.99), 1));
		c.add(new OrderLine(new Item("bottle of perfume", (double) 18.99), 1));
		c.add(new OrderLine(new Item("packet of headache pills", (double) 9.75), 1));
		c.add(new OrderLine(new Item("box of imported chocolates", (double) 11.25), 1));

		o.put("Order 3", c);

		new calculator().calculate(o);

	}
}