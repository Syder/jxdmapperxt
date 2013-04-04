package com.vektor.jxdmapper;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Environment;
import android.view.View;


public class FileSystemInterface {
	private final static View v = null;
	
	
	

	private static String convertToString(InputStream fin) {
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(fin));
		StringBuilder sb = new StringBuilder();
		String line = null;
		try {
			while((line = reader.readLine())!=null){
				sb.append(line).append("\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
	
	
	public static ArrayList<String> getProfiles(Context context){
		ArrayList<String> profiles = new ArrayList<String>();
		File file = context.getDir("profiles", Context.MODE_PRIVATE); 
		 profiles.addAll(Arrays.asList(file.list()));
		return profiles;
	}
	
	public static boolean writeGamekeyValue(String content, Context ctx){
		String fname = "gamekeyValue";
		try {
			FileOutputStream fo = ctx.openFileOutput(fname, Context.MODE_PRIVATE);
			fo.write(content.getBytes());
			fo.close();
			return true;
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		return false;
	}
	public static boolean saveScreenshot(Bitmap bmp, Context ctx){
		String fname = "ss.jpg";
		try {
			FileOutputStream fo = ctx.openFileOutput(fname, Context.MODE_PRIVATE);
			bmp.compress(Bitmap.CompressFormat.JPEG, 90, fo);
		    fo.flush();
			fo.close();
			return true;
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		return false;
	}
	public static String readGameKeyValue( Context context) {
		String fname = "gamekeyValue";
		FileInputStream fin;
		try {
			fin = context.openFileInput(fname);
			String set = convertToString(fin);
			fin.close();
			return set;
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		return null;
	}
	public static boolean saveProfile(String pname, String content, Context context){
		ContextWrapper cw = new ContextWrapper(context);
		String fname = cw.getFilesDir().getAbsolutePath()+File.separator+"profiles"+File.separator+pname;
		File mydir = context.getDir("profiles", Context.MODE_PRIVATE); 
		File fileWithinMyDir = new File(mydir, pname);
		try {
			FileOutputStream fo = new FileOutputStream(fileWithinMyDir);
			fo.write(content.getBytes());
			fo.close();
			return true;
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
		
		
	}
	
	
	public static boolean gameKeyValueExists(Context ctx) {
		
		ContextWrapper cw = new ContextWrapper(ctx);
		String fname = cw.getFilesDir().getAbsolutePath()+File.separator+"gamekeyValue";
		File file = new File(fname);
		return file.exists();

	}
	
	public static void saveData(Boolean boolean1, Context ctx) {
		ContextWrapper cw = new ContextWrapper(ctx);
		String fname = cw.getFilesDir().getAbsolutePath()+File.separator+"gamekeyValue";
		File file = new File(fname);
		if (boolean1 == true) {
			if (!file.exists()) {
				file.mkdir();
			}
		}
		if (boolean1 == false) {
			if (file.exists()) {
				//file.delete();
			}
		}
	}



	public static void deleteProfile(String selectedProfile, Context context) {
		File mydir = context.getDir("profiles", Context.MODE_PRIVATE);
		File file = new File(mydir, selectedProfile);
		if(file.exists()) file.delete();
	}



	public static String getProfile(String selectedProfile, Context context) {
		File mydir = context.getDir("profiles", Context.MODE_PRIVATE); 
		File file = new File(mydir, selectedProfile);
		FileInputStream fin;
		try {
			fin = new FileInputStream(file);
			String set = convertToString(fin);
			fin.close();
			return set;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	
}
