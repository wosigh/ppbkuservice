/**
 */
package com.tntservices.ppbkuservice;

import com.palm.luna.LSException;
import com.palm.luna.service.LunaServiceThread;
import com.palm.luna.service.ServiceMessage;
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
    			while ((line = input.readLine()) != null) 
    			{ 
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
		if (msg.getJSONPayload().has("param")) 
		{ 
			String prog = "/opt/bin/bldExList"; 
			String param = msg.getJSONPayload().getString("param"); 
			Process p = Runtime.getRuntime().exec(prog + " " + param); 
			BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream())); 
			String line = null; 
			String output = ""; 
			while ((line = input.readLine()) != null) 
			{
				output = output + line; 
			} 
			input.close(); 
			JSONObject reply = new JSONObject(); 
			reply.put("output", output); 
			msg.respond(reply.toString()); 
		}
		else
		{
			msg.respondError("DANGIT", "THAT DAWG DON'T HUNT"); 
		} 
	}
	@LunaServiceThread.PublicMethod 
	public void runIncList(ServiceMessage msg) throws JSONException, LSException, IOException 
	{ 
		if (msg.getJSONPayload().has("param")) 
		{ 
			String prog = "/opt/bin/bldIncList"; 
			String param = msg.getJSONPayload().getString("param"); 
			Process p = Runtime.getRuntime().exec(prog + " " + param); 
			BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream())); 
			String line = null; 
			String output = ""; 
			while ((line = input.readLine()) != null) 
			{
				output = output + line; 
			} 
			input.close(); 
			JSONObject reply = new JSONObject(); 
			reply.put("output", output); 
			msg.respond(reply.toString()); 
		}
		else
		{
			msg.respondError("CRAP", "ARE YOU OBTUSE?"); 
		} 
	}
	@LunaServiceThread.PublicMethod 
	public void runCMod(ServiceMessage msg) throws JSONException, LSException, IOException 
	{ 
		if (msg.getJSONPayload().has("param")) 
		{ 
			String prog = "chmod 755"; 
			String param = msg.getJSONPayload().getString("param"); 
			Process p = Runtime.getRuntime().exec(prog + " " + param); 
			BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream())); 
			String line = null; 
			String output = ""; 
			while ((line = input.readLine()) != null) 
			{
				output = output + line; 
			} 
			input.close(); 
			JSONObject reply = new JSONObject(); 
			reply.put("output", output); 
			msg.respond(reply.toString()); 
		}
		else
		{
			msg.respondError("POPP!", "Hear that, popp? That's your head popping out of your arse!"); 
		} 
	}
	@LunaServiceThread.PublicMethod 
	public void runCmd(ServiceMessage msg) throws JSONException, LSException, IOException 
	{ 
		if (msg.getJSONPayload().has("prog"))
		{
				if (msg.getJSONPayload().has("param")) 
				{ 
					String prog = msg.getJSONPayload().getString("prog"); 
					String param = msg.getJSONPayload().getString("param"); 
					Process p = Runtime.getRuntime().exec(prog + " " + param); 
					BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream())); 
					String line = null; 
					String output = ""; 
					while ((line = input.readLine()) != null) 
					{
						output = output + line; 
					} 
					input.close(); 
					JSONObject reply = new JSONObject(); 
					reply.put("output", output); 
					msg.respond(reply.toString()); 
				}
				else
				{
					String prog = msg.getJSONPayload().getString("prog"); 
					Process p = Runtime.getRuntime().exec(prog); 
					BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream())); 
					String line = null; 
					String output = ""; 
					while ((line = input.readLine()) != null) 
					{
						output = output + line; 
					} 
					input.close(); 
					JSONObject reply = new JSONObject(); 
					reply.put("output", output); 
					msg.respond(reply.toString()); 
				}
		}
		else
		{
			msg.respondError("IDIOT!", "YOU MUST SPECIFY PARAMETERS, DILLHOLE!"); 
		} 
	}	
	@LunaServiceThread.PublicMethod
	public void version(ServiceMessage msg) 
	{
        try 
        {
		StringBuilder sb = new StringBuilder(8192);
		sb.append("{\"general\":[");
		sb.append("{name:" + JSONObject.quote(this.name) + "},");
		sb.append("{version:" + JSONObject.quote(this.version) + "}");
		sb.append("]}");
          msg.respond(sb.toString());
        }
        catch (LSException e) 
        {
          this.logger.severe("", e);
        }
	}

    @LunaServiceThread.PublicMethod
	public void status(ServiceMessage msg)
	throws JSONException, LSException {
	if (ipkgReady) {
	    JSONObject reply = new JSONObject();
	    reply.put("returnValue",true);
	    msg.respond(reply.toString());
	} else
	    ipkgDirNotReady(msg);
    }

}
