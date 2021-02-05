package cn.mapway.openapi.viewer.client.util.xhr;

import jsinterop.annotations.JsFunction;

@JsFunction
@FunctionalInterface
public interface PromiseExecutor {
	
	public void executor(Function resolve, Function reject);

}
