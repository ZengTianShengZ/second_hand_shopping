package com.dz4.ishop.frag;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.dz4.ishop.listener.TitlechangeListener;
import com.dz4.ishop.ui.MainActivity;
import com.dz4.ishopping.R;
import com.dz4.support.widget.BaseFragment;
import com.zss.imageloader.MyAdapter;
import com.zss.imageloader.ShowImageActivity;
import com.zss.imageloader.ZssMyAdapter;
/**
 * 
 * 购物车的fragment
 * @author MZone
 *
 */
public class Fragment_chat extends  BaseFragment{
	private View rootview;
	private  Bundle savedInstanceState;
    private EditText editText;
    private ImageView imageView;
    private Context context;
    
    private GridView gridView;
    private ZssMyAdapter zssMyAadapter;
    
    private List<String> imglist = new ArrayList<String>();
    
    private  ArrayList<String>  imgItem = new ArrayList<String>();
    
    private int REQUEST_CODE_NUM1 = 1;
	public Fragment_chat(MainActivity mainActivity) {
		 this.context = mainActivity;
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		return rootview;
	}
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		rootview =getLayoutInflater(savedInstanceState).inflate(R.layout.fragment_chat, null);
		editText = (EditText) rootview.findViewById(R.id.fragment_char_edt);
		imageView = (ImageView) rootview.findViewById(R.id.fragment_char_img);
		imageView.setOnClickListener(new  onClickListener());
		
		gridView = (GridView) rootview.findViewById(R.id.fragment_char_gridView);
		
	};
	@Override
	public void processHandlerMessage(Message msg) {
		// TODO 自动生成的方法存根
		
	}

	public void initView() {
		// TODO 自动生成的方法存根
		 
	}

	public void initData() {
		// TODO 自动生成的方法存根
		
	}
	@Override
	public void initEvent() {
		// TODO 自动生成的方法存根
		//editText.setOnClickListener(new onClickListener());
		 
	}
	
	
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
 
		String str = data.getStringExtra("IntentData");
		if(TextUtils.isEmpty(str)){
			return;
		}else if(str.equals("ShowImageActivity")){
			//arg2.getStringArrayListExtra("imgItem");
			Log.i("ffffddd",str);
			Log.i("ffffiii",data.getStringArrayListExtra("imgItem").get(1));
			
			/*imgItem = data.getStringArrayListExtra("imgItem");
			for(int i=0;i<imgItem.size()-1;i++){
				 
				imglist.add(imgItem.get(i));
				Log.i("imlise", imgItem.get(i));
			}
			imglist.add("IMG_20151024_114013.jpg");
			imglist.add("IMG_20151024_114013.jpg");
			imglist.add("IMG_20151024_114013.jpg");
			imglist.add("IMG_20151024_114013.jpg");*/
			
			zssMyAadapter = new ZssMyAdapter(context,data.getStringArrayListExtra("imgItem"),
					R.layout.zss_shoe_image, data.getStringArrayListExtra("imgDirPath"));
			gridView.setAdapter(zssMyAadapter); 
		}
	}



	class onClickListener implements View.OnClickListener {

		@Override
		public void onClick(View view) {
 		 
				 Log.i("img", "imgg1");
				 Intent intent  = new Intent(context,ShowImageActivity.class);
				 startActivityForResult(intent, REQUEST_CODE_NUM1);
				 Log.i("img", "imgg2");
		}
	};
}
