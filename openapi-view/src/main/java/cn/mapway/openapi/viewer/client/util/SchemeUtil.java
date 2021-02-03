package cn.mapway.openapi.viewer.client.util;

import cn.mapway.openapi.viewer.client.specification.Schema;
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
}
