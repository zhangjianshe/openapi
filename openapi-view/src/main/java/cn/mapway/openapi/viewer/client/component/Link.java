package cn.mapway.openapi.viewer.client.component;

import com.google.gwt.user.client.ui.Anchor;

public class Link extends Anchor {
    public Link(String name) {
        super();
        setText(name);
        setStyleName("my-link");
    }

    public Link() {
        super();
        setStyleName("my-link");
    }

    Object data;

    public void setData(Object data)
    {
        this.data=data;
    }

    public <T> T getData(){
        return (T)data;
    }
}
