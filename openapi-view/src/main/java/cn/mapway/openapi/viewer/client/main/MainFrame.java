package cn.mapway.openapi.viewer.client.main;

import cn.mapway.openapi.viewer.client.component.TextBoxEx;
import cn.mapway.openapi.viewer.client.main.parts.OperationList;
import cn.mapway.openapi.viewer.client.main.parts.OperationView;
import cn.mapway.openapi.viewer.client.specification.*;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.*;
import jsinterop.annotations.JsMethod;

import java.util.ArrayList;

/**
 * main frame to show
 */
public class MainFrame extends Composite {
    public static OpenApiDoc mDoc;
    private static MainFrameUiBinder ourUiBinder = GWT.create(MainFrameUiBinder.class);
    private static Server CURRENT_SERVER;
    OperationList operationList;
    OperationView operationView;
    Widget current;
    @UiField
    Label lbTitle;
    @UiField
    Label lbDescription;
    @UiField
    Label lbVersion;
    @UiField
    ContactView viewContact;
    @UiField
    ApiTree apiTree;
    @UiField
    DockLayoutPanel root;
    private final SelectionHandler<Operation> operationSelected = new SelectionHandler<Operation>() {
        @Override
        public void onSelection(SelectionEvent<Operation> selectionEvent) {
            Operation operation = selectionEvent.getSelectedItem();
            sureOperator(operation);
        }
    };
    private final SelectionHandler<TreeItem> treeSelectedHandler = new SelectionHandler<TreeItem>() {
        @Override
        public void onSelection(SelectionEvent<TreeItem> event) {
            TreeItem treeItem = event.getSelectedItem();
            apiTree.changeStyle(treeItem);

            if (treeItem.getUserObject() instanceof Group) {
                sureOperatorList(treeItem);
            } else if (treeItem.getUserObject() instanceof Operation) {
                Operation operation = (Operation) treeItem.getUserObject();
                sureOperator(operation);
            }

        }
    };
    @UiField
    ListBox ddlServers;
    private final ChangeHandler serverChangeHandler = new ChangeHandler() {
        @Override
        public void onChange(ChangeEvent event) {
            String server = ddlServers.getSelectedValue();
            CURRENT_SERVER = mDoc.servers[ddlServers.getSelectedIndex()];
        }
    };
    @UiField
    TextBoxEx txtSearch;

    public MainFrame() {
        initWidget(ourUiBinder.createAndBindUi(this));
        apiTree.addSelectionHandler(treeSelectedHandler);
        ddlServers.addChangeHandler(serverChangeHandler);
    }

    public final static Server getCurrentServer() {
        return CURRENT_SERVER;
    }

    @JsMethod(namespace = "JSON")
    private static native OpenApiDoc parse(String json);

    /**
     * 展示操作
     *
     * @param operation
     */
    private void sureOperator(Operation operation) {
        if (operationView == null) {
            operationView = new OperationView();
        }
        if (operationView != current) {
            if (current != null) {
                root.remove(current);
            }
            root.add(operationView);
            current = operationView;
        }
        operationView.parse(operation);
    }

    /**
     * 操作列表
     *
     * @param treeItem
     */
    private void sureOperatorList(TreeItem treeItem) {
        ArrayList list = findAllOperation(treeItem);
        if (operationList == null) {
            operationList = new OperationList();
            operationList.addSelectionHandler(operationSelected);
        }
        if (operationList != current) {
            if (current != null) {
                root.remove(current);
            }
            root.add(operationList);
            current = operationList;
        }
        operationList.parse(list);
    }

    private ArrayList findAllOperation(TreeItem treeItem) {
        ArrayList list = new ArrayList();

        for (int i = 0; i < treeItem.getChildCount(); i++) {
            TreeItem item = treeItem.getChild(i);
            if (item.getChildCount() > 0) {
                ArrayList<Operation> subs = findAllOperation(item);
                list.addAll(subs);
            } else {
                Operation operation = (Operation) item.getUserObject();
                if (operation != null) {
                    list.add(operation);
                }
            }
        }
        return list;
    }

    public void parseData(String data) {
        mDoc = parse(data);
        renderData();
    }

    private void renderData() {
        Info info = mDoc.info;
        lbTitle.setText(info.title);
        lbDescription.setText(info.description);
        lbVersion.setText(info.version);

        CURRENT_SERVER = null;
        if (mDoc.servers != null) {
            for (int i = 0; i < mDoc.servers.length; i++) {
                if (i == 0) {
                    CURRENT_SERVER = mDoc.servers[0];
                }
                Server server = mDoc.servers[i];
                ddlServers.addItem(server.description, server.description);
            }
        }

        apiTree.parseData(mDoc.paths);
        viewContact.parse(mDoc.info);
    }

    @UiHandler("txtSearch")
    public void txtValueChanged(KeyUpEvent event) {
        String text = txtSearch.getValue();


        int keyCode = event.getNativeEvent().getKeyCode();
        GWT.log("key:" + keyCode);
        if (keyCode == KeyCodes.KEY_ENTER) {
            filter(text);
        } else if (keyCode == KeyCodes.KEY_BACKSPACE) {
            GWT.log(text);
            if (text.length() > 0) {
                text = text.substring(0, text.length() - 1);
            }
            GWT.log(text);
            filter(text);

        } else if (keyCode == KeyCodes.KEY_LEFT || keyCode == KeyCodes.KEY_RIGHT)
        {

        }else if( keyCode == KeyCodes.KEY_UP )
        {
            apiTree.moveUp();
        }else if(keyCode == KeyCodes.KEY_DOWN)
        {
            apiTree.moveDown();
        }
        else {

            GWT.log("last " + text);
            filter(text);
        }
    }

    @UiHandler("txtSearch")
    public void txtSearchFocus(FocusEvent event) {
        txtSearch.setText("");
        filter("");
    }

    private void filter(String text) {
        text = text.trim();
        apiTree.filter(text.toLowerCase());
    }

    interface MainFrameUiBinder extends UiBinder<DockLayoutPanel, MainFrame> {
    }
}