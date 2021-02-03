package cn.mapway.openapi.viewer.client.main.parts;

import cn.mapway.openapi.viewer.client.component.JsonPanel;
import cn.mapway.openapi.viewer.client.main.MainFrame;
import cn.mapway.openapi.viewer.client.main.util.GenTableInfo;
import cn.mapway.openapi.viewer.client.main.util.TableUtil;
import cn.mapway.openapi.viewer.client.resource.MainResource;
import cn.mapway.openapi.viewer.client.specification.*;
import cn.mapway.openapi.viewer.client.util.SchemeUtil;
import com.google.gwt.core.client.GWT;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * OperationView
 *
 * @author zhangjianshe@gmail.com
 */
public class OperationView extends Composite {
    private static OperationViewUiBinder ourUiBinder = GWT.create(OperationViewUiBinder.class);
    @UiField
    Label lbTitle;
    @UiField
    Label lbURL;
    @UiField
    HTML lbDescription;
    @UiField
    FlexTable tableParameters;
    @UiField
    HTMLPanel htmlAllSchema;
    @UiField
    HTMLPanel htmlRequest;
    @UiField
    HTMLPanel htmlResponse;
    @UiField
    Image imgMethod;
    @UiField
    JsonPanel jsonInput;
    @UiField
    JsonPanel jsonOutput;

    public OperationView() {
        initWidget(ourUiBinder.createAndBindUi(this));
        TableUtil.initTableStyle(tableParameters);
        TableUtil.changeSize(imgMethod);
    }

    public void parse(Operation operation) {
        lbTitle.setText(operation.summary);

        if ("o".equals(operation.opType)) {
            imgMethod.setResource(MainResource.INSTANCE.o());
        } else if ("g".equals(operation.opType)) {
            imgMethod.setResource(MainResource.INSTANCE.g());
        } else if ("p".equals(operation.opType)) {
            imgMethod.setResource(MainResource.INSTANCE.p());
        } else if ("d".equals(operation.opType)) {
            imgMethod.setResource(MainResource.INSTANCE.d());
        } else {
            imgMethod.setResource(MainResource.INSTANCE.n());
        }

        String root = "";
        if (MainFrame.getCurrentServer() != null) {
            root = MainFrame.getCurrentServer().url;
        }
        lbURL.setText(root + operation.getPath());

        lbDescription.setHTML(operation.description);
        tableParameters.removeAllRows();
        if (operation.parameters != null) {
            parseParameters(operation.parameters);
        }
        //输入参数
        Map<String, GenTableInfo> allSchema = new HashMap<>();

        htmlRequest.clear();
        if (operation.requestBody != null) {
            MediaType mediaType = operation.requestBody.jsonType();
            Schema schema = mediaType.getSchema().resolve();
            htmlRequest.add(TableUtil.rootSchemaToFlexTable(schema, allSchema));
            JSONValue jsonObject = SchemeUtil.fromSchema(schema);
            jsonInput.setJson(jsonObject);
        } else {
            jsonInput.setString("无需参数");
        }

        //输出模型
        htmlResponse.clear();
        Response defaultResponse = operation.getDefaultResponse();
        MediaType outputMediaType = defaultResponse.jsonMediaType();
        if (outputMediaType != null) {
            Schema schema = outputMediaType.getSchema().resolve();
            htmlResponse.add(TableUtil.rootSchemaToFlexTable(schema, allSchema));
            jsonOutput.setJson(SchemeUtil.fromSchema(schema));
        } else {
            jsonOutput.setString("好像没有参数返回");
        }


        htmlAllSchema.clear();
        //所有内嵌模型
        int count = allSchema.size();
        while (count > 0) {
            //不能循环 拷贝一份出来

            List<GenTableInfo> tempList = new ArrayList<>();
            for (GenTableInfo genTableInfo : allSchema.values()) {
                tempList.add(genTableInfo);
            }
            for (GenTableInfo genTableInfo : tempList) {
                if (genTableInfo.hasGen == null || !genTableInfo.hasGen) {
                    //修改 allSchema
                    htmlAllSchema.add(TableUtil.rootSchemaToFlexTable(genTableInfo.schema, allSchema));
                    genTableInfo.hasGen = true;
                }
            }
//            查看allSchema内容
            int leftCount = 0;
            for (GenTableInfo genTableInfo : allSchema.values()) {
                if (genTableInfo.hasGen == false) {
                    leftCount++;
                }
            }
            count = leftCount;
        }
    }

    void parseParameters(Parameter[] parameters) {

        int row = 0;
        int col = 0;
        int index = 1;

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
        FlexTable.FlexCellFormatter cellFormatter = tableParameters.getFlexCellFormatter();
        tableParameters.setText(row, col++, "分类");
        tableParameters.setText(row, col++, "名称");
        tableParameters.setText(row, col++, "数据类型");
        tableParameters.setText(row, col++, "必填");
        tableParameters.setText(row, col++, "例子");
        tableParameters.setText(row, col++, "说明");
        cellFormatter.setWidth(row, 0, "150px");

        row++;


        row = renderCatalogParameter(row, tableParameters, pathParameters, "URL模板参数");
        row = renderCatalogParameter(row, tableParameters, queryParameters, "URL查询参数");
        row = renderCatalogParameter(row, tableParameters, headParameters, "HTTP请求头参数");
        row = renderCatalogParameter(row, tableParameters, cookieParameters, "Cookie参数");
    }

    private int renderCatalogParameter(int row, FlexTable table, ArrayList<Parameter> parameters, String catalog) {
        if (parameters.size() == 0) {
            return row;
        }
        FlexTable.FlexCellFormatter cellFormatter = table.getFlexCellFormatter();
        HTMLTable.RowFormatter rowFormatter = table.getRowFormatter();

        table.setText(row, 0, catalog);
        if (parameters.size() > 1) {
            cellFormatter.setRowSpan(row, 0, parameters.size());
        }
        int index = 0;
        for (Parameter parameter : parameters) {
            parameter = parameter.resolve();
            int col = 1;
            if (parameters.size() > 1 && index > 0) {
                col = 0;
            }
            tableParameters.setWidget(row, col++, TableUtil.format(parameter.name, "variable"));
            String type = parameter.getType();

            tableParameters.setWidget(row, col++, TableUtil.format(type, "keyword"));

            tableParameters.setText(row, col++, parameter.isRequired() ? "是" : "否");
            if (parameter.example != null) {
                tableParameters.setText(row, col++, parameter.example.toString());
            } else {
                tableParameters.setText(row, col++, "");
            }
            tableParameters.setWidget(row, col++, TableUtil.format(parameter.description, "summary"));
            String style = row % 2 == 0 ? "row0" : "row1";
            rowFormatter.setStylePrimaryName(row, style);
            row++;
            index++;
        }
        return row;
    }


    interface OperationViewUiBinder extends UiBinder<ScrollPanel, OperationView> {
    }
}