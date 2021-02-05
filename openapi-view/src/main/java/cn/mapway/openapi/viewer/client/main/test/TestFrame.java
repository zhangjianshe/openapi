package cn.mapway.openapi.viewer.client.main.test;

import cn.mapway.openapi.viewer.client.component.JsonPanel;
import cn.mapway.openapi.viewer.client.component.ace.AceEditor;
import cn.mapway.openapi.viewer.client.component.ace.AceEditorMode;
import cn.mapway.openapi.viewer.client.main.MainFrame;
import cn.mapway.openapi.viewer.client.main.test.param.ParameterListEditor;
import cn.mapway.openapi.viewer.client.resource.MainResource;
import cn.mapway.openapi.viewer.client.specification.*;
import cn.mapway.openapi.viewer.client.util.Https;
import cn.mapway.openapi.viewer.client.util.IOnData;
import cn.mapway.openapi.viewer.client.util.RequestHead;
import cn.mapway.openapi.viewer.client.util.SchemeUtil;
import cn.mapway.openapi.viewer.client.util.xhr.DataType;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.*;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.json.client.JSONString;
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
    HTML txtUrl;
    @UiField
    Image imgMethod;
    @UiField
    AceEditor editor;
    @UiField
    JsonPanel resultJson;
    @UiField
    Image imgLoadding;
    @UiField
    DockLayoutPanel dockParameter;
    @UiField
    SplitLayoutPanel resultPanel;
    ParameterListEditor listEditor;
    Widget current;
    private boolean initialize = false;
    private ValueChangeHandler<Parameter> parameterValueChanged = new ValueChangeHandler<Parameter>() {
        @Override
        public void onValueChange(ValueChangeEvent<Parameter> event) {
            rerenderUrl();
        }
    };

    public TestFrame() {
        initWidget(ourUiBinder.createAndBindUi(this));
        imgLoadding.setUrl(MainResource.INSTANCE.loading().getSafeUri());
        current = editor;
    }

    /**
     * 根据路径参数 更新URL地址
     */
    private void rerenderUrl() {
        String templateUrl = txtUrl.getTitle();

        if (mOperation.parameters == null) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (Parameter p : mOperation.parameters) {
            String v = "";
            if (p.pvalue == null) {
                if (p.example != null) {
                    v = p.example.toString();
                }
            } else {
                v = p.pvalue;
            }
            if (p.in.equals("path")) {
                if (v != null && v.length() > 0) {
                    v = "<span class='inline-param'>" + v + "</span>";
                    //替换URL中参数值 ${code}  == v
                    String pattern = "{" + p.name + "}";
                    templateUrl = templateUrl.replaceAll(pattern, v);
                } else {
                    v = "<span class='inline-param-error'>" + p.name + "</span>";
                    //替换URL中参数值 ${code}  == v
                    String pattern = "{" + p.name + "}";
                    templateUrl = templateUrl.replaceAll(pattern, v);
                }
            }
            if (p.in.equals("query")) {
                if (sb.length() > 0) {
                    sb.append("&");
                }
                sb.append(p.name).append("=").append(v);
            }
        }
        if (sb.length() > 0) {
            templateUrl += "?" + sb.toString();
        }
        txtUrl.setHTML(templateUrl);
        //处理查询参数
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
        txtUrl.setTitle(txtUrl.getText());

        sureEditor();
        renderValue(mOperation.requestBody);
    }

    public void renderValue(RequestBody requestBody) {
        if (requestBody != null) {
            MediaType mediaType = requestBody.jsonType();
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
        resultJson.setString("");

        String path = txtUrl.getText();

        RequestHead requestHead = new RequestHead();
        if (mOperation.parameters != null) {
            for (int i = 0; i < mOperation.parameters.length; i++) {
                Parameter parameter = mOperation.parameters[i];
                if (parameter.in.equals("header")) {
                    requestHead.put(parameter.name, parameter.getActualValue());
                }
            }
        }

        requestHead.jsonContent();
        Https.send(path, requestHead, editor.getValue(), imgMethod.getAltText(), new IOnData<String>() {
            @Override
            public void onError(String url, DataType dataType, String error) {
                switch (dataType) {
                    case DATA_TYPE_TEXT:
                        resultJson.setTextString(error);
                        break;
                    case DATA_TYPE_HTML:
                        resultJson.setHTML(error);
                        break;
                    case DATA_TYPE_JSON:
                        resultJson.setJson(new JSONString(error));
                        break;
                    case DATA_TYPE_XML:

                        resultJson.setXML(error);
                    default:
                        resultJson.setTextString(error);
                }

                imgLoadding.setVisible(false);
            }

            @Override
            public void onSuccess(String url, DataType dataType, String data) {
                switch (dataType) {
                    case DATA_TYPE_TEXT:
                        resultJson.setTextString(data);
                        break;
                    case DATA_TYPE_HTML:
                        resultJson.setHTML(data);
                        break;
                    case DATA_TYPE_JSON:
                        resultJson.setJsonString(data);
                        break;
                    case DATA_TYPE_XML:

                        resultJson.setXML(data);
                    default:
                        resultJson.setTextString(data);
                }
                imgLoadding.setVisible(false);
            }
        });


    }


    @UiHandler("btnPathVar")
    public void btnPathVarClick(ClickEvent event) {
        sureParameterListEditor().parse(mOperation.parameters, "path");
    }

    @UiHandler("btnHeadVar")
    public void btnHeadVarClick(ClickEvent event) {
        sureParameterListEditor().parse(mOperation.parameters, "header");
    }

    @UiHandler("btnFormVar")
    public void btnFormVarClick(ClickEvent event) {
        sureParameterListEditor().parse(mOperation.parameters, "form");
    }

    @UiHandler("btnBodyVar")
    public void btnBodyVarClick(ClickEvent event) {
        sureEditor();
        renderValue(mOperation.requestBody);
    }

    @UiHandler("btnQueryVar")
    public void btnQueryVarClick(ClickEvent event) {
        sureParameterListEditor().parse(mOperation.parameters, "query");
    }

    private AceEditor sureEditor() {
        if (editor == current) {
            return editor;
        }
        if (current != null) {
            dockParameter.remove(current);
        }
        dockParameter.add(editor);
        current = editor;
        return editor;
    }


    private ParameterListEditor sureParameterListEditor() {

        if (listEditor == null) {
            listEditor = new ParameterListEditor();
            listEditor.addValueChangeHandler(parameterValueChanged);
        }
        if (listEditor != current) {
            if (current != null) {
                dockParameter.remove(current);
            }
            dockParameter.add(listEditor);
            current = listEditor;
        }
        return listEditor;
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
            editor.startEditor();
            editor.setMode(AceEditorMode.JSON);
            editor.setShowPrintMargin(false);
            editor.setFontSize(16);

            initialize = true;
        }
    }

    interface TestFrameUiBinder extends UiBinder<DockLayoutPanel, TestFrame> {
    }
}