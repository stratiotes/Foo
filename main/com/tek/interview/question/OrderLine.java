package com.tek.interview.question;

/**
 * represents an order line which contains the @link Item and the quantity.
 * @author stratiotes
 *
 */
public class OrderLine {

	private int quantity;
	private Item item; 

	/**
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

	/**
	 * @return the Item object contained in the OrderLine
	 */
	public Item getItem() {
		return item;
	}

	/**
	 * @return the quanity of the Item contained in the OrderLine.
	 */
	public int getQuantity() {
		return quantity;
	}
}