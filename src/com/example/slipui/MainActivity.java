package com.example.slipui;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;


public class MainActivity extends Activity {
	final static private String IMAGEVIEW_TAG = "icon bitmap";
	final static private String B_LIKE_TAG= "like button";
	final static private String B_OPTIONS_TAG= "option button";
	final static private String TEXTVIEW_TAG= "display";
	private static float Display_x;
	private static float Display_y;
	private static boolean get_drag_location=false;
	
	
	private TextView Display1;
	private TextView Display2;
	private Button Button_like;

	private Button Button_last;


	private DataBase database;
	private ActionBar actionBar;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
			
		
		showActionBar();
		
		
		
		ImageButton Button_attach = (ImageButton)findViewById(R.id.attach);
	    Button_attach.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent open_menu = new Intent("com.example.slipui.ATTACHACTIVITY");
				startActivity(open_menu);
				
			}
		});
		
		
	
		
		
		Display1 = (TextView) findViewById(R.id.display1);
		
		Display2 = (TextView) findViewById(R.id.display2);


		database = new DataBase();
		
		
		Button_like=(Button) findViewById(R.id.like);
		Button_like.setTag(B_LIKE_TAG);
		
		Button_like.setOnTouchListener(new View.OnTouchListener(){

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if(event.getAction()==MotionEvent.ACTION_DOWN){

					database.like();
					
					
					
					
				
					
					AnimationSet animationT = new AnimationSet(true);
					TranslateAnimation translate = new TranslateAnimation(
							Animation.RELATIVE_TO_SELF,0.0f,
							Animation.RELATIVE_TO_SELF,0.0f,
							Animation.RELATIVE_TO_SELF,0.0f,
							Animation.RELATIVE_TO_SELF,0.5f);
					
					AlphaAnimation alpha = new AlphaAnimation(0.8f,0.5f);
					
					alpha.setDuration(100);
					alpha.setFillAfter(true);
					translate.setDuration(200);
					translate.setInterpolator(new AccelerateInterpolator());
					animationT.addAnimation(translate);
					animationT.addAnimation(alpha);
					
					
					Display1.startAnimation(animationT);
					Display2.setText(database.peek());
					Display1.setText(database.next());
					
					AnimationSet animation = new AnimationSet(true);

					ScaleAnimation scale = new ScaleAnimation(
							1.0f,0.85f,1.0f,0.85f,
							Animation.RELATIVE_TO_SELF,0.5f,
							Animation.RELATIVE_TO_SELF,0.5f);

					scale.setDuration(80);
					
					animation.addAnimation(scale);

					animation.setFillAfter(true);
					v.startAnimation(animation);
					

					
				}
				if(event.getAction()==MotionEvent.ACTION_UP){
					
					
					AnimationSet animation = new AnimationSet(true);
					ScaleAnimation scale = new ScaleAnimation(
							0.8f,1.1f,0.8f,1.1f,
							Animation.RELATIVE_TO_SELF,0.5f,
							Animation.RELATIVE_TO_SELF,0.5f);
					
					scale.setDuration(100);

					animation.addAnimation(scale);
					v.startAnimation(animation);

				}

				return true;
			}
	
			
		});
		

		
		Button_last=(Button) findViewById(R.id.last);
		Button_last.setOnTouchListener(new View.OnTouchListener(){

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if(event.getAction()==MotionEvent.ACTION_DOWN){
					Display1.setText(database.last());
					Display1.append("\nNow like is "+database.getLike());
					
					AnimationSet animation = new AnimationSet(true);

					ScaleAnimation scale = new ScaleAnimation(
							1.0f,0.85f,1.0f,0.85f,
							Animation.RELATIVE_TO_SELF,0.5f,
							Animation.RELATIVE_TO_SELF,0.5f);

					scale.setDuration(80);
					
					animation.addAnimation(scale);

					animation.setFillAfter(true);
					v.startAnimation(animation);
				}
				if(event.getAction()==MotionEvent.ACTION_UP){
					
					
					AnimationSet animation = new AnimationSet(true);
					ScaleAnimation scale = new ScaleAnimation(
							0.8f,1.1f,0.8f,1.1f,
							Animation.RELATIVE_TO_SELF,0.5f,
							Animation.RELATIVE_TO_SELF,0.5f);
					
					scale.setDuration(100);

					animation.addAnimation(scale);
					v.startAnimation(animation);

				}
				return true;
			}
	
			
		});
		
		Display1.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				
				if(event.getAction()==MotionEvent.ACTION_DOWN){
					ClipData data = ClipData.newPlainText("", "");
					View.DragShadowBuilder shadowBuilder= new View.DragShadowBuilder(v);
					
					
					v.startDrag(data, shadowBuilder, v, 0);

					get_drag_location=false;
					return true;
					}
				
				
				return true;
			}
			
		});
		
		
		Display1.setOnDragListener(new View.OnDragListener(){
			@Override
			public boolean onDrag(View v, DragEvent event) {
				// TODO Auto-generated method stub
				
				switch(event.getAction()){
				case DragEvent.ACTION_DRAG_STARTED:
					Display1.setText(database.peek());
					Display1.append("\nNow like is "+database.getLike());
					
					break;
				case DragEvent.ACTION_DRAG_ENTERED:

					break;
				case DragEvent.ACTION_DRAG_LOCATION:
					Display_x =  event.getX();
					Display_y =  event.getY();
					get_drag_location=true;
					break;
				case DragEvent.ACTION_DRAG_EXITED:
					break;

				case DragEvent.ACTION_DROP:
					Display_x =  event.getX();
					Display_y =  event.getY();
					get_drag_location=true;
					break;
				case DragEvent.ACTION_DRAG_ENDED:
					
						if(Display_y>220.0f&&get_drag_location){					
							database.like();
							Display1.setText(database.next());
						}else{
//						if(Display_x<250.0f&&Display_x>50.0f){
							if(get_drag_location==true){
							Display1.setText(database.current());
							get_drag_location=false;
							}else{
								Display1.setText(database.next());
							}
						}
						Display1.append("\nNow like is "+database.getLike());
					
						
						
					
					break;
				default:
					break;
				}
				
				return true;
			}
		});
	}

	
	
	
	private void showActionBar(){
		
		
		
		actionBar=getActionBar();
		actionBar.show();
		LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflater.inflate(R.layout.custom_actionbar,null);
		
	    actionBar.setDisplayShowCustomEnabled(true);

	    actionBar.setCustomView(v);
	    
	    ImageButton Button_options = (ImageButton)findViewById(R.id.options);
	    Button_options.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent open_menu = new Intent("com.example.slipui.MENUACTIVITY");
				startActivity(open_menu);
				
			}
		});    
	}
	
	
	
	
}



