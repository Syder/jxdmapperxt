package com.vektor.jxdmapper;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {
	GameKeyApplicationsDialog gkad = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		View v = getWindow().getDecorView().findViewById(android.R.id.content);
		gkad = new GameKeyApplicationsDialog(v.getContext());
		if(!gkad.isShowing()){
			gkad.show();
		}
	}

	@Override
	protected void onResume(){
		super.onResume();
		if(!gkad.isShowing()) gkad.show();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}

}
