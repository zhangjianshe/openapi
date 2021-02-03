package cn.mapway.openapi.viewer.client.specification;

import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL, name = "Object")
public abstract class Operation {

    @JsProperty
    public String pinyin;
    /**
     * get post option delete
     */
    @JsProperty
    public String opType;
    @JsProperty
    public String[] tags;

    @JsProperty
    public String summary;

    @JsProperty
    public String description;
    @JsProperty
    public String operationId;

    @JsProperty
    public Boolean deprecated;

    @JsProperty
    public ExternalDoc externalDocs;
    @JsProperty
    public Parameter[] parameters;
    @JsProperty
    public RequestBody requestBody;
    /**
     * 返回值
     */
    @JsProperty
    public MapObject<Response> responses;
    @JsProperty
    private PathItem pathItem;

    @JsOverlay
    public final void setPathItem(PathItem item) {
        this.pathItem = item;
    }

    @JsOverlay
    public final boolean isDeprecated() {
        return deprecated != null && deprecated;
    }

    @JsOverlay
    public final String getPath() {
        return pathItem.name;
    }

    /**
     * 缺省的返回值
     *
     * @return
     */
    @JsOverlay
    public final Response getDefaultResponse() {
        Response response = responses.item("default");
        if (response == null) {
            response = responses.item("200");
            return response;
        }
        return response;
    }
}
