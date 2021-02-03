package cn.mapway.openapi.viewer.client.main.util;

import cn.mapway.openapi.viewer.client.component.Link;
import cn.mapway.openapi.viewer.client.specification.Schema;
import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.*;

import java.util.Map;

/**
 *
 */
public class TableUtil {

    public static void initTableStyle(FlexTable table) {
        table.setStyleName("list-table");
        Element e = table.getElement();
        e.setAttribute("borderCollapse", "collapse");
        e.setAttribute("cellPadding", "4px");
        e.setAttribute("cellSpacing", "1pt");
        e.setAttribute("bgColor", "#555555");
        e.setAttribute("width", "100%");
    }


    public static Label format(String text, String... style) {
        Label lb = new Label(text);
        if (style.length >= 1) {
            lb.setStyleName(style[0]);
            for (int i = 1; i < style.length; i++) {
                lb.setStyleName(style[i], true);
            }
        }
        return lb;
    }

    /**
     * Schema 转换为表格
     *
     * @param schema
     * @return
     */
    public static FlexTable rootSchemaToFlexTable(Schema schema, Map<String, GenTableInfo> allSchema) {

        FlexTable table = new FlexTable();
        TableUtil.initTableStyle(table);
        FlexTable.FlexCellFormatter cellFormatter = table.getFlexCellFormatter();
        HTMLTable.RowFormatter rowFormatter = table.getRowFormatter();


        String name = schema.title;
        table.setText(0, 0, name);
        cellFormatter.setColSpan(0, 0, 4);
        Anchor anchor = new Anchor();
        anchor.setName(name);
        table.setWidget(0, 1, anchor);


        int col = 0;
        int row = 1;
        table.setText(row, col++, "名称");
        table.setText(row, col++, "数据类型");
        table.setText(row, col++, "必填");
        table.setText(row, col++, "例子");
        table.setText(row, col++, "说明");
        col = 0;
        cellFormatter.setWidth(row, col++, "150px");
        cellFormatter.setWidth(row, col++, "100px");
        cellFormatter.setWidth(row, col++, "50px");
        cellFormatter.setWidth(row, col++, "100px");

        row++;
        if (schema.type.equals("object")) {
            for (String key : schema.properties.keys()) {
                col = 0;
                Schema field = schema.properties.item(key);
                field = field.resolve();
                table.setWidget(row, col++, TableUtil.format(key, "variable"));

                Schema exportSchema = field.getObjectSchema();
                if (exportSchema != null) {
                    GenTableInfo genTableInfo = allSchema.get(exportSchema.title);
                    if (genTableInfo == null) {
                        genTableInfo = new GenTableInfo();
                        genTableInfo.schema = exportSchema;
                        genTableInfo.hasGen = false;
                        genTableInfo.title = exportSchema.title;
                        allSchema.put(exportSchema.title, genTableInfo);
                    }
                    Link anchor1 = new Link(field.getDataType());
                    anchor1.setHref("#" + genTableInfo.title);
                    table.setWidget(row, col++, anchor1);
                } else {
                    table.setWidget(row, col++, TableUtil.format(field.getDataType(), "keyword"));
                }

                table.setText(row, col++, "是");
                table.setText(row, col++, field.example);
                table.setWidget(row, col++, TableUtil.format(field.description, "summary"));


                String style = row % 2 == 0 ? "row0" : "row1";
                rowFormatter.setStylePrimaryName(row, style);
                row++;
            }
        } else if (schema.type.equals("array")) {

        } else {
            col = 0;
            table.setWidget(row, col++, TableUtil.format("基本类型", "variable"));
            table.setWidget(row, col++, TableUtil.format(schema.getDataType(), "keyword"));
            table.setText(row, col++, "是");
            table.setText(row, col++, schema.example);
            table.setWidget(row, col++, TableUtil.format(schema.description, "summary"));
        }
        return table;
    }

    public static void changeSize(Image image) {
        image.setHeight("15px");
        image.setWidth("15px");
        Style style = image.getElement().getStyle();
        style.setVerticalAlign(Style.VerticalAlign.MIDDLE);
        style.setMarginRight(3, Style.Unit.PX);
    }
}
