package model;

import java.sql.Connection;

import java.sql.*;
import java.sql.Statement;
import java.util.Base64;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

//import com.mysql.cj.xdevapi.Statement;

public class Users {


	//A common method to connect to the DB
	private Connection connect()
	{
		Connection con = null;
		try
		{
			Class.forName("com.mysql.jdbc.Driver");

			//Provide the correct details: DBServer/DBName, username, password
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/gadgetbadget","root","");
		}
		catch (Exception e)
		{e.printStackTrace();}
		return con;
	}


	//insert Users
	public String insertUser(String fname, String lname, String nic, String address, String phone,String email,String username, String password,String type)
	{
		String output = "";
		  String regex = "^(.+)@(.+)$";
		  
		//  String regex2 ="^\\d{10}$";
		  String regex3="^[0-9]{9}[vVxX]$";
		try
		{
			Connection con = connect();
			if (con == null)
			{
				return "Error while connecting to the database for inserting."; 
				}
			// create a prepared statement
			String query = " insert into users (`U_id`,`fname`,`lname`,`nic`,`address`,`phone`,`email`,`username`,`password`,`type`)" + " values (?, ?, ?, ?, ?,?,?,?,?,?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, fname);
			preparedStmt.setString(3, lname);
			preparedStmt.setString(4, nic);
			preparedStmt.setString(5, address);
			preparedStmt.setString(6, phone);
			preparedStmt.setString(7, email);
			preparedStmt.setString(8, username);
			preparedStmt.setString(9,Base64.getEncoder().encodeToString( password.getBytes()));
			preparedStmt.setString(10, "Buyer");
			// execute the statement
			
		if(password.length() < 6 )
			{
				output = "Password is too short !";
			}
		if(!(email.matches(regex))) {
			
			output = "Invalid e-mail address !";
		}
		
      if(!(nic.matches(regex3))) {
			
			output = "Invalid NIC !";
		}
		
		else {
			preparedStmt.execute();
			con.close();
			output = "You have successfully registered.";
		}
		}
		catch (Exception e)
		{
			output = "Registration failed !";
			System.err.println(e.getMessage());
		}
		return output;
	}
	
	
	
	//update profile details
	 public String updateUserinfo(String ID,String fname, String lname, String nic, String address, String phone,String email,String username, String password)  {  
		 String output = ""; 
	 
	  try   {    
		  Connection con = connect(); 
	 
	   if (con == null)   
	   {
		   return "Error while connecting to the database for updating."; 
		   
	   } 
	 
	    String query = "UPDATE users SET fname=?,lname=?,nic=?,address=?,phone=?,email=?,username=?,password=?     WHERE U_id=?"; 
	 
	   PreparedStatement preparedStmt = con.prepareStatement(query); 
	 
	      preparedStmt.setString(1, fname);   
	      preparedStmt.setString(2, lname);   
	      preparedStmt.setString(3, nic);  
	      preparedStmt.setString(4, address); 
	      preparedStmt.setString(5, phone); 
	      preparedStmt.setString(6, email); 
	      preparedStmt.setString(7, username); 
	      preparedStmt.setString(8, Base64.getEncoder().encodeToString( password.getBytes()));
	      //preparedStmt.setString(8,  password);
	      preparedStmt.setInt(9, Integer.parseInt(ID)); 
	 
	      preparedStmt.execute();   
	      con.close(); 
	 
	      output = "User details updated successfully"; 
	   
	  }  
	  catch (Exception e)   
	  {  
		   output = "Error while updating the user details.";  
		   System.err.println(e.getMessage()); 
		   
	  } 
	 
	  return output; 
	  } 
	 
	 
	/*
	 * //change password public String updatePassword(String username, String
	 * password) { String output = "";
	 * 
	 * try { Connection con = connect();
	 * 
	 * if (con == null) { return
	 * "Error while connecting to the database for changing password.";
	 * 
	 * }
	 * 
	 * String query = "UPDATE users SET password=?     WHERE username=?";
	 * 
	 * PreparedStatement preparedStmt = con.prepareStatement(query);
	 * 
	 * //preparedStmt.setString(1, username); preparedStmt.setString(1, password);
	 * 
	 * 
	 * preparedStmt.execute(); con.close();
	 * 
	 * output = "Password changed Successfully.";
	 * 
	 * } catch (Exception e) { output = "Error while changing the password.";
	 * System.err.println(e.getMessage());
	 * 
	 * }
	 * 
	 * return output; }
	 */
	 
	 
 //delete profile details
	 
