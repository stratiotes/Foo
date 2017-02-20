package com.tek.interview.question;

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

/**
 * The executable main class for creating orders that contain orderlines and calculating the 
 * total cost for the orders.
 */
public class Foo {
	
	/**
	 * receives a collection of orders. For each order, iterates on the order lines and display the total price 
	 * which  is the item's price and quantity plus computed taxes.
	 * 
	 * For each order, print the total Sales Tax paid and Total price without taxes for this order
	 */
	private static void displayCalculations(Map<String, Order> o) {
		
		OrderMoney grandtotal = new OrderMoney("0.0");
		
		// Iterate through the orders
		for (Map.Entry<String, Order> entry : o.entrySet()) {

			Order r = entry.getValue();
			System.out.println("*******" + r.getName() + "*******");

			OrderMoney totalTax = new OrderMoney("0.0");
			OrderMoney total = new OrderMoney("0.0");

			// Iterate through the items in the order
			for (int i = 0; i < r.size(); i++) {

				// Calculate the taxes
				Item it = r.get(i).getItem();
				TaxCalculator taxer = it.getTaxCalculator();
				OrderMoney taxAmt = taxer.computeTax(it.getPrice());
				
				
				// Calculate the total price
				OrderMoney totalprice = it.getPrice().add(taxAmt);

				// Print out the item's total price
				System.out.println(r.get(i).getQuantity() + " " + it.getDescription() + ": " + totalprice.toString());

				// Keep a running total
				totalTax = totalTax.add(taxAmt);
				total = total.add(it.getPrice());
			}

			// Print out the total taxes
			System.out.println("Sales Tax: " + totalTax);

			// Print out the total amount
			System.out.println("Total: " + total);
			grandtotal = grandtotal.add(total);
			
		}

		System.out.println("Sum of orders: " + grandtotal);
	}

	
	/**
	 * The main method - a single parameter is the filePath to an XML file properly formatted with Order
	 * information to be processed.  
	 */
	public static void main(String[] args) throws Exception {

		Map<String, Order> OrdersMap = new TreeMap<String, Order>();
		
		if(args == null || args.length == 0) {
			System.out.println("Usage: java Foo ordersXmlFilePath");
		}
		else {
			OrderUnpacker orderUnpacker = new OrderUnpacker();
			orderUnpacker.unpackXML(args[0], OrdersMap);
			if ( OrdersMap.isEmpty() ) {
				System.out.println("Failed to unpack Orders XMLFile at " + args[0]);
			}
			else {
				Foo.displayCalculations(OrdersMap);
			}
		}

	}
}