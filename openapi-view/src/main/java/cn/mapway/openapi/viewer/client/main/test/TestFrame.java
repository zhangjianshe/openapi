package cn.mapway.openapi.viewer.client.main.test;

import cn.mapway.openapi.viewer.client.main.MainFrame;
import cn.mapway.openapi.viewer.client.resource.MainResource;
import cn.mapway.openapi.viewer.client.specification.MediaType;
import cn.mapway.openapi.viewer.client.specification.Operation;
import cn.mapway.openapi.viewer.client.specification.Schema;
import cn.mapway.openapi.viewer.client.util.Https;
import cn.mapway.openapi.viewer.client.util.IOnData;
import cn.mapway.openapi.viewer.client.util.SchemeUtil;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.logical.shared.HasCloseHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;


/**
 * TestFrame
 *
 * @author zhangjianshe@gmail.com
 */
public class TestFrame extends Composite implements HasCloseHandlers<Boolean> {

    private static TestFrameUiBinder ourUiBinder = GWT.create(TestFrameUiBinder.class);
    Operation mOperation;
    @UiField
    Button btnClose;
    @UiField
    Label txtUrl;
    @UiField
    Image imgMethod;
    @UiField
    TextArea editor;
    @UiField
    TextArea result;
    @UiField
    Image imgLoadding;
    private boolean initialize = false;

    public TestFrame() {
        initWidget(ourUiBinder.createAndBindUi(this));
        int width = RootLayoutPanel.get().getOffsetWidth();
        int height = RootLayoutPanel.get().getOffsetHeight();

        this.setHeight(((height - 100) / 2) + "px");
        this.setWidth(((width - 100) / 2) + "px");
        imgLoadding.setUrl(MainResource.INSTANCE.loading().getSafeUri());
    }

    public TestFrame parse(Operation operation) {
        mOperation = operation;
        fillCaption();
        return this;
    }

    private void fillCaption() {
        if ("g".equals(mOperation.opType)) {
            imgMethod.setResource(MainResource.INSTANCE.g());
            imgMethod.setAltText("GET");
        } else if ("p".equals(mOperation.opType)) {
            imgMethod.setResource(MainResource.INSTANCE.p());
            imgMethod.setAltText("POST");
        } else if ("o".equals(mOperation.opType)) {
            imgMethod.setResource(MainResource.INSTANCE.o());
            imgMethod.setAltText("OPTION");
        } else if ("d".equals(mOperation.opType)) {
            imgMethod.setResource(MainResource.INSTANCE.d());
            imgMethod.setAltText("DELETE");
        } else {
            imgMethod.setResource(MainResource.INSTANCE.n());
            imgMethod.setAltText(mOperation.opType);
        }
        txtUrl.setText(MainFrame.getCurrentServer().url + mOperation.getPath());

        if (mOperation.requestBody != null) {
            MediaType mediaType = mOperation.requestBody.jsonType();
            Schema schema = mediaType.getSchema().resolve();
            JSONValue jsonObject = SchemeUtil.fromSchema(schema);
            editor.setText(SchemeUtil.toJson(jsonObject));
        } else {
            editor.setText("{}");
        }
    }

    @UiHandler("btnClose")
    public void btnCloseClick(ClickEvent event) {
        CloseEvent.fire(this, null, true);
    }

    @UiHandler("btnTest")
    public void btnTestClick(ClickEvent event) {
        //测试代码
        imgLoadding.setVisible(true);
        result.setValue("");

        String path = txtUrl.getText();
        try {
            Https.fetchString(path, "", editor.getValue(), imgMethod.getAltText(),
                    new IOnData<String>() {
                        @Override
                        public void onError(String url, String error) {
                            result.setValue(error);
                            imgLoadding.setVisible(false);
                        }

                        @Override
                        public void onSuccess(String url, String data) {
                            JavaScriptObject obj = JsonUtils.safeEval(data);
                            result.setValue(JsonUtils.stringify(obj, "\t"));
                            imgLoadding.setVisible(false);
                        }
                    });
        } catch (RequestException e) {
            result.setValue(e.getMessage());
            imgLoadding.setVisible(false);
        }
    }

    @Override
    public HandlerRegistration addCloseHandler(CloseHandler<Boolean> handler) {
        return addHandler(handler, CloseEvent.getType());
    }

    @Override
    protected void onLoad() {
        super.onLoad();
        initJsonEditor();
        int width = Window.getClientWidth() - 200;
        int height = Window.getClientHeight() - 200;
        this.setHeight(height + "px");
        this.setWidth(width + "px");

    }

    private void initJsonEditor() {
        if (initialize == false) {
//            editor.startEditor();
//            editor.setMode(AceEditorMode.JSON);
//            editor.setShowPrintMargin(false);
//            editor.setFontSize(16);
//
//            result.startEditor();
//            result.setMode(AceEditorMode.JSON);
//            result.setShowPrintMargin(false);
//            result.setFontSize(16);
//            result.setReadOnly(true);

            initialize = true;
        }
    }

    interface TestFrameUiBinder extends UiBinder<DockLayoutPanel, TestFrame> {
    }
}