package com.hb.core.rest.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class TestProcessor implements Processor{
        // Anonymous processor
        public void process(Exchange exchange) throws Exception {
        	System.out.println(exchange);
        }	
}
