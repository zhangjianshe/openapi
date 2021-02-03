package cn.mapway.openapi.viewer.client.specification;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL, name = "Object")
public class Info {
    @JsProperty
    public String title;
    @JsProperty
    public String description;
    @JsProperty
    public String termsOfService;
    @JsProperty
    public String version;
    @JsProperty
    public Contact contact;

    @JsProperty
    public License license;
}
