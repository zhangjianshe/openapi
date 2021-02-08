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
import cn.mapway.openapi.viewer.client.util.xhr.FormData;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
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
 * <p>
 * multipart 请求格式  query参数通过 form形式传递
 * json      请求格式  query参数通过 url形式传递
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
    Image imageLoading;
    @UiField
    DockLayoutPanel dockParameter;
    @UiField
    SplitLayoutPanel resultPanel;
    @UiField
    ListBox ddlRequestContentType;
    @UiField
    Button btnBodyVar;
    @UiField
    Button btnQueryVar;
    ParameterListEditor listEditor;
    Widget current;
    private String mUrl;
    private final ChangeHandler requestTypeChanged = new ChangeHandler() {
        @Override
        public void onChange(ChangeEvent event) {
            changeRequestType();
        }
    };
    private boolean initialize = false;
    private ValueChangeHandler<Parameter> parameterValueChanged = new ValueChangeHandler<Parameter>() {
        @Override
        public void onValueChange(ValueChangeEvent<Parameter> event) {
            rerenderUrl();
        }
    };
    private IOnData<String> httpHandler = new IOnData<String>() {
        @Override
        public void onError(String url, DataType dataType, String error) {
            rendData(dataType, error);
        }

        private void rendData(DataType dataType, String error) {
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

            imageLoading.setVisible(false);
        }

        @Override
        public void onSuccess(String url, DataType dataType, String data) {
            rendData(dataType, data);
        }
    };

    public TestFrame() {
        initWidget(ourUiBinder.createAndBindUi(this));
        imageLoading.setUrl(MainResource.INSTANCE.loading().getSafeUri());
        current = editor;
        ddlRequestContentType.addChangeHandler(requestTypeChanged);
    }

    /**
     * 根据路径参数 更新URL地址
     */
    private void rerenderUrl() {
        String templateUrl = txtUrl.getTitle();
        mUrl = txtUrl.getTitle();
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
                    String strv = "<span class='inline-param'>" + v + "</span>";
                    //替换URL中参数值 ${code}  == v
                    String pattern = "{" + p.name + "}";
                    templateUrl = templateUrl.replaceAll(pattern, strv);
                    mUrl = mUrl.replaceAll(pattern, v);

                } else {
                    String strv = "<span class='inline-param-error'>" + p.name + "</span>";
                    //替换URL中参数值 ${code}  == v
                    String pattern = "{" + p.name + "}";
                    templateUrl = templateUrl.replaceAll(pattern, strv);
                    mUrl = mUrl.replaceAll(pattern, v);
                }
            }
            if (ddlRequestContentType.getSelectedIndex() == 0) {
                if (p.in.equals("query")) {
                    if (sb.length() > 0) {
                        sb.append("&");
                    }
                    sb.append(p.name).append("=").append(v);
                }
            }
        }
        if (sb.length() > 0) {
            templateUrl += "?" + sb.toString();
            mUrl += "?" + sb.toString();
        }
        txtUrl.setHTML(templateUrl);
        //处理查询参数
    }

    public TestFrame parse(Operation operation) {
        mOperation = operation;
        fillCaption();
        changeRequestType();
        return this;
    }

    private void changeRequestType() {
        if (ddlRequestContentType.getSelectedIndex() == 0) {
            btnBodyVar.setVisible(true);
            btnBodyVar.click();
        } else {
            btnBodyVar.setVisible(false);
            btnQueryVar.click();
        }
        rerenderUrl();
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
        imageLoading.setVisible(true);
        resultJson.setString("");

        String path = txtUrl.getText();

        // 0 JSON 1 form
        int index = ddlRequestContentType.getSelectedIndex();

        FormData formData = new FormData();

        RequestHead requestHead = new RequestHead();
        if (mOperation.parameters != null) {
            for (int i = 0; i < mOperation.parameters.length; i++) {
                Parameter parameter = mOperation.parameters[i];
                if (parameter.in.equals("header")) {
                    requestHead.put(parameter.name, parameter.getActualValue());
                } else if (parameter.in.equals("query")) {
                    if (index == 0) {
                        // JSON 格式 数据需要添加到URL

                    } else {
                        if (parameter.style != null && parameter.style.equals("binary")) {
                            //文件
                        } else {
                            formData.append(parameter.name, parameter.getActualValue());
                        }
                    }
                }
            }
        }

        requestHead.jsonContent();
        if (index == 0) {
            // JSON
            requestHead.jsonContent();
            Https.send(path, requestHead, editor.getValue(), imgMethod.getAltText(), httpHandler);
        } else {
            requestHead.formContent();
            Https.send(path, requestHead, formData, imgMethod.getAltText(), httpHandler);

        }

    }

    @UiHandler("btnPathVar")
    public void btnPathVarClick(ClickEvent event) {
        sureParameterListEditor().parse(mOperation.parameters, "path");
    }

    @UiHandler("btnHeadVar")
    public void btnHeadVarClick(ClickEvent event) {
        sureParameterListEditor().parse(mOperation.parameters, "header");
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