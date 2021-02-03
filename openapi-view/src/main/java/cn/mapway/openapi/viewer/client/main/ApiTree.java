package cn.mapway.openapi.viewer.client.main;

import cn.mapway.openapi.viewer.client.component.LocalStorage;
import cn.mapway.openapi.viewer.client.main.util.TableUtil;
import cn.mapway.openapi.viewer.client.resource.MainResource;
import cn.mapway.openapi.viewer.client.resource.TreeResource;
import cn.mapway.openapi.viewer.client.specification.Group;
import cn.mapway.openapi.viewer.client.specification.MapObject;
import cn.mapway.openapi.viewer.client.specification.Operation;
import cn.mapway.openapi.viewer.client.specification.PathItem;
import cn.mapway.openapi.viewer.client.util.ChineseUtil;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.logical.shared.OpenEvent;
import com.google.gwt.event.logical.shared.OpenHandler;
import com.google.gwt.user.client.ui.*;

import java.util.ArrayList;

/**
 * API Tree
 *
 * @author zhangjianshe@gmail.com
 */
public class ApiTree extends Tree {


    Element lastElement;
    /**
     * The open handler.
     */
    private OpenHandler<TreeItem> openHandler = new OpenHandler<TreeItem>() {

        @Override
        public void onOpen(OpenEvent<TreeItem> event) {
            Group g = (Group) event.getTarget().getUserObject();
            setOpen(g.fullName(), true);
        }
    };
    /**
     * The close handler.
     */
    private CloseHandler<TreeItem> closeHandler = new CloseHandler<TreeItem>() {

        @Override
        public void onClose(CloseEvent<TreeItem> event) {
            Group g = (Group) event.getTarget().getUserObject();
            setOpen(g.fullName(), false);
        }
    };

    /**
     * Instantiates a new api tree.
     */
    public ApiTree() {
        super(new TreeResource(), false);
        this.addOpenHandler(openHandler);
        this.addCloseHandler(closeHandler);

    }

    /**
     * 根据用户的选择改变TREEITEM的样式
     *
     * @param item
     */
    public void changeStyle(TreeItem item) {
        if (item.getChildCount() > 0) {
            Element ele = item.getWidget().getElement();

            while (ele != null) {
                if (ele.getTagName().equalsIgnoreCase("tr")) {
                    ele.getFirstChildElement().getStyle().setWidth(16, Style.Unit.PX);
                    ele.getFirstChildElement().getStyle().setCursor(Style.Cursor.DEFAULT);
                    break;
                }
                ele = ele.getParentElement();
            }


            ele = item.getWidget().getElement();
            String tag = ele.getTagName();
            while (ele != null) {
                if (ele.getTagName().equalsIgnoreCase("table")) {
                    break;
                }
                ele = ele.getParentElement();
            }
            if (ele != lastElement) {
                if (lastElement != null) {
                    lastElement.getStyle().clearBackgroundColor();
                }
                Style style = ele.getStyle();
                style.setBackgroundColor("darkseagreen");
                style.setWidth(100, Style.Unit.PCT);
                style.setTextAlign(Style.TextAlign.LEFT);
                lastElement = ele;
            }
        } else {
            Element ele = item.getWidget().getElement().getParentElement().getParentElement();

            if (ele != lastElement) {
                if (lastElement != null) {
                    lastElement.getStyle().clearBackgroundColor();
                }
                Style style = ele.getStyle();
                style.setBackgroundColor("skyblue");
                style.setWidth(100, Style.Unit.PCT);
                style.setTextAlign(Style.TextAlign.LEFT);
                lastElement = ele;
            }
        }
    }

    /**
     * Checks if is open.
     *
     * @param key the key
     * @return true, if is open
     */
    public boolean isOpen(String key) {
        String data = LocalStorage.val(key);
        if ("1".equals(data)) {
            return true;
        }
        return false;
    }

    /**
     * Sets the open.
     *
     * @param key  the key
     * @param open the open
     */
    public void setOpen(String key, boolean open) {
        LocalStorage.save(key, open ? "1" : "0");
    }

    /**
     * Parses the data.
     *
     * @param data the data
     */
    public void parseData(MapObject<PathItem> data) {
        this.clear();
        if (data == null) {
            this.addTextItem("没有接口数据");
            return;
        }

        String[] keys = data.keys();
        for (int i = 0; i < keys.length; i++) {
            PathItem item = data.item(keys[i]);
            item.name = keys[i];
            parseOperator(item);
        }

    }

    private void parseOperator(PathItem item) {
        if (item.get != null) {
            item.get.setPathItem(item);
            item.get.opType = "g";
            addOperator(item.get);
        }
        if (item.post != null) {
            item.post.setPathItem(item);
            item.post.opType = "p";
            addOperator(item.post);
        }

        if (item.options != null) {
            item.options.setPathItem(item);
            item.options.opType = "o";
            addOperator(item.options);
        }
        if (item.delete != null) {
            item.delete.setPathItem(item);
            item.delete.opType = "d";
            addOperator(item.delete);
        }
    }


