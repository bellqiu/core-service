package com.hb.core.convert;

public interface Converter <D, E>{
	D convert(E e);
	E transf(D d);
}
