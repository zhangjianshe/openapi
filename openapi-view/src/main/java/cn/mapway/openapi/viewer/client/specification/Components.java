package cn.mapway.openapi.viewer.client.specification;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

/**
 * Components
 *
 * @author zhangjianshe@gmail.com
 */
@JsType(isNative = true, namespace = JsPackage.GLOBAL,name = "Object")
public class Components {
    @JsProperty
    public MapObject<Schema> schemas;
    @JsProperty
    public MapObject<Parameter> parameters;
    @JsProperty
    public MapObject<RequestBody> requestBodies;
    @JsProperty
    public MapObject<Example> examples;
    @JsProperty
    public MapObject<Response> responses;

}
