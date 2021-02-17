package cn.mapway.openapi.viewer.client.util;

import java.util.HashMap;

/**
 * RequestHead
 *
 * @author zhangjianshe@gmail.com
 */
public class RequestHead extends HashMap<String, String> {
    public RequestHead jsonContent() {
        put("Content-Type", "application/json;charset=UTF-8");
        return this;
    }

    public RequestHead formContent() {
      //  put("Content-Type", "multipart/form-data");
        return this;
    }
}
