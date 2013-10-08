package com.honeybuy.shop.web.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.hb.core.exception.CoreServiceException;

public abstract class AbstractHBTag extends TagSupport {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);
		
		WebApplicationContext ctx = WebApplicationContextUtils
				.getRequiredWebApplicationContext(pageContext
						.getServletContext());

		AutowireCapableBeanFactory bf = ctx.getAutowireCapableBeanFactory();

		bf.autowireBean(this);
	}

	@Override
	public int doEndTag() throws JspException {

		try {
			pageContext.include("/WEB-INF/jsp/tag/"+handle(pageContext)+".jsp");
		} catch (Exception e) {
			throw new CoreServiceException(e);
		}

		return TagSupport.EVAL_PAGE;
	}

	public abstract String handle(PageContext context);

}
