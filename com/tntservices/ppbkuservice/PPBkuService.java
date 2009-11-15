/**
 */
package com.tntservices.ppbkuservice;

import com.palm.luna.LSException;
import com.palm.luna.service.LunaServiceThread;
import com.palm.luna.service.ServiceMessage;
import com.palm.luna.message.ErrorMessage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Drew
 */

public class PPBkuService extends LunaServiceThread {
    private String name = "Palm Pre Backup Utility";
    private String version = "0.0.1114";

    @LunaServiceThread.PublicMethod 
	public void runBackup(ServiceMessage msg) throws JSONException, LSException, IOException 
    { 
	Process p = Runtime.getRuntime().exec("/opt/bin/ppbku"); 
	BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream())); 
	String line = null; 
	String output = ""; 
	while ((line = input.readLine()) != null) { 
	    output = output + line; 
	} 
	input.close(); 
	JSONObject reply = new JSONObject(); 
	reply.put("output", output); 
	msg.respond(reply.toString());  
    }

    @LunaServiceThread.PublicMethod 
	public void runExList(ServiceMessage msg) throws JSONException, LSException, IOException 
    { 
	if (msg.getJSONPayload().has("param")) { 
	    String prog = "/opt/bin/bldExList"; 
	    String param = msg.getJSONPayload().getString("param"); 
	    // FIXME %%% 'param' is tainted and must be sanitised here %%%
	    Process p = Runtime.getRuntime().exec(prog + " " + param); 
	    BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream())); 
	    String line = null; 
	    String output = ""; 
	    while ((line = input.readLine()) != null) {
		output = output + line; 
	    } 
	    input.close(); 
	    JSONObject reply = new JSONObject(); 
	    reply.put("output", output); 
	    msg.respond(reply.toString()); 
	}
	else {
	    msg.respondError(ErrorMessage.ERROR_CODE_INVALID_PARAMETER,
			     "Missing 'param' parameter");
	} 
    }

    @LunaServiceThread.PublicMethod 
	public void runIncList(ServiceMessage msg) throws JSONException, LSException, IOException 
    { 
	if (msg.getJSONPayload().has("param")) { 
	    String prog = "/opt/bin/bldIncList"; 
	    String param = msg.getJSONPayload().getString("param"); 
	    // FIXME %%% 'param' is tainted and must be sanitised here %%%
	    Process p = Runtime.getRuntime().exec(prog + " " + param); 
	    BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream())); 
	    String line = null; 
	    String output = ""; 
	    while ((line = input.readLine()) != null) {
		output = output + line; 
	    } 
	    input.close(); 
	    JSONObject reply = new JSONObject(); 
	    reply.put("output", output); 
	    msg.respond(reply.toString()); 
	}
	else {
	    msg.respondError(ErrorMessage.ERROR_CODE_INVALID_PARAMETER,
			     "Missing 'param' parameter");
	} 
    }

    @LunaServiceThread.PublicMethod
	public void version(ServiceMessage msg) 
	throws JSONException, LSException
    {
	JSONObject reply = new JSONObject(); 
	reply.put("name", this.name); 
	reply.put("version", this.version); 
	msg.respond(reply.toString());
    }

    @LunaServiceThread.PublicMethod
	public void status(ServiceMessage msg)
	throws JSONException, LSException
    {
	JSONObject reply = new JSONObject();
	reply.put("returnValue",true);
	msg.respond(reply.toString());
    }

}
