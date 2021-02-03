package cn.mapway.openapi.viewer.client.specification;

/**
 * Group
 *
 * @author zhangjianshe@gmail.com
 */

public class Group {
    String name;
    String path;

    public Group(String name, String path) {
        this.name = name;
        this.path = path;
    }

    public String fullName() {
        return path + "_" + name;
    }
}
