package com.hb.core.rest.processor;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.camel.Exchange;


public class DefaultRestResponse {
	public Response process(Exchange exchange) throws Exception {
		return Response.status(Status.FORBIDDEN).entity("access denied!").type(MediaType.TEXT_PLAIN)
				.build();
	}
}
