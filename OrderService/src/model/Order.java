package model;

import java.sql.Connection;



import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;


public class Order {
	
	
	    //A common method to connect to the DB
		private Connection connect(){
			
			Connection con = null;
			
			try
			{
				
				Class.forName("com.mysql.jdbc.Driver");
				//Provide the correct details: DBServer/DBName, username, password
				con= DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/gadgetbadget","root", "1999ppdd");
			}
			catch (Exception e)
			{
				e.printStackTrace();
				
			
			}
			
		    return con;
		}
	
		  
	  
		
		
		// add orders
		 public String addOrders(String name ,String buyerID, String productID ,String projectID ,String price , int qty  ){
		
		
			String output = "";
	
			int items_qty=0;
			boolean validate = false;
			java.sql.Date sqlDate = new java.sql.Date(new java.util.Date().getTime());
			
		
			try
			{
				
				Connection con = connect();
				
				if (con == null)
				{
					return "Error while connecting to the database for inserting."; 
			
			
				}
	
				
				
				String query2 = " select Stock_qty from items where Item_id = ?";
				PreparedStatement preparedStmt2 = con.prepareStatement(query2);
				preparedStmt2.setString(1, productID);
				ResultSet rs = preparedStmt2.executeQuery();
				
				
				
				//newly added
				String query3 = " select * from projects where Proj_id = ?";
				PreparedStatement preparedStmt3 = con.prepareStatement(query3);
				preparedStmt3.setString(1, projectID);
				ResultSet rs3 = preparedStmt3.executeQuery();
				
	
				while(rs.next()) {
					items_qty= rs.getInt("Stock_qty");
		
				}
	
	
				if(qty > items_qty) {
					
					validate = false;
					output = " only " + items_qty + " quantity available!" ;
				}
				
				
				else {
					validate = true;
				}
				
				
				
					//newly added
					if(validate || (!(projectID.isEmpty()))) {
					
			
		
		        
					// create a prepared statement
					String query = " insert into orders(`orderID`,`Name`,`BuyerID`,`productID`,`Proj_id`,`Price`,`qty`,`Date`)"+ "values(?,?,?,?,?,?,?,?)";
					
					PreparedStatement preparedStmt = con.prepareStatement(query);
					
			
					
					// binding values
					preparedStmt.setInt(1, 0);
					preparedStmt.setString(2, name);
					preparedStmt.setString(3, buyerID);
					preparedStmt.setString(4, productID);
					preparedStmt.setString(5, projectID);//newly added
					preparedStmt.setDouble(6, Double.parseDouble(price));
					preparedStmt.setInt(7, qty);
				    preparedStmt.setDate(8, sqlDate);
					
					
					// execute the statement
			
					preparedStmt.execute();
					con.close();
					
					
					output = "Inserted orders successfully";
					
					}
				
				
				
				
				}
			
					catch (Exception e)
					{
						
					output = "Error while inserting the orders.";
					System.err.println(e.getMessage());
					}
			
			
				return output;
				
				
				
			}
			
			
	  
	    
	
