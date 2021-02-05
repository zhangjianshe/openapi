package cn.mapway.openapi.viewer.client.util.xhr;

import jsinterop.annotations.*;

import static jsinterop.annotations.JsPackage.GLOBAL;

@JsType(isNative = true, namespace = GLOBAL)
public class Blob {

	@JsConstructor
	public Blob(String[] data, BlobOptions options) {

	}

	@JsConstructor
	public Blob(Blob[] data, BlobOptions options) {

	}

	
	@JsProperty
	private boolean isClosed;
	
	@JsProperty
	private int size;
	
	@JsProperty
	private String type;
	
    @JsOverlay
	public final boolean isClosed() {
		return this.isClosed;
	}

    @JsOverlay
	public final int getSize() {
		return this.size;
	}

    @JsOverlay
	public final String getType() {
		return this.type;
	}
    
    @JsMethod
    public native void close();
    
    @JsMethod
    public native Blob slice(int start, int end, String contentType);

}
