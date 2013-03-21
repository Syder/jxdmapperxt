package com.vektor.jxdmapper;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.content.Context;
import android.content.ContextWrapper;
import android.util.Log;

public class ShInterface {


	
	public static boolean canSu(){
		boolean ret = false;
		Process su;
		try{
			su = Runtime.getRuntime().exec("su");
			
			DataOutputStream os = new DataOutputStream(su.getOutputStream());
			DataInputStream is = new DataInputStream(su.getInputStream());
			
			if(null != os && null != is){
				os.writeBytes("id\n");
				os.flush();
				
				String currUid = is.readLine();
				boolean exitSu = false;
				if(null == currUid)
				{
					ret = false;
					exitSu = false;
					Log.d("ROOT", "Can't get root access or denied by user.");
					
				}
				else if (true == currUid.contains("uid=0"))
				{
					ret = true;
					exitSu = true;
					Log.d("ROOT","Root access Granted");
				}
				else {
					ret=false;
					exitSu=true;
					Log.d("ROOT", "Root access rejected: "+currUid );
				}
				

	            if (exitSu)
	            {
	               os.writeBytes("exit\n");
	               os.flush();
	            }
			}
		}       catch (Exception e)
	      {
	         // Can't get root !
	         // Probably broken pipe exception on trying to write to output stream (os) after su failed, meaning that the device is not rooted

	         ret = false;
	         Log.d("ROOT", "Root access rejected [" + e.getClass().getName() + "] : " + e.getMessage());
	      }

	      return ret;
	}
	
	public final static boolean execute(String command){
		boolean retval = false;
	      
	      try
	      {
	         if (null != command && !command.equals(""))
	         {
	            Process suProcess = Runtime.getRuntime().exec("su");

	            DataOutputStream os = new DataOutputStream(suProcess.getOutputStream());

	            // Execute command that require root access
	            os.writeBytes(command+"\n");
	            os.writeBytes("exit\n");
	            os.flush();
	            //retval = true;
	            
	            try
	            {
	               int suProcessRetval = suProcess.waitFor();
	               if (255 != suProcessRetval)
	               {
	                  // Root access granted
	                  retval = true;
	               }
	               else
	               {
	                  // Root access denied
	                  retval = false;
	               }
	            }
	            catch (Exception ex)
	            {
	               Log.e("ROOT", "Error executing root action", ex);
	            }
	            
	         }
	      }
	      catch (IOException ex)
	      {
	         Log.w("ROOT", "Can't get root access", ex);
	         retval = false;
	      }
	      catch (SecurityException ex)
	      {
	         Log.w("ROOT", "Can't get root access", ex);
	         retval = false;
	      }
	      catch (Exception ex)
	      {
	         Log.w("ROOT", "Error executing internal operation", ex);
	         retval = false;
	      }
	      
	      return retval;
	   }

	public static boolean keyEnable(Context context){
		ContextWrapper cw = new ContextWrapper(context);
		String fname = cw.getFilesDir().getAbsolutePath()+File.separator+"gamekeyValue";
		String cmd = "cat " + fname + " > /sys/devices/platform/mx-adcjs.0/key";
		Log.d("KeyMapper","Sending command to execute:"+cmd);
		boolean bool = execute(cmd);
		return bool;
	}
	
	
}
	

