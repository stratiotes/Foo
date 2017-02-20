package com.tek.interview.question;

import java.math.RoundingMode;

/**
 * Represents the a generic tax calculator algorithm 
 * @author stratiotes
 */
public class TaxCalculator {
	public static final OrderMoney IMPORT_TAX = new OrderMoney("0.15", RoundingMode.FLOOR);
	public static final OrderMoney DOMESTIC_TAX = new OrderMoney("0.10", RoundingMode.HALF_UP);

	private OrderMoney percentage;
	
	public TaxCalculator() {
		this(DOMESTIC_TAX);
	}
	
	public TaxCalculator(OrderMoney taxPercentage) {
		this.percentage = taxPercentage;
	}
	
	/**
	 * Computes the appropriate tax for a given price
	 * @param priceOfItem an OrderMoney price for which to compute the tax
	 * @return an OrderMoney object represent the tax portion of the @param
	 */
	public OrderMoney computeTax(final OrderMoney priceOfItem) {
		
		return percentage.multiply(priceOfItem);
	}
}