	 public String deleteUserinfo(String U_id)  {   
		 
		 String output = ""; 
	 
	  try   {   
		  
		  Connection con = connect(); 
	 
	   if (con == null)    
	   
	   {
		   return "Error while connecting to the database for deleting.";
		   
	   } 
	 
	     String query = "delete from users where U_id=?"; 
	 
	     PreparedStatement preparedStmt = con.prepareStatement(query); 
	 
	   	preparedStmt.setInt(1, Integer.parseInt(U_id)); 
	 
	     preparedStmt.execute();   
	     con.close(); 
	 
	   output = "User deleted successfully";   } 
	  catch (Exception e)  
	  {    
		  output = "Error while deleting the User.";  
		  System.err.println(e.getMessage());  
		  } 
	 
	  return output; 
	  } 
	


	//login 

	public String login(String username, String password) {



		try {

			Connection con = connect(); 
			
			if (con == null)    

			{
				
				return "-----Error while connecting to the database for login --------";

			} 

			System.out.println("wor");
			String query = "select `username`,`password` from `users` where `username` = ? and `password` = ?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			//System.out.println("1111");
			System.out.println(preparedStmt);
			System.out.println(username);
			System.out.println(Base64.getEncoder().encodeToString( password.getBytes()));
			
			
			
			
//			byte[] decodedBytes = Base64.getDecoder().decode(password);
//			String decodedString = new String(decodedBytes);
			
			preparedStmt.setString(1,username);
			preparedStmt.setString(2, Base64.getEncoder().encodeToString( password.getBytes()));
			//System.out.println("222");
			
			
			ResultSet rs = preparedStmt.executeQuery();


			if(rs.next()) {
				con.close();
				if(username.equals("admin"))
				{
					
					return "You are now logged in.";
				}
				return "Hi"+" "+username+"."+"You are now successfully logged in.";
			}
			else {
				con.close();
				
				if(username.equals("")  )
				{
					
					return "username cannot be empty";
				}
				
				else if( password.equals(""))
				{
					
					return "password cannot be empty";
				}
				else {
				return  "Incorrect username or password ! ";
				}
			}

		}
		catch(Exception e) 
		{
			
			System.out.println(e);
			return "Error while connecting to the database for login.";

		}


	}


	// view profile details
	public String viewProfile(String U_id) {
		
		
		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for reading.";
			}

			output = "<table border=\"1\"><tr><th>First Name</th><th>Last Name</th><th>NIC</th><th>Address</th><th>Phone Number</th><th>E-mail</th><th>Username</th><th>Password</th><th>Update</th><th>Remove</th></tr>";

			String query = "select *  from users where U_id=' " + U_id + "'" ;
			

			Statement stmt = con.createStatement();
		    
		     
			ResultSet rs = stmt.executeQuery(query);
			
			while (rs.next()) {
				String user = Integer.toString(rs.getInt("U_id"));
				String fname = rs.getString("fname");
				String lname = rs.getString("lname");
				String nic = rs.getString("nic");
				String address = rs.getString("address");
				String phone = rs.getString("phone");
				String email = rs.getString("email");
				String username = rs.getString("username");
				String password = rs.getString("password");
				
				
				
				
				output += "<tr><td>" + fname + "</td>";
				output += "<td>" + lname + "</td>";
				output += "<td>" + nic + "</td>";
				output += "<td>" + address + "</td>";
				output += "<td>" + phone + "</td>";
				output += "<td>" + email + "</td>";
				output += "<td>" + username + "</td>";
				output += "<td>" + password + "</td>";
				
				
				
				
				
				  output +=
				  "<td><input name=\"btnUpdate\" type=\"button\"  value=\"Update\" class=\"btn btn-secondary\"></td>"
				  + "<td><form method=\"post\" action=\"UsertReg.jsp\">" +
				  "<input name=\"btnRemove\" type=\"submit\" value=\"Remove\"  class=\"btn btn-danger\">"
				  + "<input name=\"UserID\" type=\"hidden\" value=\"" + user + "\">" +
				  "</form></td></tr>";
				 
			}
			
			con.close();

