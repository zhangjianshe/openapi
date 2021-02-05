package cn.mapway.openapi.viewer.client.component;

import com.google.gwt.user.client.ui.TextBox;

/**
 * TextBoxEx
 *
 * @author zhangjianshe@gmail.com
 */
public class TextBoxEx extends TextBox {
    Object data;

    public TextBoxEx() {
        super();
        setStyleName("my-textbox");
    }

    public <T> T getData() {
        return (T) data;
    }

    public void setData(Object d) {
        data = d;
    }
}