	   //view orders
	   public String viewOrders() {
		
			
			String output = "";
			
			
			
			try {
				
				
				
				Connection con = connect();
				
				
				if (con == null) {
					
					return "Error while connecting to the database for reading.";
				}
				
				
				// Prepare the html table to be displayed
				output = "<table border='1'><tr><th>Order ID</th><th>Name</th><th>Buyer ID</th>" + "<th>Product ID</th>"+ "<th>Project ID</th>" + "<th>Price</th>"+ "<th>Quantity</th>" + "<th>Order Date</th>" + "<th>Update</th><th>Remove</th></tr>";
	
				
				String query = "select * from orders";
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(query);
	
				
				
				// iterate through the rows in the result set
				while (rs.next()) {
					String orderID = Integer.toString(rs.getInt("orderID"));
					String name = rs.getString("Name");
					String buyerID = rs.getString("BuyerID");
					String productID = rs.getString("productID");
					String projectID = rs.getString("Proj_id");
					String price = Double.toString(rs.getDouble("Price")); 
					String qty = Integer.toString(rs.getInt("qty"));
					String orderDate = rs.getString("Date");
					
	
					
					// Add into the html table
					output += "<tr><td>" + orderID + "</td>";
					output += "<td>" + name + "</td>";
					output += "<td>" + buyerID + "</td>";
					output += "<td>" + productID + "</td>";
					output += "<td>" + projectID + "</td>";
					output += "<td>" + price + "</td>";
					output += "<td>" + qty + "</td>";
					output += "<td>" + orderDate + "</td>";
	
					// buttons
					output += "<td><input name='btnUpdate' type='button' value='Update'class='btn btn-secondary'></td>"+ "<td><form method='post' action='items.jsp'>"+ "<input name='btnRemove' type='submit' value='Remove'class='btn btn-danger'>" + "<input name='orderID' type='hidden' value='" + orderID + "'>" + "</form></td></tr>";
				}
				con.close();
				
	
				// Complete the html table
				output += "</table>";
				
				
			} catch (Exception e) {
				
				output = "Error while reading the orders.";
				System.err.println(e.getMessage());
			}
			
			return output;
			
		}
	
	
	
	
	
	
	
	
	


	
	//view each  buyer order details
	public String viewBuyerOrders(String BuyerID){
		
		
		String output = "";
		
		
		try {
			
			
			Connection con = connect();
			
			
			if (con == null) {
				
				
				return "Error while connecting to the database for reading.";
			}
			
			
			
			// Prepare the html table to be displayed
			output = "<table border='1'><tr><th>Order ID</th><th>Name</th><th>Buyer ID</th><th>Product ID</th>"+ "<th>Price</th>" + "<th>Quantity</th>" + "<th>Order Date</th>" +"<th>Update</th><th>Remove</th></tr>";

			

			String query = "select * from orders where BuyerID =?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			preparedStmt.setString(1, BuyerID);
			ResultSet rs = preparedStmt.executeQuery();

		    
		  
			
			// iterate through the rows in the result set
			while (rs.next()) {
				
				
				String orderID = Integer.toString(rs.getInt("orderID"));
				String name = rs.getString("Name");
				String buyerID = rs.getString("BuyerID");
				String productID = rs.getString("productID");
				String price = Double.toString(rs.getDouble("Price")); 
				String qty = Integer.toString(rs.getInt("qty"));
				String orderDate = rs.getString("Date");
				
				
				

				// Add into the html table
				output += "<tr><td>" + orderID + "</td>";
				output += "<td>" + name + "</td>";
				output += "<td>" + buyerID + "</td>";
				output += "<td>" + productID + "</td>";
				output += "<td>" + price + "</td>";
				output += "<td>" + qty + "</td>";
				output += "<td>" + orderDate + "</td>";

				
				
				
				// buttons
				output += "<td><input name='btnUpdate' type='button' value='Update'class='btn btn-secondary'></td>"
						+ "<td><form method='post' action='order.jsp'>"
						+ "<input name='btnRemove' type='submit' value='Remove'class='btn btn-danger'>"
						+ "<input name='orderID' type='hidden' value='" + orderID + "'>" + "</form></td></tr>";
			}
			
			con.close();

			// Complete the html table
			output += "</table>";
		} catch (Exception e) {
			
			
			output = "Error while reading the orders.";
			System.err.println(e.getMessage());
		}
		
		
		return output;
	}
	
	

	

		
	
	
	
	//update order items quantity
	public String updateOrders(String ID, int qty) {
		
		
		String output = "";
		
		
		try {
			
			
			Connection con = connect();
			
			
			if (con == null) {
				
				return "Error while connecting to the database for updating.";
			}
			
			
			// create a prepared statement
			String query = "UPDATE orders SET qty=? WHERE orderID=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			
			// binding values
			preparedStmt.setInt(1, qty);
			preparedStmt.setString(2, ID);

			
			// execute the statement
			preparedStmt.execute();
			con.close();
			
			
			output = "Quantity Updated  successfully";
			
		} catch (Exception e) {
			
			
			output = "Error while updating the order.";
			System.err.println(e.getMessage());
		}
		
		
		return output;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	//delete order
	public String deleteOrders(String ID) {
		
		String output = "";
		
		
		try {
			
			Connection con = connect();
			
			if (con == null) {
				
				return "Error while connecting to the database for deleting.";
			}
			
			// create a prepared statement
			String query = "delete from orders where orderID=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			
			// binding values
			preparedStmt.setInt(1, Integer.parseInt(ID));
			
			// execute the statement
			preparedStmt.execute();
			
			con.close();
			
			output = "Deleted successfully";
			
			
		} catch (Exception e) {
			
			output = "Error while deleting the order.";
			System.err.println(e.getMessage());
			
		}
		
		return output;
	}
	
	



}
