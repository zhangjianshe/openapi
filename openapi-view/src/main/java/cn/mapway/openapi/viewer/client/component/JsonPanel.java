package cn.mapway.openapi.viewer.client.component;

import cn.mapway.openapi.viewer.client.resource.MainCss;
import cn.mapway.openapi.viewer.client.resource.MainResource;
import cn.mapway.openapi.viewer.client.util.SchemeUtil;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RequiresResize;


/**
 * The Class JsonPanel.
 */
public class JsonPanel extends HTML implements RequiresResize {

    /**
     * Instantiates a new json panel.
     */
    public JsonPanel() {
        setStyleName("html-panel");
    }

    /**
     * Sets the json.
     *
     * @param jsonString the new json
     */
    public void setJsonString(String jsonString) {
        String json = SchemeUtil.format(jsonString);
        MainCss css = MainResource.INSTANCE.css();
        String html = highlighted(json, css.cssNumber(), "variable",
                css.cssString(), css.cssBoolean(), css.cssNull());
        this.setHTML("<pre class='" + css.pre() + "'>" + html + "</pre>");
    }

    /**
     * Sets the json.
     *
     * @param jsonObject the new json
     */
    public void setJson(JSONValue jsonObject) {
        JavaScriptObject scriptObject = JavaScriptObject.createObject();
        if (jsonObject.isArray() != null) {
            scriptObject = jsonObject.isArray().getJavaScriptObject();
        } else if (jsonObject.isObject() != null) {
            scriptObject = jsonObject.isObject().getJavaScriptObject();
        } else if (jsonObject.isString() != null) {
            this.setString(jsonObject.isString().stringValue());
            return;
        } else if (jsonObject.isNumber() != null) {
            this.setString(jsonObject.isNumber().toString());
            return;
        } else if (jsonObject.isNull() != null) {
            this.setString("null");
            return;
        } else if (jsonObject.isBoolean() != null) {
            this.setString(jsonObject.isBoolean().booleanValue() + "");
            return;
        }

        String json = SchemeUtil.format(scriptObject);
        MainCss css = MainResource.INSTANCE.css();
        String html = highlighted(json, css.cssNumber(), "variable",
                css.cssString(), css.cssBoolean(), css.cssNull());
        this.setHTML("<pre class='" + css.pre() + "'>" + html + "</pre>");
    }

    /**
     * Sets the string.
     *
     * @param text the new string
     */
    public void setTextString(String text) {

        this.setHTML("<div>" + text + "</div>");
    }

    /**
     * Sets the string.
     *
     * @param xml the new string
     */
    public void setXML(String xml) {

        this.setHTML("<pre>" + xml + "</pre>");
    }

    /**
     * Sets the string.
     *
     * @param text the new string
     */
    public void setString(String text) {
        MainCss css = MainResource.INSTANCE.css();
        this.setHTML("<pre class='" + css.pre() + "'>" + text + "</pre>");
    }

    /**
     * Highted.
     *
     * @param json       the json
     * @param cssNumber  the css number
     * @param cssKey     the css key
     * @param cssString  the css string
     * @param cssBoolean the css boolean
     * @param cssNull    the css null
     * @return the string
     */
    private native String highlighted(String json, String cssNumber, String cssKey,
                                      String cssString, String cssBoolean, String cssNull)/*-{

        json = json.replace(/&/g, '&').replace(/</g, '<').replace(/>/g, '>');
        return json
            .replace(
                /("(\\u[a-zA-Z0-9]{4}|\\[^u]|[^\\"])*"(\s*:)?|\b(true|false|null)\b|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?)/g,
                function (match) {
                    var cls = cssNumber;
                    if (/^"/.test(match)) {
                        if (/:$/.test(match)) {
                            cls = cssKey;
                        } else {
                            cls = cssString;
                        }
                    } else if (/true|false/.test(match)) {
                        cls = cssBoolean;
                    } else if (/null/.test(match)) {
                        cls = cssNull;
                    }
                    return '<span class="' + cls + '">' + match
                        + '</span>';
                });
    }-*/;

    @Override
    public void onResize() {

    }
}
