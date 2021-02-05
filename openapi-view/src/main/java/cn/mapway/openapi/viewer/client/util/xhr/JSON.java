package cn.mapway.openapi.viewer.client.util.xhr;

import jsinterop.annotations.JsType;

import static jsinterop.annotations.JsPackage.GLOBAL;

@JsType(isNative=true, namespace=GLOBAL)
public class JSON {
	
	public native static String stringify(Object obj);
	
	public native static Object parse(String obj);

}
