package cn.mapway.openapi.viewer.client.util.xhr;

import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

import static jsinterop.annotations.JsPackage.GLOBAL;

@JsType(isNative=true, name = "document", namespace = GLOBAL)
public class Document extends Node {
	
	
	
	public static native HTMLElement getElementById(String id);
	
	 public static native HTMLElement createElement(String tag);
	 
	 @JsProperty
	 public static native HTMLElement getHead();
	 
	 @JsProperty
	 public static native HTMLElement getBody();

}
