package com.hb.core.rest.api;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import com.hb.core.entity.User;

@Path("/crud")
public interface CrudService {
	@GET
	@Path("/user/{userId}")
	public User getUser(@PathParam("userId") String id);
	
	@POST
	@Path("/user")
	public User newUser(User user);
	
}
