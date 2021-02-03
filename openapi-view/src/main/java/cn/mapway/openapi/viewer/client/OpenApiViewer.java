package cn.mapway.openapi.viewer.client;

import cn.mapway.openapi.viewer.client.main.MainFrame;
import cn.mapway.openapi.viewer.client.resource.MainResource;
import cn.mapway.openapi.viewer.client.util.ChineseUtil;
import cn.mapway.openapi.viewer.client.util.Https;
import cn.mapway.openapi.viewer.client.util.IOnData;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.RootPanel;

public class OpenApiViewer implements EntryPoint {
    public void onModuleLoad() {


        MainResource.INSTANCE.css().ensureInjected();

        ChineseUtil.init();
        MainFrame mainFrame = new MainFrame();
        RootPanel.getBodyElement().addClassName(MainResource.INSTANCE.css().body());
        RootLayoutPanel root = RootLayoutPanel.get();

        root.add(mainFrame);

        try {
            Https.fetchString("http://localhost:8080/v3/api-docs", "", "get", new IOnData<String>() {
                @Override
                public void onError(String url, String error) {
                    GWT.log(url + " " + error);
                }

                @Override
                public void onSuccess(String url, String data) {
                    mainFrame.parseData(data);
                }
            });
        } catch (RequestException e) {
            e.printStackTrace();
            GWT.log(e.getMessage());
        }

    }
}
