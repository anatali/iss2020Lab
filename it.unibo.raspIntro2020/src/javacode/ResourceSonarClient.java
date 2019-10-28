package javacode;

import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.coap.MediaTypeRegistry;

public class ResourceSonarClient {
private CoapClient client;

	public ResourceSonarClient( String address, String path) { //"coap://localhost:5683/" + url
		String url = address + "/" + path;
		client = new CoapClient( url );
		System.out.println("ResourceSonarClient | STARTS for: " +  url);
	}
	
	public void updateTheResource( String msg ) {
		CoapResponse resp = client.put(msg, MediaTypeRegistry.TEXT_PLAIN);
		System.out.println("ResourceSonarClient | RESPONSE CODE: " + resp.getCode());		
	}
}
