package com.dropouts.hack.backend;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import com.dropouts.hack.backend.login.User;

@Path("/hello")
public class Test {
	
	@GET
	@Path("/{param}")
	public Response getMsg(@PathParam("param") String msg) {
 
		String output = "" + msg;
 
		return Response.status(200).entity(output).build();
 
	}
	
	public static void main(String[] args) {
		User user = new User();
		
	}
}