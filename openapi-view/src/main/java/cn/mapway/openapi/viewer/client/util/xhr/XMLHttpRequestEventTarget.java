package cn.mapway.openapi.viewer.client.util.xhr;

import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

import static jsinterop.annotations.JsPackage.GLOBAL;

@JsType(isNative=true, namespace=GLOBAL)
public class XMLHttpRequestEventTarget extends EventTarget{
	
	
	@JsProperty
	public Function onabort;
	
	@JsProperty
	public Function onerror;
	
	@JsProperty
	public Function onload;
	
	@JsProperty
	public Function onloadstart;
	
	@JsProperty
	public Function onprogress;
	
	@JsProperty
	public Function ontimeout;
	
	@JsProperty
	public Function onloadend;

}
