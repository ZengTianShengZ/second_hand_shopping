package com.zss.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.GridView;



import com.dz4.ishopping.R;
import com.zss.imageloader.MyAdapter;
import com.zss.imageloader.ZssMyAdapter;

public class ShowImgItemActivity extends Activity {
	List<String> mSelectedImage = new LinkedList<String>();
 
	public static ArrayList<String>  imgList = new ArrayList<String>();
	  GridView sGirdView;
	  ZssMyAdapter mAdapter;
	  String dd  = "sdd";
 
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.zss_activity_select_image);

		DisplayMetrics outMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
 
		intdata();

	}

	private void intdata() {
		  

		
		 Intent intent = getIntent();
		 Bundle bundle =intent.getExtras();
		 imgList =  bundle.getStringArrayList("SleImg");
		 Log.i("imgList", imgList.get(0));
		 
		 mSelectedImage.add("IMG_20151024_114029.jpg");
		 mSelectedImage.add("IMG_20151024_114029.jpg");
		 
		/* mAdapter = new ZssMyAdapter(getApplicationContext(),mSelectedImage,
					R.layout.zss_shoe_image, dd);
		 sGirdView.setAdapter(mAdapter);  */
 
		  
		  
		  
	}
}
