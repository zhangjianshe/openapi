package cn.mapway.openapi.viewer.client.specification;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

@JsType(isNative = true,namespace = JsPackage.GLOBAL,name = "Object")
public class PathItem {
    @JsProperty
    public String name;
    @JsProperty
    public String summary;
    @JsProperty
    public String description;
    @JsProperty
    public String $ref;
    @JsProperty
    public Operation get;
    @JsProperty
    public Operation put;
    @JsProperty
    public Operation post;
    @JsProperty
    public Operation delete;
    @JsProperty
    public Operation options;
    @JsProperty
    public Operation head;
    @JsProperty
    public Operation patch;
    @JsProperty
    public Operation trace;

}
