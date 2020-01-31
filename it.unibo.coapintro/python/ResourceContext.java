package it.unibo.coapintro.qak;

import static org.eclipse.californium.core.coap.CoAP.ResponseCode.CHANGED;
import static org.eclipse.californium.core.coap.CoAP.ResponseCode.CREATED;
import static org.eclipse.californium.core.coap.CoAP.ResponseCode.DELETED;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.CoapServer;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.eclipse.californium.core.server.resources.CoapExchange;

 
public class ResourceContext extends CoapResource {
private int counter = 0;

	public ResourceContext( String name ) {
		super(name);
		setObservable(true);
		System.out.println("Resource " + name + " | created  " );
	}
	
	@Override
	public void handleGET(CoapExchange exchange) {
		System.out.println("Resource " + getName() + " | handleGET from:" + 
				exchange.getSourceAddress() + " arg:" + exchange.getRequestText()  );
		exchange.respond("Resource " + getName() + " | counter = " + counter);
	}

/*
 * POST is NOT idempotent.	Use POST when you want to add a child resource
 */
	@Override
	public void handlePOST(CoapExchange exchange) {
		exchange.accept();		
		if (exchange.getRequestOptions().isContentFormat(MediaTypeRegistry.TEXT_XML)) {
			String xml = exchange.getRequestText();
			exchange.respond(CREATED, xml.toUpperCase());
			
		} else {
			// ...
			exchange.respond(CREATED);
		}
	}
	
/*
 * PUT method is idempotent. Use PUT when you want to modify 
 */

	@Override
	public void handlePUT(CoapExchange exchange) {
		String arg = exchange.getRequestText() ;		
		System.out.println("Resource " + getName() + " | PUT arg=" + arg );
		if( arg.equals("inc")) counter++;
		if( arg.equals("dec")) counter--;
		changed();	// notify all CoAp observers		
    	/*
    	 * Notifies all CoAP clients that have established an observe relation with
    	 * this resource that the state has changed by reprocessing their original
    	 * request that has established the relation. The notification is done by
    	 * the executor of this resource or on the executor of its parent or
    	 * transitively ancestor. If no ancestor defines its own executor, the
    	 * thread that has called this method performs the notification.
    	 */
		exchange.respond(CHANGED);
	}

	@Override
	public void handleDELETE(CoapExchange exchange) {
		delete();
		exchange.respond(DELETED);
	}
 
	public static void main(String[] args) {
		CoapServer server = new CoapServer();
		server.add( 
				new ResourceContext("ctxBasicRobot").add(
					new ResourceContext("basicrobot") )
		);
		server.start();
	}

}
