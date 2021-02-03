package cn.mapway.openapi.viewer.client.util;

import com.google.gwt.http.client.*;

/**
 * http get post data
 */
public class Https {
    public static void fetchString(final String url, String contextType,
                                   String method, final IOnData<String> handler) throws RequestException {

        RequestBuilder builder =
                new RequestBuilder(method.equalsIgnoreCase("post") ? RequestBuilder.POST
                        : RequestBuilder.GET, URL.encode(url));
        if (contextType == null || contextType.length() == 0) {
            contextType = "application/json;charset=UTF-8";
        }
        builder.setHeader("Content-type", contextType);


        Request request = builder.sendRequest("", new RequestCallback() {
            @Override
            public void onError(Request request, Throwable exception) {
                handler.onError(url, exception.getMessage());
            }

            @Override
            public void onResponseReceived(Request request, Response response) {
                if (200 == response.getStatusCode()) {
                    String data = response.getText();
                    handler.onSuccess(url, data);
                } else {
                    handler.onError(url, response.getStatusText());
                }
            }
        });
    }
}
