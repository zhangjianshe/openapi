package cn.mapway.openapi.viewer.client.specification;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

@JsType(isNative = true,namespace = JsPackage.GLOBAL,name = "Object")
public class OpenApiDoc {
    @JsProperty
    public String openapi;

    @JsProperty
    public Info info;



    @JsProperty
    public Server[] servers;

    @JsProperty
    public MapObject<PathItem> paths;

    @JsProperty
    public Components components;
}
