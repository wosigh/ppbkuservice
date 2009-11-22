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
    private String version = "0.1.1117";


	public static boolean isLegalString( String parameter )
	{
		boolean isValid=true;
			String validChars="abcdefghijklmnopqrstuvwxyz0123456789._-/\"\\";
		parameter=parameter.toLowerCase(); // case desensitize
		for (int i=0;i<parameter.length();i++)
		{
			char c=parameter.charAt(i);
			if (validChars.indexOf(c)== -1)
			{
				isValid=false;
				break;
			}
		}
		return isValid;
	}


	@LunaServiceThread.PublicMethod
	public void runBackup(ServiceMessage msg) throws InterruptedException, JSONException, LSException, IOException
	{ 
		try
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
			p.waitFor();
		}
		catch (LSException e) 
		{
			this.logger.severe("", e);
		}
	}
	@LunaServiceThread.PublicMethod
	public void runRestore(ServiceMessage msg) throws InterruptedException, JSONException, LSException, IOException
	{ 
		try
		{
			Process p = Runtime.getRuntime().exec("/opt/bin/pprstr"); 
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
			p.waitFor();
		}
		catch (LSException e) 
		{
			this.logger.severe("", e);
		}
	}

	@LunaServiceThread.PublicMethod
	public void runExList(ServiceMessage msg) throws InterruptedException, JSONException, LSException, IOException 
	{
		try
		{
			if (msg.getJSONPayload().has("param")) 
			{
				String prog = "/opt/bin/bldExList";
				String param = msg.getJSONPayload().getString("param");
				if (isLegalString(param))
				{
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
					p.waitFor();
				}
				else 
				{
					msg.respondError(ErrorMessage.ERROR_CODE_INVALID_PARAMETER, "Invalid value for 'param' parameter");
				}
			}
			else 
			{
			    msg.respondError(ErrorMessage.ERROR_CODE_INVALID_PARAMETER, "Missing 'param' parameter");
			}
		} 
		catch (LSException e) 
		{
			this.logger.severe("", e);
		}
	}
	@LunaServiceThread.PublicMethod 
	public void runIncList(ServiceMessage msg) throws InterruptedException, JSONException, LSException, IOException
	{
		try
		{
			if (msg.getJSONPayload().has("param")) 
			{
				String prog = "/opt/bin/bldIncList"; 
				String param = msg.getJSONPayload().getString("param");
				if (isLegalString(param))
				{
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
					p.waitFor();
				}
				else 
				{
					msg.respondError(ErrorMessage.ERROR_CODE_INVALID_PARAMETER, "Invalid value for 'param' parameter");
				}
			}
			else 
			{
				msg.respondError(ErrorMessage.ERROR_CODE_INVALID_PARAMETER, "Missing 'param' parameter");
			}
		}
		catch (LSException e) 
		{
			this.logger.severe("", e);
		}
	}
	@LunaServiceThread.PublicMethod
	public void version(ServiceMessage msg) throws JSONException, LSException
	{
		try
		{
			JSONObject reply = new JSONObject(); 
			reply.put("name", this.name); 
			reply.put("version", this.version); 
			msg.respond(reply.toString());
		}
		catch (LSException e) 
		{
			this.logger.severe("", e);
		}
	}
	@LunaServiceThread.PublicMethod
	public void status(ServiceMessage msg) throws JSONException, LSException
	{
		try
		{
			JSONObject reply = new JSONObject();
			reply.put("returnValue",true);
			msg.respond(reply.toString());
		}
		catch (LSException e) 
		{
			this.logger.severe("", e);
		}
	}
}