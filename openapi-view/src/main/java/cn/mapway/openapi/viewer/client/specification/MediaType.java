package cn.mapway.openapi.viewer.client.specification;

import cn.mapway.openapi.viewer.client.main.MainFrame;
import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

/**
 * MediaType
 *
 * @author zhangjianshe@gmail.com
 */
@JsType(isNative = true, namespace = JsPackage.GLOBAL, name = "Object")
public class MediaType {

    @JsProperty
    private Schema schema;

    @JsProperty
    public String example;


    @JsOverlay
    public final Schema getSchema() {
        if (schema == null) return null;

        if (schema.$ref != null && schema.$ref.length() > 0) {
            //寻找Schema
            String prefix = "#/components/schemas/";
            if (schema.$ref.startsWith(prefix)) {
                String key = schema.$ref.substring(prefix.length());
                return MainFrame.mDoc.components.schemas.item(key);
            }
            return this.schema;
        }
        return this.schema;
    }
}
