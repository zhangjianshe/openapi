package cn.mapway.openapi.viewer.client.util.xhr;

import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

import static jsinterop.annotations.JsPackage.GLOBAL;

@JsType(isNative=true, namespace=GLOBAL, name="Object")
public class BlobOptions {
	
	@JsProperty
	String type;
	
	@JsProperty
	String endings;
	
	
	public static class Builder {
		
		String type="";
		
		String endings="transparent";

		public Builder() {

		}
		
		
		public Builder type(String type){
			this.type = type;
			return this;
		}
		
		public Builder endings(String endings){
			
			this.endings = endings;
			return this;
		}
		
		
		public BlobOptions build(){
			BlobOptions options = new BlobOptions();
			options.endings = this.endings;
			options.type = this.type;
			
			return options;
		}

	}

}
