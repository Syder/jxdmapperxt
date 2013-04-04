package com.vektor.jxdmapper;

import java.util.concurrent.atomic.AtomicInteger;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.MeasureSpec;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsoluteLayout;
import android.widget.ImageButton;
import android.graphics.PorterDuff;

public class MapperButton {
	private ImageButton button, swipebutton;
	private GameKeyApplicationsDialog d;
	private AbsoluteLayout abs;
	private boolean swipe;
	float x;
	float y;

	@SuppressWarnings("deprecation")
	public MapperButton(int id, GameKeyApplicationsDialog d, AbsoluteLayout abs) {
		System.out.println((d == null) + " " + (abs == null));
		button = (ImageButton) d.findViewById(id);
		this.abs = abs;
		this.x = button.getX();
		this.y = button.getY();
		button.setOnTouchListener(d);
		button.setOnLongClickListener(d);
		this.swipe = false;
	}
	
	public ImageButton getSwipe(){
		return swipebutton;
	}
	
	public int getViewId() {
		return this.button.getId();
	}

	public boolean isSwipeOn() {
		return this.swipe;
	}

	public ImageButton getButton() {
		return button;
	}

	public void setVisibility(int visible) {
		// TODO Auto-generated method stub
		this.button.setVisibility(visible);
	}

	public int getWidth() {
		// TODO Auto-generated method stub
		return button.getWidth();
	}

	public void swipe() {
		System.out.println(abs == null);
		if (!swipe) {
			swipe = true;
			swipebutton = new ImageButton(abs.getContext());

			if (button.getX() <= 512) {
				if (button.getY() <= 300) {
					swipebutton.setX(button.getX() + 80);
					swipebutton.setY(button.getY() + 80);
				} else {
					swipebutton.setX(button.getX() + 80);
					swipebutton.setY(button.getY() - 80);
				}
			} else {
				if (button.getY() <= 300) {
					swipebutton.setX(button.getX() - 80);
					swipebutton.setY(button.getY() + 80);
				} else {
					swipebutton.setX(button.getX() - 80);
					swipebutton.setY(button.getY() - 80);
				}
			}
			swipebutton.setId((new AtomicInteger(Integer.MAX_VALUE))
					.decrementAndGet());
			swipebutton.setImageResource(getButtonImage());
			swipebutton.setBackgroundColor(Color.TRANSPARENT);
			Drawable d = swipebutton.getDrawable();
			d.mutate();
			d.setColorFilter(0xFF00FF00,Mode.MULTIPLY);
			swipebutton.setImageDrawable(d);
			swipebutton.setVisibility(View.VISIBLE);
			abs.addView(swipebutton);
			swipebutton.setOnTouchListener(new OnTouchListener() {
				@SuppressWarnings("deprecation")
				public boolean onTouch(View v, MotionEvent event) {
					if (event.getAction() == MotionEvent.ACTION_MOVE) {
						//moveView(v, event.getRawX(), event.getRawY());
						v.setX(event.getRawX()-v.getWidth()/2);
						v.setY(event.getRawY()-v.getHeight()/2);
					}
					return true;
				}
			});
		} else {
			swipe = false;
			swipebutton.setVisibility(View.GONE);
			abs.removeView(swipebutton);
		}
	}

	private int getButtonImage() {
		int bmp;

		switch (this.getViewId()) {
		case R.id.btn_a:
			bmp = R.drawable.button_a;
			break;
		case R.id.btn_b:
			bmp = R.drawable.button_b;
			break;
		case R.id.btn_x:
			bmp = R.drawable.button_x;
			break;
		case R.id.btn_y:
			bmp = R.drawable.button_y;
			break;
		case R.id.btn_l:
			bmp = R.drawable.button_l;
			break;
		case R.id.btn_r:
			bmp = R.drawable.button_r;
			break;
		case R.id.btn_l2:
			bmp = R.drawable.button_l2;
			break;
		case R.id.btn_r2:
			bmp = R.drawable.button_r2;
			break;
		case R.id.btn_select:
			bmp = R.drawable.button_select;
			break;
		case R.id.btn_start:
			bmp = R.drawable.button_start;
			break;
		case R.id.btn_volup:
			bmp = R.drawable.button_volup;
			break;
		case R.id.btn_voldn:
			bmp = R.drawable.button_voldn;
			break;
		case R.id.button_lpad_up:
			bmp = R.drawable.button_lpad_up;
			break;
		case R.id.button_lpad_left:
			bmp = R.drawable.button_lpad_left;
			break;
		case R.id.button_lpad_down:
			bmp = R.drawable.button_lpad_down;
			break;
		case R.id.button_lpad_right:
			bmp = R.drawable.button_lpad_right;
			break;
		case R.id.button_rpad_up:
			bmp = R.drawable.button_rpad_up;
			break;
		case R.id.button_rpad_left:
			bmp = R.drawable.button_rpad_left;
			break;
		case R.id.button_rpad_down:
			bmp = R.drawable.button_rpad_down;
			break;
		case R.id.button_rpad_right:
			bmp = R.drawable.button_rpad_right;
			break;
		default:
			bmp = -1;
			break;
		}
		return bmp;
	}

	public void moveView(View view, float x, float y) {
		view.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
				MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
		int left = (int) x - view.getMeasuredWidth() / 2;
		int top = (int) y - view.getMeasuredHeight() / 2;
		@SuppressWarnings("deprecation")
		ViewGroup.LayoutParams params = new AbsoluteLayout.LayoutParams(
				AbsoluteLayout.LayoutParams.WRAP_CONTENT,
				AbsoluteLayout.LayoutParams.WRAP_CONTENT, left, top);
		view.setLayoutParams(params);

	}

}
