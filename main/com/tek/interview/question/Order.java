package com.tek.interview.question;

import java.util.ArrayList;
import java.util.List;

/**
 * represents an order which wraps a list of @link OrderLine objects.
 * @author stratiotes
 *
 */
public class Order {

	private String Name;
	private List<OrderLine> orderLines;
	
	public Order(String name) {
		this.Name = name;
	}

	/**
	 * adds an OrderLine to the Order.  The OrderLine must not be null.
	 * @param o
	 * @throws IllegalArgumentException
	 */
	public void add(OrderLine order) throws IllegalArgumentException {
		if (order == null) {
			System.err.println("ERROR - Order is NULL");
			throw new IllegalArgumentException("Order is NULL");
		}
		if (orderLines == null) {
			orderLines = new ArrayList<OrderLine>();
		}
		orderLines.add(order);
	}

	/**
	 * @return the Name of the Order
	 */
	public String getName() {
		return Name;
	}
	
	/**
	 * @return the number of OrderLines in the Order
	 */
	public int size() {
		return orderLines == null ? 0 : orderLines.size();
	}

	/**
	 * @param i, index of the OrderLine to return
	 * @return the OrderLine at the given index
	 */
	public OrderLine get(int i) {
		return orderLines == null ? null : orderLines.get(i);
	}

	/**
	 * Clears the OrderLines from the Order, an empty Order is the result
	 */
	public void clear() {
		if ( orderLines != null )
			orderLines.clear();
	}
}