package cn.mapway.openapi.viewer.client.resource;

import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.Tree;

/**
 * TreeResource
 *
 * @author zhangjianshe@gmail.com
 */


public class TreeResource implements Tree.Resources {

    /* (non-Javadoc)
     * @see com.google.gwt.user.client.ui.Tree.Resources#treeClosed()
     */
    @Override
    public ImageResource treeClosed() {

        return MainResource.INSTANCE.treeClose();
    }

    /* (non-Javadoc)
     * @see com.google.gwt.user.client.ui.Tree.Resources#treeLeaf()
     */
    @Override
    public ImageResource treeLeaf() {

        return MainResource.INSTANCE.inter();
    }

    /* (non-Javadoc)
     * @see com.google.gwt.user.client.ui.Tree.Resources#treeOpen()
     */
    @Override
    public ImageResource treeOpen() {

        return MainResource.INSTANCE.treeOpen();
    }

}

