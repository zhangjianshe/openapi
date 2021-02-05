package cn.mapway.openapi.viewer.client.util.xhr;

import jsinterop.annotations.JsMethod;
import jsinterop.annotations.JsType;

import static jsinterop.annotations.JsPackage.GLOBAL;

@JsType(isNative=true, namespace = GLOBAL)
public class Node extends EventTarget{
	
	@JsMethod
	public native void appendChild(Object child);
	
	@JsMethod
	public native void removeChild(Object child);

}
