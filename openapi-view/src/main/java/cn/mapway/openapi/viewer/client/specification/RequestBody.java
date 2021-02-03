package cn.mapway.openapi.viewer.client.specification;

import cn.mapway.openapi.viewer.client.main.MainFrame;
import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

/**
 * RequestBody
 *
 * @author zhangjianshe@gmail.com
 */
@JsType(isNative = true, namespace = JsPackage.GLOBAL, name = "Object")
public class RequestBody {
    @JsProperty
    public String description;

    @JsProperty
    public Boolean required;

    @JsProperty
    public MapObject<MediaType> content;

    @JsProperty
    private String $ref;
    @JsOverlay
    public final RequestBody resolve()
    {
        if (this.$ref != null && this.$ref.length() > 0) {
            //寻找Schema
            String prefix = "#/components/requests/";
            if (this.$ref.startsWith(prefix)) {
                String key = this.$ref.substring(prefix.length());
                return MainFrame.mDoc.components.requestBodies.item(key);
            }
            return this;
        }
        return this;
    }
    /**
     * 返回JSON格式数据
     *
     * @return
     */
    @JsOverlay
    public final MediaType jsonType() {
        return content != null ? content.item("application/json") : null;
    }
}
