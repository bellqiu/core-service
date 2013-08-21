package com.hb.core.rest.processor;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.camel.Exchange;

import com.hb.core.entity.User;


public class TestResponse2 {
	public Response process(Exchange exchange) throws Exception {
		Response rs = Response.status(Status.OK).entity(new User()).type(MediaType.TEXT_PLAIN)
				.build();
		 return rs;
	}
}
