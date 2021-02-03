package cn.mapway.openapi.viewer.client.specification;

import com.google.gwt.core.client.JavaScriptObject;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

/**
 * MapObject
 *
 * @author zhangjianshe@gmail.com
 */
public class MapObject<T>  extends JavaScriptObject {
    protected MapObject() {
    }

    public final native T item(String key)/*-{
        if (key != null)
            return this[key];
        return null;
    }-*/;

    /**
     * 所有的内容
     *
     * @return
     */
    public final native String[] keys()/*-{
        var temp = [];
        for (k in this) {
            temp.push(k);
        }
        return temp;
    }-*/;
}
