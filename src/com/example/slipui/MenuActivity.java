package com.example.slipui;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class MenuActivity extends Activity {
	String Options[] = { " Option1"," Option2"," Option3"," Option4"," Option5"};
	
	private myListView list1;
	private myListView list2;
	private myListView list3;
	private ActionBar actionBar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_menu);
		
		showActionBar();
		
		list1 = (myListView) findViewById(R.id.list1);
		list2 = (myListView) findViewById(R.id.list2); 
		list3 = (myListView) findViewById(R.id.list3);
		
		
		int ID[] = { R.id.menu_icon,R.id.menu_text}; 
		
		ArrayList<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();
		
		for(int i=0; i<Options.length; i++){
			HashMap<String,Object> map = new HashMap<String,Object>();
			
			map.put("menu_icon", R.id.menu_icon);
			map.put("menu_text", Options[i]);
		
			
			list.add(map);
		}
		
		
		SimpleAdapter adapter= new SimpleAdapter(this,list,
				R.layout.menulist,
				new String[] {"menu_icon","menu_text"},ID);
		
		list1.setAdapter(adapter);
		list2.setAdapter(adapter);
		list3.setAdapter(adapter);
		
		//		setListAdapter(new ArrayAdapter<String>(MenuActivity.this,android.R.layout.simple_list_item_1,Options));
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		
		
		
		return super.onTouchEvent(event);
	}
	
	private void showActionBar(){
		
		
		
		actionBar=getActionBar();
		actionBar.show();
		LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflater.inflate(R.layout.menu_actionbar,null);
		
	    actionBar.setDisplayShowCustomEnabled(true);

	    actionBar.setCustomView(v);
	    
	    ImageButton Button_menu_to_main = (ImageButton)findViewById(R.id.menu_to_main);
	   
	    Button_menu_to_main.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				Intent open_menu = new Intent("android.intent.action.MAIN");
//				startActivity(open_menu);
				MenuActivity.this.finish();
			}
		});    
	}
	
}



	class myListView extends ListView{

		public myListView(Context context) {
			super(context);
			// TODO Auto-generated constructor stub
		}
		
		public myListView(Context context,AttributeSet attrs) {
			super(context,attrs);
			// TODO Auto-generated constructor stub
		}
		public myListView(Context context,AttributeSet attrs, int defStyle) {
			super(context,attrs,defStyle);
			// TODO Auto-generated constructor stub
		}
		
		
		@Override
		protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
			// TODO Auto-generated method stub
			int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,MeasureSpec.AT_MOST);
			
			super.onMeasure(widthMeasureSpec, expandSpec);
		}
		
	}


