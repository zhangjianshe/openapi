package cn.mapway.openapi.viewer.client.util.xhr;

import jsinterop.annotations.JsFunction;


@JsFunction
@FunctionalInterface
public interface Function {
	
	
	public Object call(Object event);

}
