package cn.mapway.openapi.viewer.client.util.xhr;

import jsinterop.annotations.JsMethod;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

import static jsinterop.annotations.JsPackage.GLOBAL;

@JsType(isNative=true, namespace = GLOBAL)
public class Element extends Node{
	
	@JsProperty
   public String innerHTML;
	
	@JsProperty
	public String outerHTML;
	
	@JsProperty
	public String id;
	
	@JsProperty
	public Function onload;
	
	@JsMethod
	public native void setAttribute(String attribute, String value);
	
	
	
	
	
	
}
