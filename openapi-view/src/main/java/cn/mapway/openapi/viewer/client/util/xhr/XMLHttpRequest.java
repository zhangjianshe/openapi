package cn.mapway.openapi.viewer.client.util.xhr;

import com.google.gwt.core.client.JavaScriptObject;
import jsinterop.annotations.JsConstructor;
import jsinterop.annotations.JsMethod;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

import static jsinterop.annotations.JsPackage.GLOBAL;

@JsType(isNative = true, namespace = GLOBAL)
public class XMLHttpRequest extends XMLHttpRequestEventTarget {


    /**
     * An EventHandler that is called whenever the readyState attribute changes. The callback is called from the user interface thread. The XMLHttpRequest.onreadystatechange property contains the event handler to be called when the readystatechange event is fired, that is every time the readyState property of the XMLHttpRequest changes. The callback is called from the user interface thread.
     */
    @JsProperty
    public Function onreadystatechange;
    /**
     * The XMLHttpRequest.response property returns the response's body. It can be of the type ArrayBuffer, Blob, Document, JavaScript object, or a DOMString, depending of the value of XMLHttpRequest.responseType property. Value of response is null if the request is not complete or was not successful. However, if the value of responseType was set to "text" or the empty string, response can contain the partial text response while the request is still in the loading state.
     */
    @JsProperty
    public String response;
    @JsProperty(name = "response")
    public JavaScriptObject responseAsJSON;
    @JsProperty(name = "response")
    public Document responseAsDocument;
    @JsProperty(name = "response")
    public Blob responseAsBlob;
    /**
     * The XMLHttpRequest.responseType property is an enumerated value that returns the type of the response. It also lets the author change the response type to one "arraybuffer", "blob", "document", "json", or "text". If an empty string is set as the value of responseType, it is assumed as type "text".
     */
    @JsProperty
    public String responseType;
    /**
     * The XMLHttpRequest.responseText property returns a DOMString that contains the response to the request as text, or null if the request was unsuccessful or has not yet been sent. The responseText property will have the partial response as it arrives even before the request is complete. If responseType is set to anything other than the empty string or "text", accessing responseText will throw InvalidStateError exception.
     */
    @JsProperty
    public String responseText;
    /**
     * The XMLHttpRequest.status property returns the numerical status code of the response of the XMLHttpRequest. status will be an unsigned short. Before the request is complete, the value of status will be 0. It is worth noting that browsers report a status of 0 in case of XMLHttpRequest errors too.
     */
    @JsProperty
    public short status;
    /**
     * The XMLHttpRequest.timeout property is an unsigned long representing the number of milliseconds a request can take before automatically being terminated. The default value is 0, which means there is no timeout. Timeout shouldn't be used for synchronous XMLHttpRequests requests used in a document environment or it will throw an InvalidAccessError exception. When a timeout happens, a timeout event is fired.
     */
    @JsProperty
    public long timeout;
    /**
     * The XMLHttpRequest.upload property returns an XMLHttpRequestUpload object, representing the upload process. It is an opaque object, but being an XMLHttpRequestEventTarget, event listeners can be set on it to track its process.
     */
    @JsProperty
    public XMLHttpRequestUpload upload;
    /**
     * The XMLHttpRequest.withCredentials property is a Boolean that indicates whether or not cross-site Access-Control requests should be made using credentials such as cookies, authorization headers or TLS client certificates. Setting withCredentials has no effect on same-site requests.
     */
    @JsProperty
    public Boolean withCredentials;
    /**
     * The XMLHttpRequest.readyState property returns the state an XMLHttpRequest client is in. An XHR client exists in one of the following states:
     */
    @JsProperty
    public short readyState;
    /**
     * The XMLHttpRequest.responseURL property returns the serialized URL of the response or the empty string if the URL is null. If the URL is returned, URL fragment if present in the URL will be stripped away. The value of responseURL will be the final URL obtained after any redirects.
     */
    @JsProperty
    private String responseURL;
    /**
     * The XMLHttpRequest.responseXML property returns a Document containing the response to the request, or null if the request was unsuccessful, has not yet been sent, or cannot be parsed as XML or HTML. The response is parsed as if it were a text/xml stream. When the responseType is set to "document" and the request has been made asynchronously, the response is parsed as a text/html stream. responseXML is null for data: URLs.
     */
    @JsProperty
    private Document responseXML;
    /**
     * The XMLHttpRequest.statusText property returns a DOMString containing the response's status message as returned by the HTTP server. Unlike XMLHTTPRequest.status which indicates a numerical status code, this property contains the text of the response status, such as "OK" or "Not Found". If the request's readyState is in UNSENT or OPENED state, the value of statusText will be an empty string.
     */
    @JsProperty
    private String statusText;

    @JsConstructor
    public XMLHttpRequest() {


    }

    /**
     * The XMLHttpRequest.abort() method aborts the request if it has already been sent. When a request is aborted, its readyState is set to 0 (UNSENT), but the readystatechange event is not fired.
     */
    @JsMethod
    public native void abort();

    /**
     * The XMLHttpRequest.getAllResponseHeaders() method returns all the response headers, separated by CRLF, as a string, or null if no response has been received.
     */
    @JsMethod
    public native String getAllResponseHeaders();

    /**
     * The XMLHttpRequest.getResponseHeader() method returns the string containing the text of the specified header. If there are multiple response headers with the same name, then their values are returned as a single concatenated string, where each value is separated from the previous one by a pair of comma and space. The getResponseHeader() method returns the value as a UTF byte squence.
     */
    @JsMethod
    public native String getResponseHeader(String type);

    /**
     * The XMLHttpRequest.open() method initializes a request. This method is to be used from JavaScript code; to initialize a request from native code, use openRequest() instead.
     */
    @JsMethod
    public native void open(String method, String url, boolean async, String user, String password);


    /**
     * The XMLHttpRequest.overrideMimeType() method overrides the MIME type returned by the server. This may be used, for example, to force a stream to be treated and parsed as text/xml, even if the server does not report it as such. This method must be called before send().
     */
    @JsMethod
    public native void overrideMimeType(String mimetype);

    /**
     * The XMLHttpRequest.send() method sends the request. If the request is asynchronous (which is the default), this method returns as soon as the request is sent. If the request is synchronous, this method doesn't return until the response has arrived. send() accepts an optional argument for the request body. If the request method is GET or HEAD, the argument is ignored and request body is set to null.
     */
    @JsMethod
    public native void send(String data);


    @JsMethod
    public native void send();


    @JsMethod
    public native void send(FormData data);


    @JsMethod
    public native void send(Blob data);

    @JsMethod
    public native void send(Document data);


    /**
     * The XMLHttpRequest.setRequestHeader() method sets the value of an HTTP request header. You must call setRequestHeader()after open(), but before send(). If this method is called several times with the same header, the values are merged into one single request header.
     */
    @JsMethod
    public native void setRequestHeader(String header, String value);


}
