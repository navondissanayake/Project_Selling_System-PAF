package com;

import javax.ws.rs.Consumes;




import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

//For JSON
import com.google.gson.*;

//For XML
	import org.jsoup.*;
	import org.jsoup.parser.*;
	import org.jsoup.nodes.Document;


import model.Order;

@Path("/Order")
public class OrderService {
	
	
	Order ordObj = new Order();

	
	
	//view order details
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readOrders()
	{
		return ordObj.readOrders(); 
	 
	} 
	
	
	
	
	
	
	//view each buyer order details
	@GET
	@Path("/{BuyerID}")
	@Produces(MediaType.TEXT_PLAIN)
	public String readOrdersbuyer(@PathParam("BuyerID") String BuyerID)
	 {
		
		return ordObj.readOrdersbuyer(BuyerID); 
		
	 }
	
	

	
	
	
	
	
	//insert orders
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertOrder(
	 @FormParam("Name") String name,
	 @FormParam("BuyerID") String buyerID,
	 @FormParam("productID") String productID,
	 @FormParam("Proj_id") String projectID,
	 @FormParam("Price") String price,
	 @FormParam("qty") int qty)
	{
		
	 String output = ordObj.insertOrder(name,buyerID, productID,projectID, price,qty);
	 return output;
	 
	}
	
	
	
	
	
	//update order details
	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateItem(String itemData)
	{
		
		
	//Convert the input string to a JSON object
	 JsonObject itemObject = new JsonParser().parse(itemData).getAsJsonObject();
	 
	//Read the values from the JSON object
	 String orderID = itemObject.get("orderID").getAsString();
	 int qty = Integer.parseInt(itemObject.get("qty").getAsString());

	 String output = ordObj.updateOrderQuantity(orderID, qty);
	 
	 
	 return output;
	 
	}
	
	
	
	
	
	
	//delete order details
	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteItem(String itemData)
	{
		
	//Convert the input string to an XML document
	 Document doc = Jsoup.parse(itemData, "", Parser.xmlParser());

	 
	//Read the value from the element <itemID>
	 String orderID = doc.select("orderID").text();
	 String output = ordObj.deleteOrders(orderID);
	 return output;
	 
	 
	}

}
