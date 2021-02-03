package cn.mapway.openapi.viewer.client.specification;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL,name = "Object")
public abstract class ServerVariable {
    @JsProperty
    public String description;

    @JsProperty
    public abstract String getDefault();

    @JsProperty
    public abstract String[] getEnum();

}
