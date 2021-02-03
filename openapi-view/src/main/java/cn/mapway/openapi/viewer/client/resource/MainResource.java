package cn.mapway.openapi.viewer.client.resource;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ClientBundle.Source;
import com.google.gwt.resources.client.DataResource;
import com.google.gwt.resources.client.ImageResource;

/**
 * 系统资源
 */
public interface MainResource extends ClientBundle {
    public static final MainResource INSTANCE = GWT.create(MainResource.class);

    @Source("main.gss")
    MainCss css();

    @Source({"image/treeClose.png"})
    ImageResource treeClose();

    @Source("image/inter.png")
    ImageResource inter();

    @Source("image/treeOpen.png")
    ImageResource treeOpen();

    @Source("image/g.png")
    ImageResource g();

    @Source("image/p.png")
    ImageResource p();

    @Source("image/n.png")
    ImageResource n();

    @Source("image/d.png")
    ImageResource d();

    @Source("image/o.png")
    ImageResource o();

    @Source("image/loadding.gif")
    ImageResource loading();
}
