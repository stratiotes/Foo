/**
 * 
 */
package com.tek.interview.question;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Currency;

/**
 * Represents money for an Order as US Dollar currency
 *
 * @author stratiotes
 *
 */
public class OrderMoney {
	static final Currency USD = Currency.getInstance("USD");
	static final RoundingMode DEFAULT_ROUNDING = RoundingMode.HALF_UP;
	private BigDecimal currentValue;
	private RoundingMode roundMode;
	
	public OrderMoney(String initialAmount) {
		this(initialAmount, DEFAULT_ROUNDING);
	}
	
	public OrderMoney(String initialAmount, RoundingMode roundmode) {
		this.currentValue = new BigDecimal(initialAmount);
		this.roundMode = roundmode;
	}
	
	public OrderMoney(BigDecimal initialAmount, RoundingMode rm) {
		this(initialAmount.toString(), rm);
	}
	
	/**
	 * Display helper that determines the appropriate 
	 * format of the amount this OrderMoney object represents
	 * @see java.lang.Object#toString()
	 */
	public final String toString() {
		DecimalFormat df = new DecimalFormat();

		df.setMaximumFractionDigits(2);
		df.setMinimumFractionDigits(0);
		df.setRoundingMode(roundMode);

		//df.setGroupingUsed(false);

		String result = df.format(currentValue);
		return result;
	}
		
	/**
	 * Ultity method for multiplying the value by the value
	 * of another OrderMoney object.
	 */
	public OrderMoney multiply(final OrderMoney multiplicand) {
		BigDecimal mult = new BigDecimal(multiplicand.toString());
		return new OrderMoney(currentValue.multiply(mult), multiplicand.roundMode);
	}
	
	/**
	 * Utility method for adding the value with the value of 
	 * another OrderMoney object.
	 */
	public OrderMoney add(final OrderMoney amount) {
		BigDecimal a = new BigDecimal(amount.toString());
		return new OrderMoney(currentValue.add(a), amount.roundMode);
	}
	
}
