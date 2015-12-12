package com.example.testdecorview;

import java.lang.reflect.Field;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.example.testdecorview.hierarchyserver.ViewServer;

public class MainActivity extends Activity {
	
	ViewGroup group = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		requestWindowFeature(Window.FEATURE_NO_TITLE);		
		super.onCreate(savedInstanceState);
		group = (ViewGroup)getWindow().getDecorView();
		group.removeAllViews();
		LayoutInflater.from(this).inflate(R.layout.activity_main, group, true);
		ViewServer.get(this).addWindow(this);
		init();
	}
	
	void init(){
		TextView text = (TextView)findViewById(R.id.text);
		text.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {	
				System.out.println("onClick down");			
			}
		});
	}
	
	int getFeature(){
		String name = "android.view.Window";
		try {
			Field field = Class.forName(name).getDeclaredField("mFeatures");
			field.setAccessible(true);
			return field.getInt(getWindow());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	
    public Resources getResources() {
    	return super.getResources();
    }
    
    @Override public Resources.Theme getTheme() {
        return super.getTheme();
    }
	
	public void onDestroy() {
		super.onDestroy();
		ViewServer.get(this).removeWindow(this);
	}

	public void onResume() {
		super.onResume();
		ViewServer.get(this).setFocusedWindow(this);
	}
}
