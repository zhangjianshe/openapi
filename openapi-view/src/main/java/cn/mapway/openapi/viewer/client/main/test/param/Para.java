package cn.mapway.openapi.viewer.client.main.test.param;

import cn.mapway.openapi.viewer.client.specification.Parameter;
import com.google.gwt.user.client.ui.Widget;

/**
 * Para
 *
 * @author zhangjianshe@gmail.com
 */
public class Para {
    Parameter parameter;
    Widget widget;

    public Para(Parameter p) {
        parameter = p;
    }

    Parameter getParameter() {
        return parameter;
    }

    public Widget getWidget() {
        return widget;
    }

    public void setWidget(Widget widget1) {
        widget = widget1;
    }
}
