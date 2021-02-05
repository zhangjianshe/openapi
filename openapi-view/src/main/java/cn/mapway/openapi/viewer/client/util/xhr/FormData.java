package cn.mapway.openapi.viewer.client.util.xhr;

import com.google.gwt.core.client.JavaScriptObject;
import jsinterop.annotations.JsMethod;
import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

import static jsinterop.annotations.JsPackage.GLOBAL;

@JsType(isNative = true, namespace = GLOBAL)
public class FormData {


    @JsProperty
    private boolean isClosed;
    @JsProperty
    private int size;
    @JsProperty
    private String type;

    @JsMethod
    public native void append(String key, String value);

    @JsMethod
    public native void set(String key, String value);

    @JsMethod
    public native void append(String key, JavaScriptObject value);

    @JsMethod
    public native void set(String key, JavaScriptObject value);

    @JsMethod
    public native boolean has(String key);

    @JsMethod
    public native boolean remove(String key);


    @JsOverlay
    public final boolean isClosed() {
        return this.isClosed;
    }

    @JsOverlay
    public final int getSize() {
        return this.size;
    }

    @JsOverlay
    public final String getType() {
        return this.type;
    }

    @JsMethod
    public native void close();

    @JsMethod
    public native FormData slice(int start, int end, String contentType);

}
