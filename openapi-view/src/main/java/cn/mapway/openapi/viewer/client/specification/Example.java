package cn.mapway.openapi.viewer.client.specification;
/**
 *  Example
 *  @author zhangjianshe@gmail.com
 *
 */
import com.google.gwt.core.client.JavaScriptObject;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;
@JsType(isNative = true, namespace = JsPackage.GLOBAL,name = "Object")
public class Example {
    @JsProperty
    public String summary;
    @JsProperty
    public String description;

    @JsProperty
    JavaScriptObject value;
    @JsProperty
    String  externalValue;

}
