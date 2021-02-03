package cn.mapway.openapi.viewer.client.specification;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL,name = "Object")

public class ExternalDoc {
    @JsProperty
    public String description;

    @JsProperty
    public String url;

}
