package com.vektor.jxdmapper;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;
import android.widget.AbsoluteLayout;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Toast;

import java.util.ArrayList;






@SuppressWarnings("deprecation")
public class GameKeyApplicationsDialog extends Dialog implements
		OnClickListener, OnTouchListener, OnItemClickListener {
	
	// Elements for debugging support
	// private static final String LOG_TAG = "GameKeyApplicationsDialog";
	//private static final boolean DBG_FORCE_EMPTY_LIST = false;

	Context context;
	View mNoAppsText;
	TextView switchTextView;
	final static int X_MODIFY = 0;
	final static int Y_MODIFY = 0;
	static double F_X_MODIFY = 0;
	static double F_Y_MODIFY = 0;
	//private ImageButton button_down, button_left, button_right, button_up;
	private ImageButton button_x, button_y, button_a, button_b;
	private ImageButton button_r, button_r2, button_l, button_l2;
	private ImageButton button_select,button_start,button_volup,button_voldn;
	private Button btn_Able, btn_Disable, reset;
	// private Button bt_modeswitch;
	private int absoluteLayoutMove_xSpan, absoluteLayoutMove_ySpan;
	private int  absoluteLayoutMove_afterX = 0;
	private int  absoluteLayoutMove_afterY = 0;
	AbsoluteLayout absoluteLayoutMove;
	Configuration configScreen;
	private int mode=1;
	private Button button_mode1, button_mode2;
	private Button button_left_bigger, button_left_smaller;
	private Button button_right_bigger, button_right_smaller;
	private ImageButton ib_analog_l, ib_analog_r, ib_analog_view;
	private SeekBar sensitivity_view;
	private Bitmap leftBitmap,rightBitmap;
	private double leftScale=1.0,rightScale=1.0;
	private Button button_profile_del, button_profile_load,button_profile_new;
	private ListView profile_list;
	private String selectedProfile = "";
	LinearLayout zoom;
	//-xin-add
	private LinearLayout leftLayout,rightLayout,viewLayout;
	private AlertDialog.Builder builder;
	private AlertDialog.Builder builder2;
	private Activity a;
	private ResetListener mListener;
	
	public GameKeyApplicationsDialog(Context context, Activity a) {
		super(context,R.style.Theme_Translucent);
		this.context = context;
		final Resources resources = context.getResources();
		this.a=a;
		// mIconSize = (int)
		// resources.getDimension(android.R.dimen.app_icon_size);
		
		try {
            mListener = (ResetListener) a;
        } catch (ClassCastException e) {
            throw new ClassCastException(a.toString() + " must implement OnItemSelectedListener");
        }
	}
	

	
	/**
	 * We create the recent applications dialog just once, and it stays around
	 * (hidden) until activated by the user.
	 * 
	 * @see PhoneWindowManager#showRecentAppsDialog
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Context context = getContext();
		
		Window window = getWindow();
		//window.requestFeature(Window.FEATURE_NO_TITLE);
		//window.setType(WindowManager.LayoutParams.TYPE_SYSTEM_DIALOG);
		//window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		//		WindowManager.LayoutParams.FLAG_FULLSCREEN);
		//window.setTitle("Recents");

		
		setContentView(R.layout.gamekey_dialog);

		final WindowManager.LayoutParams params = window.getAttributes();
		params.width = 1024;
		params.height = 600;
		window.setAttributes(params);
		window.setFlags(0, WindowManager.LayoutParams.FLAG_DIM_BEHIND);
		findViewByIdInit();
		buttonListener();
		listPopulate();
		builder = new AlertDialog.Builder(context);
		builder2 = new AlertDialog.Builder(context);
		
		leftBitmap = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.button_analog_l);
		rightBitmap = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.button_analog_r);
		ib_analog_l.setImageBitmap(leftBitmap);
		ib_analog_r.setImageBitmap(rightBitmap);
		
		modeSwitch(mode);
		
		
		
	}

	public void findViewByIdInit() {
		button_x = (ImageButton) findViewById(R.id.btn_x);
		button_y = (ImageButton) findViewById(R.id.btn_y);
		button_a = (ImageButton) findViewById(R.id.btn_a);
		button_b = (ImageButton) findViewById(R.id.btn_b);

		button_l = (ImageButton) findViewById(R.id.btn_l);
		button_r = (ImageButton) findViewById(R.id.btn_r);
		button_l2 = (ImageButton) findViewById(R.id.btn_l2);
		button_r2 = (ImageButton) findViewById(R.id.btn_r2);

		ib_analog_l = (ImageButton) findViewById(R.id.ib_analog_l);
		ib_analog_r = (ImageButton) findViewById(R.id.ib_analog_r);
		ib_analog_view = (ImageButton) findViewById(R.id.ib_analog_view);

		btn_Able = (Button) findViewById(R.id.btn_Able);
		btn_Disable = (Button) findViewById(R.id.btn_Disable);
		reset = (Button) findViewById(R.id.reset);

		//button_up = (ImageButton) findViewById(R.id.fangxiang_up);
		//button_down = (ImageButton) findViewById(R.id.fangxiang_down);
		//button_left = (ImageButton) findViewById(R.id.fangxiang_left);
		//button_right = (ImageButton) findViewById(R.id.fangxiang_right);
		
		button_select = (ImageButton) findViewById(R.id.btn_select);
		button_start = (ImageButton) findViewById(R.id.btn_start);
		button_volup = (ImageButton) findViewById(R.id.btn_volup);
		button_voldn = (ImageButton) findViewById(R.id.btn_voldn);
		

		button_mode1 = (Button) findViewById(R.id.btn_mode1);
		button_mode1.setSelected(true);
		button_mode2 = (Button) findViewById(R.id.btn_mode2);
		button_left_bigger = (Button) findViewById(R.id.bt_left_bigger);
		button_left_smaller = (Button) findViewById(R.id.bt_left_smaller);
		button_right_bigger = (Button) findViewById(R.id.bt_right_bigger);
		button_right_smaller = (Button) findViewById(R.id.bt_right_smaller);

		absoluteLayoutMove = (AbsoluteLayout) findViewById(R.id.absoluteLayoutMove);
		sensitivity_view=(SeekBar) findViewById(R.id.sensitivity);
		
		rightLayout=(LinearLayout) findViewById(R.id.rocker_right);
		viewLayout=(LinearLayout) findViewById(R.id.rocker_view);
		leftLayout=(LinearLayout) findViewById(R.id.rocker_left);
		

		button_profile_del = (Button) findViewById(R.id.bt_profile_del);
		button_profile_load = (Button) findViewById(R.id.bt_profile_load);
		button_profile_new = (Button) findViewById(R.id.bt_profile_new);
		
		profile_list = (ListView) findViewById(R.id.listProfile);
		

	}
	
	public void listPopulate(){
		
		ArrayList<String> lst = FileSystemInterface.getProfiles(context);
		final ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, 
		R.layout.row, R.id.profileName, lst); 
		profile_list.setAdapter(adapter);
	}
	
	public void buttonListener() {
		button_x.setOnTouchListener(this);
		button_y.setOnTouchListener(this);
		button_a.setOnTouchListener(this);
		button_b.setOnTouchListener(this);
		button_l.setOnTouchListener(this);
		button_r.setOnTouchListener(this);
		button_l2.setOnTouchListener(this);
		button_r2.setOnTouchListener(this);
		
		button_select.setOnTouchListener(this);
		button_start.setOnTouchListener(this);
		button_volup.setOnTouchListener(this);
		button_voldn.setOnTouchListener(this);


		btn_Able.setOnClickListener(this);
		btn_Disable.setOnClickListener(this);
		reset.setOnClickListener(this);
		button_mode1.setOnClickListener(this);
		button_mode2.setOnClickListener(this);

		ib_analog_l.setOnTouchListener(this);
		ib_analog_r.setOnTouchListener(this);
		ib_analog_view.setOnTouchListener(this);

		button_left_bigger.setOnClickListener(this);
		button_right_bigger.setOnClickListener(this);
		button_left_smaller.setOnClickListener(this);
		button_right_smaller.setOnClickListener(this);

		absoluteLayoutMove.setOnTouchListener(this);
		
		button_profile_del.setOnClickListener(this);
		button_profile_load.setOnClickListener(this);
		button_profile_new.setOnClickListener(this);
		
		profile_list.setOnItemClickListener(this);
		

	}

	private void modeSwitch(int m){
		switch(m){
		case 1:
			button_mode1.setSelected(true);
			button_mode2.setSelected(false);
			button_mode1.setTextColor(Color.GREEN);
			button_mode2.setTextColor(Color.WHITE);
			
			ib_analog_view.setVisibility(View.VISIBLE);
			ib_analog_r.setVisibility(View.GONE);
			leftLayout.setVisibility(View.VISIBLE);
			rightLayout.setVisibility(View.GONE);
			viewLayout.setVisibility(View.VISIBLE);
			mode=1;
			break;
		case 2:
			button_mode1.setSelected(false);
			button_mode2.setSelected(true);
			button_mode1.setTextColor(Color.WHITE);
			button_mode2.setTextColor(Color.GREEN);
			ib_analog_view.setVisibility(View.GONE);
			ib_analog_r.setVisibility(View.VISIBLE);
			leftLayout.setVisibility(View.VISIBLE);
			rightLayout.setVisibility(View.VISIBLE);
			viewLayout.setVisibility(View.GONE);
			mode=2;
			break;
		default: return;
		}
	}
	
	
	public void moveView(View view, float x, float y) {
		view.measure(
        MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
        MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
		int left = (int) x - view.getMeasuredWidth() / 2;
		int top = (int) y - view.getMeasuredHeight() / 2;
		@SuppressWarnings("deprecation")
		ViewGroup.LayoutParams params = new AbsoluteLayout.LayoutParams(
				AbsoluteLayout.LayoutParams.WRAP_CONTENT,
				AbsoluteLayout.LayoutParams.WRAP_CONTENT, left, top);
		view.setLayoutParams(params);
		
	}



	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (v.getId()) {
		case R.id.btn_x:
		case R.id.btn_y:
		case R.id.btn_a:
		case R.id.btn_b:
		case R.id.btn_l:
		case R.id.btn_r:
		case R.id.btn_l2:
		case R.id.btn_r2:
		case R.id.btn_select:
		case R.id.btn_start:
		case R.id.btn_volup:
		case R.id.btn_voldn:
		case R.id.ib_analog_l:
		case R.id.ib_analog_r:
		case R.id.ib_analog_view:
			if (event.getAction() == MotionEvent.ACTION_MOVE){
				moveView(v, event.getRawX(), event.getRawY());
			}
			break;
		case R.id.absoluteLayoutMove:
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				absoluteLayoutMove_xSpan = (int) event.getX();
				absoluteLayoutMove_ySpan = (int) event.getY();
			}
			if (event.getAction() == MotionEvent.ACTION_MOVE) {
				absoluteLayoutMove_afterX = (int) event.getRawX();
				absoluteLayoutMove_afterY = (int) event.getRawY();
				@SuppressWarnings("deprecation")
				ViewGroup.LayoutParams layParams = new AbsoluteLayout.LayoutParams(
						AbsoluteLayout.LayoutParams.WRAP_CONTENT, //
						AbsoluteLayout.LayoutParams.WRAP_CONTENT, //
						absoluteLayoutMove_afterX - absoluteLayoutMove_xSpan
								+ X_MODIFY, //
						absoluteLayoutMove_afterY - absoluteLayoutMove_ySpan
								- Y_MODIFY);
				absoluteLayoutMove.setLayoutParams(layParams);
			}
			break;
		}
		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		final String selected = arg0.getItemAtPosition(arg2).toString();
		selectedProfile=selected;

	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		
		case R.id.bt_profile_del:
			if(!selectedProfile.equals("")) deleteDialog();
			break;
		
		case R.id.bt_profile_load:
			if(!selectedProfile.equals("")) loadProfile(selectedProfile);
			break;
		
		case R.id.bt_profile_new:
			saveDialog();
			break;
		
		case R.id.btn_mode1:
			modeSwitch(1);
			break;
		case R.id.btn_mode2:
			modeSwitch(2);
			break;
		case R.id.btn_Able:
			String analogValue=createDemonString();
			Log.i("xin_log","------------------------------------");
			Log.i("xin_log",analogValue);
		
			FileSystemInterface.writeGamekeyValue(analogValue, context);
		
			ShInterface.keyEnable(context);
			
			dismiss();
			goToHome();
			break;
		case R.id.btn_Disable:
			
			FileSystemInterface.writeGamekeyValue("0", context);
			
			ShInterface.keyEnable(context);
			
			FileSystemInterface.saveData(false, context);
			dismiss();
			goToHome();
			break;
		case R.id.bt_left_bigger:
			leftScale=leftScale+0.1;
			if(leftScale<0.5)leftScale=0.5;
			if(leftScale>1.5)leftScale=1.5;
			scale(ib_analog_l,leftBitmap,leftScale);
			break;
		case R.id.bt_left_smaller:
			leftScale=leftScale-0.1;
			if(leftScale<0.5)leftScale=0.5;
			if(leftScale>1.5)leftScale=1.5;
			scale(ib_analog_l,leftBitmap,leftScale);
			break;
		case R.id.bt_right_bigger:
			rightScale=rightScale+0.1;
			if(rightScale<0.5)rightScale=0.5;
			if(rightScale>1.5)rightScale=1.5;
			scale(ib_analog_r,rightBitmap,rightScale);
			break;
		case R.id.bt_right_smaller:
			rightScale=rightScale-0.1;
			if(rightScale<0.5)rightScale=0.5;
			if(rightScale>1.5)rightScale=1.5;
			scale(ib_analog_r,rightBitmap,rightScale);
			break;
		case R.id.reset:
			FileSystemInterface.writeGamekeyValue("0", context);
			ShInterface.keyEnable(context);
			FileSystemInterface.saveData(false, context);
			dismiss();
			goToHome();
			//mListener.resetDialog();
			break;

		}

	}
	private void loadProfile(String selectedProfile) {
		String ps = FileSystemInterface.getProfile(selectedProfile,context);
		if(null!=ps){
			FileSystemInterface.writeGamekeyValue(ps, context);
			setButtonPositions(ps);
			Toast.makeText(context, "Loaded - "+selectedProfile, Toast.LENGTH_SHORT).show();
			
			ShInterface.keyEnable(context);
			
			selectedProfile="";
		}
		
	}

	@SuppressWarnings("deprecation")
	private void setButtonPositions(String ps) {
		ps=ps.replace("\r", "");
		ps=ps.replace("\n","");
		String [] demon = ps.split("\\s+");
		//0 	1		2		3	4	5	6	7	8	9	10	11	12	13	14	15	16	 17	  18	19   20   21     22
		//1, circle_x, circle_y, r, ax, ay, bx, by, xx, xy, yx, yy, lx, ly, rx, ry, l2x, l2y, r2x, r2y, view,view_x, view_y
		//2, circle_x, circle_y, r, ax, ay, bx, by, xx, xy, yx, yy, lx, ly, rx, ry, l2x, l2y, r2x, r2y, view_x,view_y, view_r
		
		//1 diffs: view (sensitivity),view_x,view_y
		//2 diffs: view_x, view_y, view_r
		
		moveView(ib_analog_l,Float.parseFloat(demon[1]),Float.parseFloat(demon[2]));
		leftScale=(leftBitmap.getWidth()/2)/Float.parseFloat(demon[3]);
		scale(ib_analog_l,leftBitmap,leftScale);
		moveView(button_a,Float.parseFloat(demon[4]),Float.parseFloat(demon[5]));
		moveView(button_b,Float.parseFloat(demon[6]),Float.parseFloat(demon[7]));
		moveView(button_x,Float.parseFloat(demon[8]),Float.parseFloat(demon[9]));
		moveView(button_y,Float.parseFloat(demon[10]),Float.parseFloat(demon[11]));
		moveView(button_l,Float.parseFloat(demon[12]),Float.parseFloat(demon[13]));
		moveView(button_l2,Float.parseFloat(demon[16]),Float.parseFloat(demon[17]));
		moveView(button_r,Float.parseFloat(demon[14]),Float.parseFloat(demon[15]));
		moveView(button_r2,Float.parseFloat(demon[18]),Float.parseFloat(demon[19]));
		//if in mode 1..
		if(demon[0].equals("1")){ 
			modeSwitch(1); 
			moveView(ib_analog_view,Float.parseFloat(demon[21]),Float.parseFloat(demon[22]));
			sensitivity_view.setProgress(Integer.parseInt(getCheckedVisual())-1);
		} 
		//if in mode 2..
		if(demon[0].equals("2")){ 
			modeSwitch(2);
			rightScale=(rightBitmap.getWidth()/2)/Float.parseFloat(demon[22]);
			scale(ib_analog_r,rightBitmap,rightScale);
			moveView(ib_analog_r,Float.parseFloat(demon[20]),Float.parseFloat(demon[21]));
			
		}
		
	}
	

	private String createDemonString(){
		final String split=" ";
		StringBuilder sb=new StringBuilder();
		if(mode==1){
			sb.append("1"+split);
		}else{
			sb.append("2 "+split);
		}
		int[] location=new int[2];
		getViewCenter(ib_analog_l,location);
		sb.append(location[0]+split+location[1]+split);
		sb.append(ib_analog_l.getWidth()/2+split);
		getViewCenter(button_a,location);
		sb.append(location[0]+split+location[1]+split);
		getViewCenter(button_b,location);
		sb.append(location[0]+split+location[1]+split);
		getViewCenter(button_x,location);
		sb.append(location[0]+split+location[1]+split);
		getViewCenter(button_y,location);
		sb.append(location[0]+split+location[1]+split);
		getViewCenter(button_l,location);
		sb.append(location[0]+split+location[1]+split);
		getViewCenter(button_r,location);
		sb.append(location[0]+split+location[1]+split);
		getViewCenter(button_l2,location);
		sb.append(location[0]+split+location[1]+split);
		getViewCenter(button_r2,location);
		sb.append(location[0]+split+location[1]+split);
		if(mode==1){
			sb.append(getCheckedVisual()+split);
			getViewCenter(ib_analog_view,location);
			sb.append(location[0]+split+location[1]);
		}else{
			getViewCenter(ib_analog_r,location);
			sb.append(location[0]+split+location[1]+split);
			sb.append(ib_analog_r.getWidth()/2);
		}
		return sb.toString();
	}
	
	private void getViewCenter(View v,int[] p){
		v.getLocationOnScreen(p);
		p[0]+=v.getWidth()/2;
		p[1]+=v.getHeight()/2;
	}

	private String getCheckedVisual(){
		String[]values={"1","2","3","4","5"};
		return values[sensitivity_view.getProgress()];
	}
	
	private void scale(ImageButton view,Bitmap bitmap, double scale) {
		int primaryWidth=bitmap.getWidth();
		int primaryHeight=bitmap.getHeight();
		Matrix matrix = new Matrix();
		matrix.postScale((float) scale, (float) scale);
		Bitmap newBmp = Bitmap.createBitmap(bitmap, 0, 0, primaryWidth,
				primaryHeight, matrix, true);
		view.setImageBitmap(newBmp);
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		super.show();
		
	}

	private void goToHome(){
		Intent homeIntent= new Intent(Intent.ACTION_MAIN);
		homeIntent.addCategory(Intent.CATEGORY_HOME);
		homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(homeIntent);
	}
	
	DialogInterface.OnClickListener dialogDeleteClickListener = new DialogInterface.OnClickListener() {
	    @Override
	    public void onClick(DialogInterface dialog, int which) {
	        switch (which){
	        case DialogInterface.BUTTON_POSITIVE:
	            //Yes button clicked
	        	FileSystemInterface.deleteProfile(selectedProfile,context);
	        	selectedProfile="";
	        	listPopulate();
	            break;

	        case DialogInterface.BUTTON_NEGATIVE:
	            //No button clicked
	        	
	            break;
	        }
	    }
	};

	private void saveDialog(){
		final EditText input = new EditText(context);
		builder2
	    .setTitle(context.getResources().getString(R.string.profile_save_title))
	    .setMessage(context.getResources().getString(R.string.profile_save_Message))
	    .setView(input)
	    .setPositiveButton(context.getResources().getString(R.string.dialog_yes), new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int whichButton) {
	            Editable name = input.getText(); 
	            FileSystemInterface.saveProfile(name.toString(), createDemonString(), context);
	            FileSystemInterface.writeGamekeyValue(createDemonString(), context);
	            ShInterface.keyEnable(context);
	            listPopulate();
	        }
	    })
	    .setNegativeButton(context.getResources().getString(R.string.dialog_no), new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int whichButton) {
	            
	        }
	    }).show();

		
	}
	private void deleteDialog(){
		builder.setTitle(context.getResources().getString(R.string.profile_del)+" "+selectedProfile+":")
		.setMessage(context.getResources().getString(R.string.delete_message))
		.setPositiveButton(context.getResources().getString(R.string.dialog_yes), dialogDeleteClickListener)
		.setNegativeButton(context.getResources().getString(R.string.dialog_no), dialogDeleteClickListener).show();
	}
	
	public interface ResetListener{
		public void resetDialog();
	}
	

}
