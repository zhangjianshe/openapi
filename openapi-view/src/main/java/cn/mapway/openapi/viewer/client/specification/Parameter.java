package cn.mapway.openapi.viewer.client.specification;

import cn.mapway.openapi.viewer.client.main.MainFrame;
import com.google.gwt.core.client.JavaScriptObject;
import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

/**
 * Parameter
 *
 * @author zhangjianshe@gmail.com
 */
@JsType(isNative = true, namespace = JsPackage.GLOBAL, name = "Object")
public class Parameter {

    @JsProperty
    public String name;
    @JsProperty
    public String in;
    @JsProperty
    public String description;
    @JsProperty
    public String style;
    @JsProperty
    public JavaScriptObject example;
    @JsProperty
    public MapObject<Example> examples;
    /**
     * 自定义的参数 用于保存用户输入的数值
     */
    @JsProperty
    public String pvalue;
    @JsProperty
    public Boolean required;
    @JsProperty
    public Boolean explode;
    /**
     * schema  和 content 互斥
     */
    @JsProperty
    private Schema schema;
    @JsProperty
    private MapObject<MediaType> content;
    @JsProperty
    private String $ref;

    /**
     * 类型已经resolve过
     *
     * @return
     */
    @JsOverlay
    public final Schema getSchema() {
        if (schema != null) {
            return schema.resolve();
        }
        return null;
    }

    @JsOverlay
    public final String getActualValue() {
        return pvalue;
    }

    @JsOverlay
    public final Boolean isRequired() {
        return required == null || required == true;
    }

    @JsOverlay
    public final Parameter resolve() {
        if (this.$ref != null && this.$ref.length() > 0) {
            //寻找Schema
            String prefix = "#/components/parameters/";
            if (this.$ref.startsWith(prefix)) {
                String key = this.$ref.substring(prefix.length());
                return MainFrame.mDoc.components.parameters.item(key);
            }
            return this;
        }
        return this;
    }

    @JsOverlay
    public final String getType() {
        if (schema == null) {
            return "";
        }
        String type = schema.resolve().type;

        if (explode != null && explode) {
            return type + "[]";
        }
        return type;
    }
}
