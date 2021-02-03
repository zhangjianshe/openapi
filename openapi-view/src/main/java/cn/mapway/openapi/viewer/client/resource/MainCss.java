package cn.mapway.openapi.viewer.client.resource;


import com.google.gwt.resources.client.CssResource;

/**
 * CSS 样式表
 */
public interface MainCss extends CssResource {
    String body();

    String link();

    /**
     * Css number.
     *
     * @return the string
     */
    String cssNumber();

    /**
     * Css null.
     *
     * @return the string
     */
    String cssNull();

    /**
     * Css boolean.
     *
     * @return the string
     */
    String cssBoolean();

    /**
     * Css key.
     *
     * @return the string
     */
    String cssKey();

    /**
     * Css string.
     *
     * @return the string
     */
    String cssString();

    /**
     * Pre.
     *
     * @return the string
     */
    String pre();

    String cssTable();
}
