package com.hb.core.rest.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.cxf.common.message.CxfConstants;

public class RestProcessor implements Processor{
        // Anonymous processor
        public void process(Exchange exchange) throws Exception {
          System.out.println(exchange.getIn().getHeader(CxfConstants.OPERATION_NAME, String.class).toString());
          exchange.getOut().setBody("success");
        }	
}
