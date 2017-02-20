package com.tek.interview.question;

import java.math.RoundingMode;

/**
 * represents an item, contains a price and a description.
 * @author stratiotes
 *
 */
public class Item {
	// Object members
	private String description;
	private OrderMoney price;
	
	/**
	 * private method for determining if the item is an import
	 * item based on the description given.
	 *  Encapsulates the rather kludgey method of a description string test for determining
	 *  which tax to use.  
	 *  TODO: Explore/discuss with team a better way to tie the appropriate tax amount to the Item based on planned use of this class.  
	 */
	static private boolean isImportDescription(String desc) {
		return desc.toUpperCase().contains("IMPORT");
	}
	
	public Item(final String description, final double price) {
		super();
		this.description = description;
		if( isImportDescription(description))
			this.price = new OrderMoney(Double.toString(price), RoundingMode.HALF_DOWN);
		else
			this.price = new OrderMoney(Double.toString(price));
	}

	/**
	 * returns the String description of the Item
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * returns the OrderMoney price of the Item
	 */
	public OrderMoney getPrice() {
		return price;
	}
	
	/**
	 *  returns the appropriate TaxCalculator (import or domestic) to be used for the item.
	 */
	public TaxCalculator getTaxCalculator() {
		if( isImportDescription( getDescription()) ) {
			return new TaxCalculator(TaxCalculator.IMPORT_TAX);
		}
		else {
			return new TaxCalculator();
		}
	}
}