package cn.mapway.openapi.viewer.client.util;

import cn.mapway.openapi.viewer.client.util.xhr.DataType;
import cn.mapway.openapi.viewer.client.util.xhr.FormData;
import cn.mapway.openapi.viewer.client.util.xhr.Function;
import cn.mapway.openapi.viewer.client.util.xhr.XMLHttpRequest;
import com.google.gwt.core.client.JavaScriptException;
import com.google.gwt.http.client.*;

import java.util.Map;

/**
 * http get post data
 */
public class Https {
    public static void fetchString(final String url, String contextType, String data,
                                   String method, final IOnData<String> handler) throws RequestException {

        RequestBuilder builder =
                new RequestBuilder(method.equalsIgnoreCase("post") ? RequestBuilder.POST
                        : RequestBuilder.GET, URL.encode(url));
        if (contextType == null || contextType.length() == 0) {
            contextType = "application/json;charset=UTF-8";
        }
        builder.setHeader("Content-type", contextType);


        Request request = builder.sendRequest(data, new RequestCallback() {
            @Override
            public void onError(Request request, Throwable exception) {
                handler.onError(url, DataType.DATA_TYPE_TEXT, exception.getMessage());
            }

            @Override
            public void onResponseReceived(Request request, Response response) {
                if (200 == response.getStatusCode()) {
                    String data = response.getText();
                    handler.onSuccess(url, DataType.DATA_TYPE_JSON, data);
                } else {
                    handler.onError(url, DataType.DATA_TYPE_TEXT, response.getText());
                }
            }
        });
    }

    /**
     * invoke http
     *
     * @param url
     * @param header
     * @param data
     * @param method
     * @param handler
     * @throws RequestException
     */
    public static void send(final String url, RequestHead header, Object data,
                            String method, final IOnData<String> handler) {

        XMLHttpRequest request = new XMLHttpRequest();
        request.open(method, url, true, "", "");

        request.onreadystatechange = new Function() {
            @Override
            public Object call(Object event) {

                if (request.readyState == 4) {

                }
                DataType dataType = parseDataType(request.getResponseHeader("Content-Type"));

                if (request.status != 200) {
                    handler.onError(url, dataType, request.responseText);
                } else {
                    handler.onSuccess(url, dataType, request.response);
                }
                return "";
            }
        };

        for (Map.Entry<String, String> item :
                header.entrySet()) {
            request.setRequestHeader(item.getKey(), item.getValue());
        }
        try {
            if (data instanceof FormData) {
                request.send((FormData) data);
            } else {
                request.send((String) data);
            }
        } catch (Exception e) {
            handler.onError(url, DataType.DATA_TYPE_TEXT, e.getMessage());
        }

    }

    /**
     * text------------文本类型
     * application-- 应用类型
     * *----------------所有类型
     * <p>
     * html-----------html格式
     * xml -----------xml格式
     * json-----------json格式
     * *---------------所有格式
     *
     * @param contentType
     * @return
     */
    public static DataType parseDataType(String contentType) {
        if (contentType == null || contentType.length() == 0) {
            return DataType.DATA_TYPE_TEXT;
        }
        //只取第一个媒体类型
        String[] types = contentType.split(";");
        if (types.length > 0) {
            String type = types[0];
            if (type.endsWith("html")) {
                return DataType.DATA_TYPE_HTML;
            } else if (type.endsWith("xml")) {
                return DataType.DATA_TYPE_XML;
            } else if (type.endsWith("json")) {
                return DataType.DATA_TYPE_JSON;
            }
            return DataType.DATA_TYPE_TEXT;
        }
        return DataType.DATA_TYPE_TEXT;
    }
}
