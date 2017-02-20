/**
 * 
 */
package com.tek.interview.question;

import java.io.File;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Provides methods for unpacking Orders from an XML file into a Map object.
 * TODO: Consider adding methods for unpacking from database 
 * @author stratiotes
 *
 */
public class OrderUnpacker {
	
	/**
	 * Helper method for unpacking OrderLines and their content
	 */
	protected String getDetailNodeValue( Node node ) {
	    NodeList childNodes = node.getChildNodes();
	    for (int x = 0; x < childNodes.getLength(); x++ ) {
	        Node data = childNodes.item(x);
	        if ( data.getNodeType() == Node.TEXT_NODE )
	            return data.getNodeValue();
	    }
	    return "";
	}	

	/**
	 * Unpacks an XML file with order information and put orders in the referenced Map 
	 * for processing.  
	 */
	public void unpackXML(final String ordersXmlFilePath, Map<String, Order> ordersToProcess) throws Exception  {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        DocumentBuilder builder = factory.newDocumentBuilder();
        
        // Load the XML document
        Document document = builder.parse(new File(ordersXmlFilePath));
        
        NodeList documentNodes = document.getDocumentElement().getChildNodes();
        
        for (int ii = 0; ii < documentNodes.getLength(); ii++) {
        	Order theOrder = null;
        	Node node = documentNodes.item(ii);
        	// unpack the order nodes
        	if (node.getNodeName().equalsIgnoreCase("Order")) {
        		String name = node.getAttributes().getNamedItem("ID").getNodeValue();
        		theOrder = new Order(name);
        		
        		NodeList olNodes = node.getChildNodes();
        		
        		for (int ol = 0; ol < olNodes.getLength();  ol++) {
        			Node itemNode = olNodes.item(ol);
        			
        			// unpack each order line of the order
        			if (itemNode.getNodeName().equalsIgnoreCase("OrderLine")) {
            			Integer quantity = 0;
            			String description = "";
            			Double price = 0.0;
            			
            			
        				NodeList odNodes = itemNode.getChildNodes();
        				
        				for (int od = 0; od < odNodes.getLength(); od++) {
        					Node odNode = odNodes.item(od);

        					if ( odNode.getNodeType() == Node.ELEMENT_NODE ) {
                				String data = getDetailNodeValue(odNode);
                				
	                			if (odNode.getNodeName().equalsIgnoreCase("Quantity")) {
	                				quantity = Integer.parseInt(data);
	                			}
	                			
	                			if (odNode.getNodeName().equalsIgnoreCase("Description")) {
	                				description = data;
	                			}
	                			
	                			if (odNode.getNodeName().equalsIgnoreCase("Price")) {
	                				price = Double.parseDouble(data);
	                			}
        					}
        				}
        				
        				Item theItem = new Item(description, price.doubleValue());
	       				OrderLine theOL = new OrderLine(theItem, quantity.intValue());
	       				
	       				theOrder.add(theOL);
	       				
        			}
       				ordersToProcess.put(theOrder.getName(), theOrder);
        			
        		}
        		
        	}
        }
	}
}

