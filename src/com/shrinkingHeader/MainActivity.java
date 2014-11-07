package com.shrinkingHeader;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {
	ArrayAdapter<String> adapter;
	ListView list;
	TextView price;
	View firstChildInList;
	boolean FIRST_TIME = true;
	private int screenHeight;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		list = (ListView)findViewById(R.id.list);
		price = (TextView)findViewById(R.id.price);
		
		//populate list items
		String[] listItems = null ;
		listItems = new String[30];
		for(int i=0;i<30;i++){
			listItems[i] = "list Item : "+(i+1);
		}
		
		// set the height of priceTextView to half of the screen
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		setHeightForView(price, metrics.heightPixels/2-getActionBar().getHeight());
		screenHeight = metrics.heightPixels;
		
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItems);
		list.setAdapter(adapter);

		
		list.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {

			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,int visibleItemCount, int totalItemCount) {
				int scroll = getScroll(list);
				changeHeight(price,scroll);
				System.out.println("scroll "+scroll);
			}
		});
		
	}

	protected int getScroll(ListView listView) {// as list recycles views , getscrollY wont give us how much it has scrolled, hence we use this hack
		firstChildInList = listView.getChildAt(0);
		if(firstChildInList == null)return 0;
		return -firstChildInList.getTop() + listView.getFirstVisiblePosition() * firstChildInList.getHeight();
	}

	protected void setHeightForView(View v ,int h){ //  you need to set params to change height 
		LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)v.getLayoutParams(); 
		params.height = h;
		v.setLayoutParams(params);
	}
	protected void changeHeight(View view,int scroll) { // this is a simple logic , this is a little shaky , but its the way to go , you can smoothen from here
		int priceHeight = price.getHeight();
		if(priceHeight>=screenHeight/4 && priceHeight<=screenHeight/2){
			setHeightForView(view, screenHeight/2-scroll);
		}
		else if(priceHeight < screenHeight/4){
			
		}
	}
}