			output += "</table>";
			
			
		} catch (Exception e) 
		{
			
			output = "Error while reading the user.";
			System.err.println(e.getMessage());
		}

		return output;
	}
	
	
	
	
	
	//find details of a certain user type(for Admin)
	public String readUserType(String type) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}
			// Prepare the html table to be displayed
			output = "<table border=\"1\"><tr><th>First Name</th><th>Last Name</th><th>NIC</th><th>Address</th><th>Phone Number</th><th>E-mail</th><th>Username</th><th>Password</th><th>Update</th><th>Remove</th></tr>";

			String query = "select * from users where type =?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			preparedStmt.setString(1, type);
			ResultSet rs = preparedStmt.executeQuery();

			// iterate through the rows in the result set
			while (rs.next()) {
				String user = Integer.toString(rs.getInt("U_id"));
				String fname = rs.getString("fname");
				String lname = rs.getString("lname");
				String nic = rs.getString("nic");
				String address = rs.getString("address");
				String phone = rs.getString("phone");
				String email = rs.getString("email");
				String username = rs.getString("username");
				String password = rs.getString("password");
				
				
				
				
				output += "<tr><td>" + fname + "</td>";
				output += "<td>" + lname + "</td>";
				output += "<td>" + nic + "</td>";
				output += "<td>" + address + "</td>";
				output += "<td>" + phone + "</td>";
				output += "<td>" + email + "</td>";
				output += "<td>" + username + "</td>";
				output += "<td>" + password + "</td>";
				
				
				
				  output +=
						  "<td><input name=\"btnUpdate\" type=\"button\"  value=\"Update\" class=\"btn btn-secondary\"></td>"
						  + "<td><form method=\"post\" action=\"UsertReg.jsp\">" +
						  "<input name=\"btnRemove\" type=\"submit\" value=\"Remove\"  class=\"btn btn-danger\">"
						  + "<input name=\"UserID\" type=\"hidden\" value=\"" + user + "\">" +
						  "</form></td></tr>";
				  
			
			}
			con.close();

			// Complete the html table
			output += "</table>";
		} catch (Exception e) {
			output = "Error while reading the user types.";
			System.err.println(e.getMessage());
		}
		return output;
	}


	
	// read users(for Admin)
	public String viewRegUsers() {
		// TODO Auto-generated method stub
		String output = "";
		 try
		 {
		 Connection con = connect();
		 if (con == null)
		 {return "Error while connecting to the database for reading."; }
		 // Prepare the html table to be displayed
		 output = "<table border=\"1\"><tr><th>First Name</th><th>Last Name</th><th>NIC</th><th>Address</th><th>Phone Number</th><th>E-mail</th><th>Username</th><th>Password</th><th>Update</th><th>Remove</th></tr>";


		 String query = "select * from users";
		 Statement stmt = con.createStatement();
		 ResultSet rs = stmt.executeQuery(query);
		 
		 
		 // iterate through the rows in the result set
		 while (rs.next())
		 {
			 String user = Integer.toString(rs.getInt("U_id"));
				String fname = rs.getString("fname");
				String lname = rs.getString("lname");
				String nic = rs.getString("nic");
				String address = rs.getString("address");
				String phone = rs.getString("phone");
				String email = rs.getString("email");
				String username = rs.getString("username");
				String password = rs.getString("password");
				
		 
		 // Add into the html table
				
				output += "<tr><td>" + fname + "</td>";
				output += "<td>" + lname + "</td>";
				output += "<td>" + nic + "</td>";
				output += "<td>" + address + "</td>";
				output += "<td>" + phone + "</td>";
				output += "<td>" + email + "</td>";
				output += "<td>" + username + "</td>";
				output += "<td>" + password + "</td>";
		 
				
				  // buttons
				  
				  output +=
				  "<td><input name=\"btnUpdate\" type=\"button\"  value=\"Update\" class=\"btn btn-secondary\"></td>"
				  + "<td><form method=\"post\" action=\"UserReg.jsp\">" +
				  "<input name=\"btnRemove\" type=\"submit\" value=\"Remove\"  class=\"btn btn-danger\">"
				  + "<input name=\"UserID\" type=\"hidden\" value=\"" + user + "\">" +
				  "</form></td></tr>";
				 
		
		 }
				con.close();
		 
		 // Complete the html table
		 output += "</table>";
		 }
		 catch (Exception e)
		 {
		 output = "Error while reading the users.";
		 System.err.println(e.getMessage());
		 }
		 return output;
	}


	


}
