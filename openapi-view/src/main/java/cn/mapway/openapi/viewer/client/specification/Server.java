package cn.mapway.openapi.viewer.client.specification;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL,name = "Object")
public class Server {

    @JsProperty
    public String url;
    @JsProperty
    public String description;

    @JsProperty
    private MapObject<ServerVariable> variables;

}
