package cn.mapway.openapi.viewer.client.main.test.param;

import cn.mapway.openapi.viewer.client.component.TextBoxEx;
import cn.mapway.openapi.viewer.client.main.util.TableUtil;
import cn.mapway.openapi.viewer.client.specification.Parameter;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.*;

import java.util.ArrayList;


/**
 * ParameterListEditor
 *
 * @author zhangjianshe@gmail.com
 */
public class ParameterListEditor extends Composite implements HasValueChangeHandlers<Parameter> {
    private static ParameterListEditorUiBinder ourUiBinder = GWT.create(ParameterListEditorUiBinder.class);
    @UiField
    FlexTable tableParameters;
    private ValueChangeHandler<String> valueChangedhandler = new ValueChangeHandler<String>() {
        @Override
        public void onValueChange(ValueChangeEvent<String> event) {
            TextBoxEx box = (TextBoxEx) event.getSource();
            Parameter parameter = box.getData();
            parameter.pvalue = box.getValue();
            ValueChangeEvent.fire(ParameterListEditor.this, parameter);
        }
    };

    public ParameterListEditor() {
        initWidget(ourUiBinder.createAndBindUi(this));
    }

    private void parameterToFlexTable(Parameter[] parameters, String type) {

        int row = 0;
        int col = 0;
        int index = 1;

        tableParameters.removeAllRows();
        if (parameters == null) {
            return;
        }

        tableParameters.setText(row, col++, "名称");
        tableParameters.setText(row, col++, "数据类型");
        tableParameters.setText(row, col++, "数值");
        tableParameters.setText(row, col++, "说明");
        row++;


        ArrayList<Parameter> headParameters = new ArrayList<>();
        ArrayList<Parameter> pathParameters = new ArrayList<>();
        ArrayList<Parameter> queryParameters = new ArrayList<>();
        ArrayList<Parameter> cookieParameters = new ArrayList<>();

        for (Parameter p : parameters) {
            if (p.in.equals("query")) {
                queryParameters.add(p);
            }
            if (p.in.equals("path")) {
                pathParameters.add(p);
            }
            if (p.in.equals("header")) {
                headParameters.add(p);
            }
            if (p.in.equals("cookie")) {
                cookieParameters.add(p);
            }
        }


        if ("cookie".equals(type)) {
            renderCatalogParameter(row, tableParameters, pathParameters);
        } else if ("header".equals(type)) {
            renderCatalogParameter(row, tableParameters, headParameters);
        } else if ("path".equals(type)) {
            renderCatalogParameter(row, tableParameters, pathParameters);
        } else if ("query".equals(type)) {
            renderCatalogParameter(row, tableParameters, queryParameters);
        }
    }

    private int renderCatalogParameter(int row, FlexTable table, ArrayList<Parameter> parameters) {
        if (parameters.size() == 0) {
            return row;
        }
        HTMLTable.RowFormatter rowFormatter = table.getRowFormatter();

        for (Parameter parameter : parameters) {
            int col = 0;
            tableParameters.setWidget(row, col++, TableUtil.format(parameter.name, "variable"));
            String type = parameter.getType();
            tableParameters.setWidget(row, col++, TableUtil.format(type, "keyword"));
            TextBoxEx box = new TextBoxEx();
            box.setData(parameter);
            box.addValueChangeHandler(valueChangedhandler);
            if (parameter.pvalue != null) {
                box.setValue(parameter.pvalue);
            } else if (parameter.example != null) {
                box.setValue(parameter.example.toString());
            }
            tableParameters.setWidget(row, col++, box);
            tableParameters.setWidget(row, col++, TableUtil.format(parameter.description, "summary"));
            String style = row % 2 == 0 ? "row0" : "row1";
            rowFormatter.setStylePrimaryName(row, style);
            row++;
        }
        return row;
    }

    @Override
    public HandlerRegistration addValueChangeHandler(ValueChangeHandler<Parameter> handler) {
        return addHandler(handler, ValueChangeEvent.getType());
    }

    /**
     * 解析参数并编辑
     *
     * @param parameters
     * @param type
     */
    public void parse(Parameter[] parameters, String type) {
        parameterToFlexTable(parameters, type);
    }

    interface ParameterListEditorUiBinder extends UiBinder<ScrollPanel, ParameterListEditor> {
    }
}