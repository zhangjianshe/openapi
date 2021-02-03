package cn.mapway.openapi.viewer.client.component;

import cn.mapway.openapi.viewer.client.resource.MainCss;
import cn.mapway.openapi.viewer.client.resource.MainResource;
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
    }

    private final native static String format(JavaScriptObject json)/*-{
        var reg = null,
            formatted = '',
            pad = 0,
            PADDING = '    ';

        // optional settings
        var options = options || {};
        // remove newline where '{' or '[' follows ':'
        options.newlineAfterColonIfBeforeBraceOrBracket = (options.newlineAfterColonIfBeforeBraceOrBracket === true) ? true : false;
        // use a space after a colon
        options.spaceAfterColon = (options.spaceAfterColon === false) ? false : true;

        // begin formatting...
        if (typeof json !== 'string') {
            // make sure we start with the JSON as a string
            json = JSON.stringify(json);
        } else {
            // is already a string, so parse and re-stringify in order to remove extra whitespace
            json = JSON.parse(json);
            json = JSON.stringify(json);
        }

        // add newline before and after curly braces
        reg = /([\{\}])/g;
        json = json.replace(reg, '\r\n$1\r\n');

        // add newline before and after square brackets
        reg = /([\[\]])/g;
        json = json.replace(reg, '\r\n$1\r\n');

        // add newline after comma
        reg = /(\,)/g;
        json = json.replace(reg, '$1\r\n');

        // remove multiple newlines
        reg = /(\r\n\r\n)/g;
        json = json.replace(reg, '\r\n');

        // remove newlines before commas
        reg = /\r\n\,/g;
        json = json.replace(reg, ',');

        // optional formatting...
        if (!options.newlineAfterColonIfBeforeBraceOrBracket) {
            reg = /\:\r\n\{/g;
            json = json.replace(reg, ':{');
            reg = /\:\r\n\[/g;
            json = json.replace(reg, ':[');
        }
        if (options.spaceAfterColon) {
            reg = /\:/g;
            json = json.replace(reg, ':');
        }

        var items = json.split('\r\n');
        for (var index = 0; index < items.length; index++) {
            var node = items[index];
            var i = 0,
                indent = 0,
                padding = '';

            if (node.match(/\{$/) || node.match(/\[$/)) {
                indent = 1;
            } else if (node.match(/\}/) || node.match(/\]/)) {
                if (pad !== 0) {
                    pad -= 1;
                }
            } else {
                indent = 0;
            }

            for (i = 0; i < pad; i++) {
                padding += PADDING;
            }

            formatted += padding + node + '\r\n';
            pad += indent;
        }

        return formatted;
    }-*/;

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

        String json = format(scriptObject);
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
