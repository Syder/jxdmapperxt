package com.vektor.jxdmapper;

import java.io.File;
import java.io.OutputStream;

import com.vektor.jxdmapper.GameKeyApplicationsDialog.ResetListener;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;

public class MainActivity extends Activity implements ResetListener {
	GameKeyApplicationsDialog gkad = null;
	View v = null;
	Activity a = this;
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		v = getWindow().getDecorView().findViewById(android.R.id.content);
		
		//FileSystemInterface.saveScreenshot(bitmap, v.getContext());
		setContentView(R.layout.activity_main);
		
		gkad = new GameKeyApplicationsDialog(v.getContext(),a);
		
		if(!gkad.isShowing()){
			gkad.dismiss();
			gkad.show();
		}
	}
	

	
	@Override
	protected void onResume(){
		super.onResume();
		if(!gkad.isShowing()) { 
			gkad.dismiss();
			gkad.show();
		}
	}
	@Override
	protected void onPause(){
		super.onPause();
		gkad.dismiss();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}

	@Override
	public void resetDialog() {
		gkad = new GameKeyApplicationsDialog(v.getContext(),a);
		if(!gkad.isShowing()){
			gkad.dismiss();
			gkad.show();
		}
	}
	public void onUserLeaveHint() { // this only executes when Home is selected.
        // do stuff
        super.onUserLeaveHint();
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	//super.onKeyDown(keyCode, event);
    	if((keyCode == KeyEvent.KEYCODE_BACK)){}
        return false;
    }
	
}
