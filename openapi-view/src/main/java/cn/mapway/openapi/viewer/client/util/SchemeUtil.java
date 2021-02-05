package cn.mapway.openapi.viewer.client.util;

import cn.mapway.openapi.viewer.client.specification.Schema;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.*;

import java.util.ArrayList;

/**
 * SchemeUtil
 * 根据Schema构造例子 JSON 数据
 *
 * @author zhangjianshe@gmail.com
 */
public class SchemeUtil {

    /**
     * 根据Schema
     *
     * @param schema
     * @return
     */
    public final static JSONValue fromSchema(Schema schema) {
        JSONValue object = new JSONObject();
        Schema s = schema.resolve();
        if ("object".equals(s.type)) {
            String[] keys = s.properties.keys();
            for (int i = 0; i < keys.length; i++) {
                Schema fieldSchema = s.properties.item(keys[i]);
                object.isObject().put(keys[i], fromSchema(fieldSchema));
            }
        } else if ("array".equals(s.type)) {
            JSONValue array = new JSONArray();
            parseArrayItem(array, s.items, s.example);
            object = array;

        } else if ("string".equals(s.type)) {
            String v = "";

            if (s.example != null && !"".equals(s.example)) {
                v = s.example;
            } else {
                if ("byte".equals(s.format)) {
                    v = "BASE64String";
                } else if ("binary".equals(s.format)) {
                    v = "0011011001";
                } else if ("date".equals(s.format)) {
                    v = "2021-02-10";
                } else if ("date-time".equals(s.format)) {
                    v = "2021-02-10 03:41:12";
                } else if ("password".equals(s.format)) {
                    v = "password";
                }
            }
            object = new JSONString(v);
        } else if ("number".equals(s.type)) {
            Double v = 0.0;
            if (!"".equals(s.type)) {
                v = Double.parseDouble(s.example);
            }
            object = new JSONNumber(v);
        } else if ("boolean".equals(s.type)) {
            boolean v = true;
            if (!"".equals(s.type)) {
                v = Boolean.parseBoolean(s.example);
            }
            object = JSONBoolean.getInstance(v);

        } else if ("integer".equals(s.type)) {
            int v = 0;
            if (!"".equals(s.example)) {
                v = Integer.parseInt(s.example);
            }
            object = new JSONNumber(v);
        }
        return object;
    }

    private static void parseArrayItem(JSONValue array, Schema schema, String example) {
        Schema s = schema.resolve();
        JSONValue obj = fromSchema(s);
        if (example != null && !"".equals(example)) {
            example = example.trim();
            if (example.startsWith("[")) {
                JSONArray array1 = JSONParser.parse(example).isArray();
                if (array1 != null) {
                    for (int i = 0; i < array1.size(); i++) {
                        array.isArray().set(i, array1.get(i));
                    }
                }
            } else {
                //将例子分解为LIST
                String[] items = example.split(",");
                ArrayList<String> datas = new ArrayList<String>();

                for (int i = 0; i < items.length; i++) {
                    String item = items[i];
                    item = item.trim();
                    if (item.length() > 0) {
                        datas.add(item);
                    }
                }

                if (s.type.equals("string")) {
                    int index = 0;
                    for (String v : datas) {
                        array.isArray().set(index++, new JSONString(v));
                    }
                } else if (s.type.equals("number")) {
                    int index = 0;
                    for (String v : datas) {
                        array.isArray().set(index++, new JSONNumber(Double.parseDouble(v)));
                    }
                } else if (s.type.equals("boolean")) {
                    int index = 0;
                    for (String v : datas) {
                        array.isArray().set(index++, JSONBoolean.getInstance(Boolean.parseBoolean(v)));
                    }
                } else if (s.type.equals("integer")) {
                    int index = 0;
                    for (String v : datas) {
                        array.isArray().set(index++, new JSONNumber(Integer.parseInt(v)));
                    }
                }
            }
        } else {
            array.isArray().set(0, obj);
        }
    }

    public static String toJson(JSONValue jsonObject) {
        JavaScriptObject scriptObject = JavaScriptObject.createObject();
        if (jsonObject.isArray() != null) {
            scriptObject = jsonObject.isArray().getJavaScriptObject();
        } else if (jsonObject.isObject() != null) {
            scriptObject = jsonObject.isObject().getJavaScriptObject();
        } else if (jsonObject.isString() != null) {
            return jsonObject.isString().stringValue();
        } else if (jsonObject.isNumber() != null) {
            return jsonObject.isNumber().toString();
        } else if (jsonObject.isNull() != null) {
            return "null";
        } else if (jsonObject.isBoolean() != null) {
            return jsonObject.isBoolean().booleanValue() + "";
        }
        String json = format(scriptObject);
        return json;
    }

    public final static native JavaScriptObject castTo(String data)/*-{
        return data;
    }-*/;

    public final static String format(String json) {
        return format(castTo(json));
    }

    public final native static String format(JavaScriptObject json)/*-{
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

}
