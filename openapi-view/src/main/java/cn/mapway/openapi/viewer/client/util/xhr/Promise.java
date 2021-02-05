package cn.mapway.openapi.viewer.client.util.xhr;

import jsinterop.annotations.JsConstructor;
import jsinterop.annotations.JsMethod;
import jsinterop.annotations.JsType;

import static jsinterop.annotations.JsPackage.GLOBAL;

@JsType(isNative=true, namespace=GLOBAL)
public class Promise {
	
	
	@JsConstructor
	public Promise(PromiseExecutor executor){
		
	}
	//catch is a keyword in java
	 @JsMethod(name = "catch")
	public native Promise onException(Function reject);
	
	public native Promise then(Function resolve);

}
