package cn.mapway.openapi.viewer.client.util;

/**
 * The Interface IOnData.
 *
 * @param <T> the generic type
 */
public interface IOnData<T> {

    /**
     * On error.
     *
     * @param url   the url
     * @param error the error
     */
     void onError(String url, String error);

    /**
     * On success.
     *
     * @param url  the url
     * @param data the data
     */
     void onSuccess(String url, T data);
}