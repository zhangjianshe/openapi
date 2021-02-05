package cn.mapway.openapi.viewer.client.util.xhr;

import jsinterop.annotations.JsType;

import static jsinterop.annotations.JsPackage.GLOBAL;

@JsType(isNative=true, namespace=GLOBAL)
public class EventTarget {
	
	
	public native void addEventListener(String type, Function listener);

}
