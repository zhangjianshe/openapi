package cn.mapway.openapi.viewer.client.specification;

import cn.mapway.openapi.viewer.client.main.MainFrame;
import com.google.gwt.core.client.JavaScriptObject;
import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

import java.util.Map;

/**
 * Parameter
 *
 * @author zhangjianshe@gmail.com
 */
@JsType(isNative = true, namespace = JsPackage.GLOBAL,name = "Object")
public class Parameter {
    @JsProperty
    public String name;
    @JsProperty
    public String in;
    @JsProperty
    public String description;
    @JsProperty
    private Boolean required;

    @JsProperty
    private Boolean explode;

    @JsOverlay
    public final Boolean isRequired()
    {
        return required==null || required==true;
    }
    @JsProperty
    public String style;

    /**
     * schema  和 content 互斥
     */
    @JsProperty
    private Schema schema;

    @JsProperty
    private MapObject<MediaType> content;

    @JsProperty
    public JavaScriptObject example;

    @JsProperty
    public MapObject<Example> examples;


    @JsProperty
    private String $ref;

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
    public final String getType()
    {
        if(schema==null)
        {
            return "";
        }
        String type= schema.resolve().type;

        if(explode!=null && explode)
        {
            return type+"[]";
        }
        return type;
    }
}