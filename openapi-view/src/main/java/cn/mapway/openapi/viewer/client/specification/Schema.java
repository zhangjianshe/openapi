package cn.mapway.openapi.viewer.client.specification;

import cn.mapway.openapi.viewer.client.main.MainFrame;
import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

/**
 * Schema
 *
 * @author zhangjianshe@gmail.com
 */
@JsType(isNative = true, namespace = JsPackage.GLOBAL, name = "Object")
public class Schema {


    @JsProperty
    public String title;
    @JsProperty
    public String description;
    @JsProperty
    public String type;
    @JsProperty
    String format;
    @JsProperty
    public String[] required;
    @JsProperty
    public Boolean nullable;
    @JsProperty
    public MapObject<Schema> properties;
    @JsProperty
    public Schema items;
    @JsProperty
    public Boolean readOnly;
    @JsProperty
    public Boolean writeOnly;
    @JsProperty
    public Boolean deprecated;
    @JsProperty
    public String example;

    @JsProperty
    public String $ref;

    @JsOverlay
    public final Schema resolve() {
        if (this.$ref != null && this.$ref.length() > 0) {
            //寻找Schema
            String prefix = "#/components/schemas/";
            if (this.$ref.startsWith(prefix)) {
                String key = this.$ref.substring(prefix.length());
                return MainFrame.mDoc.components.schemas.item(key);
            }
            return this;
        }
        return this;
    }


    /**
     * 如果 数据类型 是 对象数组  或者对象  则 返回对象的Schema
     * 否则返回 null
     *
     * @return
     */
    @JsOverlay
    public final Schema getObjectSchema() {
        if (type.equals("object")) {
            return this;
        } else if (type.equals("array")) {
            Schema itemSchema = items.resolve();

            if (itemSchema.title == null) {
                //简单类型
                return null;
            }
            return itemSchema;
        }
        return null;
    }

    @JsOverlay
    public final String getDataType() {
        if (type.equals("object")) {
            return title;
        } else if (type.equals("array")) {
            Schema itemSchema = items.resolve();
            if (itemSchema.title == null) {
                return ((itemSchema.format != null && itemSchema.format.length() > 0) ? itemSchema.format : itemSchema.type) + " []";
            }
            return itemSchema.title + " []";
        } else {
            return (format != null && format.length() > 0) ? format : type;
        }
    }
}
