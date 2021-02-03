package cn.mapway.openapi.viewer.client.specification;

import cn.mapway.openapi.viewer.client.main.MainFrame;
import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

/**
 * Response
 *
 * @author zhangjianshe@gmail.com
 */
@JsType(isNative = true, namespace = JsPackage.GLOBAL, name = "Object")
public class Response {
    @JsProperty
    private String $ref;

    @JsProperty
    public String description;

    @JsProperty
    public MapObject<Header> headers;


    @JsProperty
    public MapObject<MediaType> content;

    /**
     * JSON 数据格式
     *
     * @return
     */
    @JsOverlay
    public final MediaType jsonMediaType() {
        MediaType mediaType = content.item("application/json");

        if (mediaType == null) {
            return content.item("*/*");
        }
        return mediaType;
    }

    @JsProperty
    public MapObject<Link> links;


    @JsOverlay
    public final Response resolve() {
        if (this.$ref != null && this.$ref.length() > 0) {
            //寻找Schema
            String prefix = "#/components/responses/";
            if (this.$ref.startsWith(prefix)) {
                String key = this.$ref.substring(prefix.length());
                return MainFrame.mDoc.components.responses.item(key);
            }
            return this;
        }
        return this;
    }
}
