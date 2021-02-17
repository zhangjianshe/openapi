package cn.mapway.openapi.viewer.client.component;

import cn.mapway.openapi.viewer.client.main.test.param.Para;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.FileUpload;


/**
 * FileUploadEx
 *
 * @author zhangjianshe@gmail.com
 */
public class FileUploadEx extends FileUpload {
    Object mData;

    private final native static JavaScriptObject file(Element ele)/*-{
        return ele.files[0];
    }-*/;

    public <T> T getData() {
        return (T) mData;
    }

    public void setData(Object para) {
        mData = para;
    }

    public JavaScriptObject getFile() {
        return file(getElement());
    }
}
