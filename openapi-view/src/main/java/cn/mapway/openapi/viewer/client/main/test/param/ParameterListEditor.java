package cn.mapway.openapi.viewer.client.main.test.param;

import cn.mapway.openapi.viewer.client.component.FileUploadEx;
import cn.mapway.openapi.viewer.client.component.TextBoxEx;
import cn.mapway.openapi.viewer.client.main.util.TableUtil;
import cn.mapway.openapi.viewer.client.specification.Parameter;
import cn.mapway.openapi.viewer.client.specification.Schema;
import cn.mapway.openapi.viewer.client.util.RequestHead;
import cn.mapway.openapi.viewer.client.util.xhr.FormData;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.*;

import java.util.List;


/**
 * ParameterListEditor
 *
 * @author zhangjianshe@gmail.com
 */
public class ParameterListEditor extends Composite implements HasValueChangeHandlers<Para> {
    private static ParameterListEditorUiBinder ourUiBinder = GWT.create(ParameterListEditorUiBinder.class);
    @UiField
    FlexTable tableParameters;
    List<Para> mParaList;
    private ValueChangeHandler<String> valueChangedhandler = new ValueChangeHandler<String>() {
        @Override
        public void onValueChange(ValueChangeEvent<String> event) {
            TextBoxEx box = (TextBoxEx) event.getSource();
            if (box != null) {
                Para parameter = box.getData();
                parameter.getParameter().pvalue = box.getValue();
                ValueChangeEvent.fire(ParameterListEditor.this, parameter);
            }
        }
    };
    private boolean mInitialize = false;

    public ParameterListEditor() {
        initWidget(ourUiBinder.createAndBindUi(this));
    }

    private void parameterToFlexTable(List<Para> parameters, String type) {

        int row = 0;
        int col = 0;

        tableParameters.removeAllRows();
        if (parameters == null || parameters.size() == 0) {
            tableParameters.setWidget(0, 0, TableUtil.format("不需要" + type
                    + "参数", "col-desc"));
            tableParameters.getCellFormatter().setAlignment(0, 0, HasHorizontalAlignment.ALIGN_CENTER, HasVerticalAlignment.ALIGN_MIDDLE);
            return;
        }

        tableParameters.setText(row, col++, "名称");
        tableParameters.setText(row, col++, "数据类型");
        tableParameters.setText(row, col++, "数值");
        tableParameters.setText(row, col++, "说明");
        row++;

        renderCatalogParameter(row, tableParameters, parameters);

    }

    private int renderCatalogParameter(int row, FlexTable table, List<Para> parameters) {
        if (parameters.size() == 0) {
            return row;
        }
        HTMLTable.RowFormatter rowFormatter = table.getRowFormatter();

        for (Para para : parameters) {
            Parameter parameter = para.getParameter().resolve();
            int col = 0;
            tableParameters.setWidget(row, col++, TableUtil.format(parameter.name, "variable"));
            String type = parameter.getType();
            tableParameters.setWidget(row, col++, TableUtil.format(type, "keyword"));


            Schema schema = parameter.getSchema();
            String format = schema.getDataType();
            if (format != null && format.equals("binary")) {
                //需要File字段
                FileUploadEx fileUpload = new FileUploadEx();
                para.setWidget(fileUpload);
                tableParameters.setWidget(row, col++, fileUpload);
            } else {
                TextBoxEx box = new TextBoxEx();
                box.setData(para);
                box.addValueChangeHandler(valueChangedhandler);
                if (parameter.pvalue != null) {
                    box.setValue(parameter.pvalue);
                } else if (parameter.example != null) {
                    box.setValue(parameter.example.toString());
                }
                para.setWidget(box);
                tableParameters.setWidget(row, col++, box);
            }

            tableParameters.setWidget(row, col++, TableUtil.format(parameter.description, "summary"));
            String style = row % 2 == 0 ? "row0" : "row1";
            rowFormatter.setStylePrimaryName(row, style);
            row++;
        }
        return row;
    }

    @Override
    public HandlerRegistration addValueChangeHandler(ValueChangeHandler<Para> handler) {
        return addHandler(handler, ValueChangeEvent.getType());
    }

    public void reset() {
        mInitialize = false;
    }

    /**
     * 解析参数并编辑
     *
     * @param parameters
     * @param type
     */
    public void parse(List<Para> parameters, String type, String emptyMessage) {
        if (mInitialize == false) {
            mParaList = parameters;
            parameterToFlexTable(parameters, type);
            mInitialize = true;
        }
    }

    public void appendToHeader(RequestHead requestHead) {
        for (Para p : mParaList) {
            Widget widget = p.getWidget();
            if (widget instanceof TextBox) {
                TextBox box = (TextBox) widget;
                requestHead.put(p.getParameter().name, box.getValue());
            }
        }
    }

    public void appendToUrl(String path) {
        StringBuilder sb = new StringBuilder();
        for (Para p : mParaList) {
            Widget widget = p.getWidget();
            if (widget instanceof TextBox) {
                TextBox box = (TextBox) widget;
                sb.append((path.length() == 0) ? "?" : "&");
                sb.append(p.getParameter().name);
                sb.append("=");
                sb.append(box.getValue());
            }
        }
        path = path + sb.toString();
    }

    public void appendToForm(FormData formData) {
        for (Para p : mParaList) {
            Widget widget = p.getWidget();
            if (widget instanceof TextBox) {
                TextBox box = (TextBox) widget;
                formData.append(p.getParameter().name, box.getValue());
            } else if (widget instanceof FileUploadEx) {
                FileUploadEx fileUploadEx = (FileUploadEx) widget;
                formData.append(p.getParameter().name, fileUploadEx.getFile());
            }
        }
    }

    interface ParameterListEditorUiBinder extends UiBinder<ScrollPanel, ParameterListEditor> {
    }
}