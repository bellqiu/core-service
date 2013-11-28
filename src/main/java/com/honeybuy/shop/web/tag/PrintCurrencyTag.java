package com.honeybuy.shop.web.tag;

import javax.servlet.ServletRequest;

public class PrintCurrencyTag extends AbstractHBTag{
	
	private static final long serialVersionUID = 4262739972508526664L;

	private boolean symbol = false;

	@Override
	public String handle(ServletRequest request) {
		request.setAttribute("__symbol", symbol);
		return "printCurrency";
	}
	
	@Override
	public void clean(ServletRequest request) {
		request.removeAttribute("__symbol");
		symbol = false;
	}

	public boolean isSymbol() {
		return symbol;
	}

	public void setSymbol(boolean symbol) {
		this.symbol = symbol;
	}


}
