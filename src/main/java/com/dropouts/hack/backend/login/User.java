package com.dropouts.hack.backend.login;

import javax.ws.rs.FormParam;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.dropouts.hack.backend.database.DatabaseManager;

@Path("/user")
public class User {
	
	private static DatabaseManager databaseManager;
	private static Session session;
	
	static{
		databaseManager = DatabaseManager.getInstance();
	}

	@POST
	@Path("/login")
	public Response authenticate(@FormParam("username") String userId, 
			@FormParam("password") String password){
		
		Session session = databaseManager.getSession();
		ResultSet user = session.execute("SELECT * FROM volunteer.user where user_id = '" + userId + "'");
		
		Row row = null;
		if(user.isExhausted()){
			session.close();
			return Response.status(200).entity("Invalid Username or Password").build();
		} else{
			row = user.one();
		}
		
		if (row.getString("user_id").equals(userId) && 
				row.getString("user_password").equals(password)){
			
			session.close();
			return Response.status(200).entity("success").build();
		} else {
			session.close();
			return Response.status(200).entity("Invalid Username or Password").build();
		}
	}
	
	@POST
	@Path("/register")
	public Response register(@FormParam("id") String userId, @FormParam("userType") String userType,
			@FormParam("username") String userName, @FormParam("password") String password, 
			@FormParam("dob") String dob, @FormParam("gender") String gender, 
			@FormParam("location") String location, 
			@FormParam("occupation") String occupation){
		
		Session session = databaseManager.getSession();
		ResultSet users = session.execute("SELECT * FROM volunteer.user");
		
		for (Row row : users) {
			if(row.getString("user_id").equalsIgnoreCase(userId)){
				return Response.status(200).entity("Username unavailable").build();
			}
		}
		
		session.execute(
                "INSERT INTO volunteer.user(user_id, user_type, user_name, user_password, user_dob, user_gender, user_location, user_occupation) " +
                        "VALUES (" +
                        "'"+userId.toLowerCase()+"'," +
                        "'"+userType+"'," +
                        "'"+userName+"'," +
                        "'"+password+"'," +
                        "'"+dob+"'," +
                        "'"+gender+"'," +
                        "'"+location+"'," +
                        "'"+occupation+
                        "');");
		
		session.close();
		
		return Response.status(200).entity("Registration successful").build();
	}
}
