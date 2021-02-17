package cn.mapway.openapi.viewer.client.main.test.param;

import java.util.ArrayList;
import java.util.List;

/**
 * ParaContainer
 *
 * @author zhangjianshe@gmail.com
 */
public class ParaContainer {
    public List<Para> headPara;
    public List<Para> cookiePara;
    public List<Para> pathPara;
    public List<Para> queryPara;

    public ParaContainer() {
        headPara = new ArrayList<>();
        cookiePara = new ArrayList<>();
        pathPara = new ArrayList<>();
        queryPara = new ArrayList<>();
    }
}
