package javacode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapHandler;
import org.eclipse.californium.core.CoapObserveRelation;
import org.eclipse.californium.core.CoapResponse;

import alice.tuprolog.Struct;
import alice.tuprolog.Term;
import it.unibo.kactor.ApplMessage;
 
public class ResourceObserverRadar {
	private CoapObserveRelation relation = null;
	private CoapClient client            = null;
	
	public ResourceObserverRadar(){
		client = new CoapClient("coap://localhost:5683/robot/sonar");
		System.out.println("   ResourceObserverRadar | STARTS ");
		radarPojo.radarSupport.setUpRadarGui();
	}
	
	//Handle an observed value written as ApplMessage representation 
	protected String handleAsApplMessage(String content) {
		//content = msg(sonar,event,sonarOnRaspCoap,none,sonar(V),N)
		ApplMessage msg = new ApplMessage(content);
		System.out.println("ResourceObserverRadar | observes: " + msg.msgContent()  );
		Struct data     = (Struct) Term.createTerm( msg.msgContent() );
		String value    = data.getArg(0).toString();
		System.out.println("ResourceObserverRadar | observes: " + value );
		return value;
	}
	
	public void  observe( ) {
		System.out.println(" ResourceObserverRadar | observing ...  "  );
		relation = client.observe(
				new CoapHandler() {
					@Override public void onLoad(CoapResponse response) {
						//String content  = handleAsApplMessage(response.getResponseText());	
						String content  = response.getResponseText();
						System.out.println("ResourceObserverRadar | observes: " + content );
						radarPojo.radarSupport.update(content, "90");
					}					
					@Override public void onError() {
						System.err.println("OBSERVING FAILED (press enter to exit)");
					}
				});		
	}
	
	public void waitUserEnd() {
 		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
 		System.out.println("press enter to end ...");		
		try { br.readLine(); } catch (IOException e) { }		
		System.out.println("CANCELLATION");		
		relation.proactiveCancel();		
	}
	
	public static void main(String[] args) {
  		ResourceObserverRadar rco = new ResourceObserverRadar();
		rco.observe( );
		rco.waitUserEnd();
	}

}
