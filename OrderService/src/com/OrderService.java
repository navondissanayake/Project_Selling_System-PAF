//IT19209944 - W.B.W.M.R.U.P.U.Aluvihare
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
	
	
	
	/*
	 * initialize single object from Order class
	 */
	Order ordObj = new Order();

	

	
	
	/*
	 * GET request and view all details
	 */
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	
	public String ViewOrders()
	{
		return ordObj.viewOrders(); 
	 
	} 
	
	
	
	
	
	
	/*
	 * GET request and view each buyer ordered details
	 */
	@GET
	@Path("/orderlist/{BuyerID}")
	@Produces(MediaType.TEXT_PLAIN)
	
	public String viewBuyerOrders(@PathParam("BuyerID") String BuyerID)
	{
		
		return ordObj.viewBuyerOrders(BuyerID); 
		
	}
	
	

	
	
	
	
	
	/*
	 * POST request and add ordered details
	 */
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	
	public String addOrders(
	 @FormParam("Name") String name,
	 @FormParam("BuyerID") String buyerID,
	 @FormParam("productID") String productID,
	 @FormParam("Proj_id") String projectID,
	 @FormParam("Price") String price,
	 @FormParam("qty") int qty)
	{
		
	  String output = ordObj.addOrders(name,buyerID, productID,projectID, price,qty);
	  return output;
	 
	}
	
	
	
	
	
	/*
	 * PUT request and update ordered details
	 */
	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	
	public String updateOrders(String itemData)
	{
		
		
		 //Convert the input string to a JSON object
		 JsonObject itemObject = new JsonParser().parse(itemData).getAsJsonObject();
		 
		 //Read the values from the JSON object
		 String orderID = itemObject.get("orderID").getAsString();
		 int qty = Integer.parseInt(itemObject.get("qty").getAsString());
	
		 String output = ordObj.updateOrders(orderID, qty);
		 
		 
		 return output;
		 
	}
	
	
	
	
	
	
	/*
	 * DELETE request and delete ordered details
	 */
	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	
	public String deleteOrders(String itemData)
	{
		
		//Convert the input string to an XML document
		Document doc = Jsoup.parse(itemData, "", Parser.xmlParser());

	 
		//Read the value from the element <itemID>
		String orderID = doc.select("orderID").text();
		String output = ordObj.deleteOrders(orderID);
		return output;
	 
	 
	}

}
