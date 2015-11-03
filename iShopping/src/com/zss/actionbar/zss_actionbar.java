package com.zss.actionbar;

 

 

import java.security.PublicKey;

import com.dz4.ishopping.R;
import com.zss.bean.ImageFloder;
import com.zss.imageloader.ListImageDirPopupWindow.OnImageDirSelected;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class zss_actionbar extends RelativeLayout {

	private LayoutInflater mInflater;
	private RelativeLayout mBarView;
	private ImageView leftImage,rightImage;
	private TextView barText;
	private LinearLayout leftLil,rightLil,centerLil;
	
	private int IMGNUM_1 = 1;
	
	 public zss_actionbar(Context context, AttributeSet attrs) {
		 super(context, attrs);
	     mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	     mBarView = (RelativeLayout) mInflater.inflate(R.layout.zss_actionbar, null);
	     addView(mBarView);
	     
	     leftLil = (LinearLayout) mBarView.findViewById(R.id.zss_actionbar_leftLil);
	     rightLil = (LinearLayout) mBarView.findViewById(R.id.zss_actionbar_righttLil);
	     centerLil = (LinearLayout) mBarView.findViewById(R.id.zss_actionbar_centertLil);
	     
	     leftImage =  (ImageView) mBarView.findViewById(R.id.zss_actionbar_leftImg);
	     rightImage =  (ImageView) mBarView.findViewById(R.id.zss_actionbar_righImg);
	     
	     barText =  (TextView) mBarView.findViewById(R.id.zss_actionbar_Tv);
	     
	     
 	    leftImage.setOnClickListener(new actionBarClicListener());
 	    // rightImage.setOnClickListener(new actionBarClicListener());
	     
	 }
	 public void setBarText(String str){
		 barText.setText(str);
	 }
	 public void setLeftImage(int id) {
	     leftImage.setImageResource(id);
	 }
	 public void setRightImage(int id) {
		 rightImage.setImageResource(id);
	 }
	 public ImageView getRightImage() {
		return rightImage;
	}
	 public ImageView getLeftImage() {
			return leftImage;
		}
	 public void setViewGone(int flag){
		 
		 
		 if(flag == 1){
			 barText.setVisibility(View.GONE);
		 }
		 if(flag == 2){
			 leftImage.setVisibility(View.GONE);
		 }
		 if(flag == 3){
			 rightImage.setVisibility(View.GONE);
			  
		 }
		 if(flag == 4){
			 leftImage.setVisibility(View.GONE);
			 barText.setVisibility(View.GONE);
		 }
		 if(flag == 5){
			 rightImage.setVisibility(View.GONE);
			 barText.setVisibility(View.GONE);
		 }
		 if(flag == 6){
			 rightImage.setVisibility(View.VISIBLE);
			 leftImage.setVisibility(View.VISIBLE);
			 barText.setVisibility(View.VISIBLE);
		 }
	 }
 	 public class actionBarClicListener implements View.OnClickListener{
	 

		@Override
		    public void onClick(View view) {
		 
				if(view == leftImage){
					if(imageOnClickListener != null){
						imageOnClickListener.leftImageOnClickListener();
					}
					 
				}
				if(view == rightImage){
					if(imageOnClickListener != null){
						imageOnClickListener.rightImageOnClickListener();
					}
 				
				 }
		    }
  
	 } 
 
	 public interface ImageOnClickListener{
		 void leftImageOnClickListener();
		 void rightImageOnClickListener();
		}
	 private ImageOnClickListener imageOnClickListener;
	 
	 public void xxx(ImageOnClickListener imageOnClickListener)
		{
			this.imageOnClickListener = imageOnClickListener;
		} 
/*	 public interface initActionBar{
		 public void setBarText(String str);
		 public void setLeftImage(int id);
		 public void setRightImage(int id);
		 
		 public void leftImageOnClickListener();
		 public void rightImageOnClickListener();
		 
		 public void setViewGone(int flag);
	 }*/
}
