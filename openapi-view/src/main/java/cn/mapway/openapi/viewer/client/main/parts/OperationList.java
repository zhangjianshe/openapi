package cn.mapway.openapi.viewer.client.main.parts;

import cn.mapway.openapi.viewer.client.component.Link;
import cn.mapway.openapi.viewer.client.main.util.TableUtil;
import cn.mapway.openapi.viewer.client.specification.Operation;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.HasSelectionHandlers;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTMLTable;

import java.util.List;

/**
 * OperationList
 *
 * @author zhangjianshe@gmail.com
 */
public class OperationList extends Composite implements HasSelectionHandlers<Operation> {
    private static OperationListUiBinder ourUiBinder = GWT.create(OperationListUiBinder.class);
    @UiField
    FlexTable table;
    private ClickHandler anchorClickHandler = new ClickHandler() {
        @Override
        public void onClick(ClickEvent clickEvent) {
            Link link = (Link) clickEvent.getSource();
            Operation operation = link.getData();
            SelectionEvent.fire(OperationList.this, operation);
        }
    };

    public OperationList() {
        initWidget(ourUiBinder.createAndBindUi(this));
        TableUtil.initTableStyle(table);
    }

    @Override
    public HandlerRegistration addSelectionHandler(SelectionHandler<Operation> selectionHandler) {
        return addHandler(selectionHandler, SelectionEvent.getType());
    }

    public void parse(List<Operation> list) {
        table.removeAllRows();
        int row = 0;
        int col = 0;
        int index = 1;
        HTMLTable.RowFormatter rowFormatter = table.getRowFormatter();
        FlexTable.FlexCellFormatter cellFormatter = table.getFlexCellFormatter();
        table.setText(row, col++, "序号");
        cellFormatter.setWordWrap(row, col - 1, false);
        table.setText(row, col++, "名称");
        table.setText(row, col++, "路径");
        table.setText(row, col++, "说明");
        table.setText(row, col++, "作者");
        row++;

        for (Operation operation : list) {
            col = 0;
            table.setText(row, col++, (index++) + "");

            Link anchor = new Link();
            anchor.setText(operation.summary);
            anchor.addClickHandler(anchorClickHandler);
            anchor.setData(operation);

            table.setWidget(row, col++, anchor);
            cellFormatter.setWordWrap(row, col - 1, false);

            if (operation.isDeprecated()) {
                table.setWidget(row, col++, TableUtil.format(operation.getPath(), "col-path", "deprecated"));
            } else {
                table.setWidget(row, col++, TableUtil.format(operation.getPath(), "col-path"));
            }
            table.setWidget(row, col++, TableUtil.format(operation.description, "col-desc"));
            table.setText(row, col++, operation.getAuthor());
            cellFormatter.setWordWrap(row, col - 1, false);

            String style = row % 2 == 0 ? "row0" : "row1";
            rowFormatter.setStylePrimaryName(row, style);
            row++;
        }
    }

    interface OperationListUiBinder extends UiBinder<DockLayoutPanel, OperationList> {
    }

}