    private void addOperator(Operation operator) {
        String pinyin = ChineseUtil.pinyin(operator.summary);
        if (pinyin != null) {
            pinyin = pinyin.toLowerCase();
        }
        operator.pinyin = pinyin;
        GWT.log(operator.summary + "  -->  " + operator.pinyin);
        String groupPath = findGroupTag(operator);
        TreeItem node = findTreeNode(groupPath);
        Group ngroup = (Group) node.getUserObject();
        node.setState(isOpen(ngroup.fullName()));
        TreeItem itemOperator = new TreeItem();
        HorizontalPanel p = new HorizontalPanel();
        p.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
        itemOperator.setWidget(p);
        itemOperator.setUserObject(operator);


        if ("g".equals(operator.opType)) {
            Image img = new Image(MainResource.INSTANCE.g());
            TableUtil.changeSize(img);
            p.add(img);
        } else if ("p".equals(operator.opType)) {
            Image img = new Image(MainResource.INSTANCE.p());
            TableUtil.changeSize(img);
            p.add(img);
        } else if ("o".equals(operator.opType)) {
            Image img = new Image(MainResource.INSTANCE.o());
            TableUtil.changeSize(img);
            p.add(img);
        } else if ("d".equals(operator.opType)) {
            Image img = new Image(MainResource.INSTANCE.d());
            TableUtil.changeSize(img);
            p.add(img);
        } else {
            Image img = new Image(MainResource.INSTANCE.n());
            TableUtil.changeSize(img);
            p.add(img);
        }

        p.add(new Label(operator.summary));
        p.setStyleName("tree-text");

        node.addItem(itemOperator);
    }

    /**
     * 根据路径信息 找到了 树节点项目
     *
     * @param groupPath
     * @return
     */
    private TreeItem findTreeNode(String groupPath) {
        String[] pathNode = groupPath.split("/");
        ArrayList<String> nodes = new ArrayList<>();
        for (int i = 0; i < pathNode.length; i++) {
            String n = pathNode[i];
            n = n.trim();
            if (n.length() > 0) {
                nodes.add(n);
            }
        }

        TreeItem treeNode = null;
        for (int i = 0; i < nodes.size(); i++) {
            String name = nodes.get(i);
            treeNode = sureTreeNode(treeNode, name);
        }
        return treeNode;
    }


    /**
     * 确保路径上有相关的 对象
     *
     * @param parent
     * @param name
     * @return
     */
    private TreeItem sureTreeNode(TreeItem parent, String name) {
        if (parent == null) {
            for (int j = 0; j < this.getItemCount(); j++) {
                TreeItem tn = this.getItem(j);
                if (tn.getText().equalsIgnoreCase(name)) {
                    return tn;
                }
            }

            TreeItem item = new TreeItem();
            Label lb = new Label(name);
            lb.setStyleName("tree-group-text");
            item.setWidget(lb);
            this.addItem(item);
            Group g = new Group(name, "");
            item.setUserObject(g);
            item.setState(isOpen(g.fullName()));
            return item;
        } else {
            for (int j = 0; j < parent.getChildCount(); j++) {
                TreeItem tn = parent.getChild(j);
                if (tn.getText().equalsIgnoreCase(name)) {
                    return tn;
                }
            }

            TreeItem item = new TreeItem();
            Label lb = new Label(name);
            lb.setStyleName("tree-group-text");
            item.setWidget(lb);
            parent.addItem(item);
            Group parentGroup = (Group) parent.getUserObject();
            Group g = new Group(name, parentGroup.fullName());
            item.setUserObject(g);


            parent.setState(isOpen(parentGroup.fullName()));

            return item;
        }
    }

    private String findGroupTag(Operation item) {
        if (item.tags != null && item.tags.length != 0) {
            for (int i = 0; i < item.tags.length; i++) {
                String tag = item.tags[i];
                tag = tag.trim();
                if (tag.length() > 0) {
                    if (tag.startsWith("/"))
                        return tag;
                    else
                        return "/" + tag;
                }
            }
        }
        return "/";
    }

    /**
     * 过滤
     *
     * @param text
     */
    public void filter(String text) {
        for (int i = 0; i < this.getItemCount(); i++) {
            TreeItem item = this.getItem(i);
            if (item.getChildCount() > 0) {
                boolean visible = canVisible(item, text);
                item.setVisible(visible);
                item.setState(visible);
            } else {
                item.setVisible(false);
            }
        }
    }

    private boolean canVisible(TreeItem item, String text) {

        if (item.getChildCount() > 0) {
            boolean v = false;
            for (int i = 0; i < item.getChildCount(); i++) {
                TreeItem subItem = item.getChild(i);
                boolean visible = canVisible(subItem, text);
                if (visible == true) {
                    v = visible;
                }
                subItem.setVisible(visible);
                subItem.setState(true);
            }
            return v;
        }

        Operation operation = (Operation) item.getUserObject();
        String t = operation.summary.toLowerCase();
        if (text.length() == 0) {
            return true;
        }
        if (t.contains(text) || (operation.pinyin != null && operation.pinyin.contains(text))) {
            return true;
        }
        return false;
    }

    public void moveUp() {
        TreeItem item = this.getSelectedItem();
        if (item == null) {
            if (this.getItemCount() > 0) {
                this.getItem(0).setSelected(true);
            }
        } else {
            if (item.getParentItem() != null) {
                int index = item.getParentItem().getChildIndex(item);
                if (index > 0) {
                    index--;
                    item.getParentItem().getChild(index).setSelected(true);
                } else {
                    item.getParentItem().setSelected(true);
                }
            } else {
                for (int i = this.getItemCount() - 1; i >= this.getItemCount(); i--) {
                    if (this.getItem(i).equals(item)) {
                        if (i > 0) {
                            this.getItem(i - 1).setSelected(true);
                            break;
                        }
                    }
                }
            }
        }
    }

    public void moveDown() {
    }
}
