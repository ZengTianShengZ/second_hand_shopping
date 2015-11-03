package com.dz4.ishop.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import cn.bmob.v3.Bmob;

import com.dz4.ishop.frag.Fragment_chat;
import com.dz4.ishop.frag.Fragment_home;
import com.dz4.ishop.frag.Fragment_me;
import com.dz4.ishop.listener.TitlechangeListener;
import com.dz4.ishop.utils.Constant;
import com.dz4.ishop.view.BottomTagView;
import com.dz4.ishopping.R;
import com.dz4.support.activity.FragmentBaseActivity;

/**
 * 
 * @author MZone
 *
 */
public class MainActivity extends FragmentBaseActivity implements TitlechangeListener{
	private BottomTagView BottomTag_home;
	private BottomTagView BottomTag_chat;
	private BottomTagView BottomTag_me;
	
	private Fragment_home mFragment_home;
	private Fragment_chat mFragment_chat;
	private Fragment_me mFragment_me;
	
	private FragmentManager  mFragmentManager ;
	private FragmentTransaction mTransaction;
	
	private TextView texttitle;
	private View page1;
	private View page2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Bmob.initialize(getApplicationContext(),Constant.BMOB_APP_ID);
		 
		initView();
		setSelected(0);
		BottomTag_home.setIconAlpha(1);
		//initIntentData();
	}

	private void initIntentData() {
		 Intent intent = getIntent();
		 Bundle bundle = intent.getExtras();
		 String IntentData = bundle.getString("IntentData");
		 if(IntentData == null){
			 return;
		 }else{
			 if(IntentData.equals("ShowImageActivity")){
				 setSelected(1);
			 } 
		 }
		  
	}

	@Override
	public void processHandlerMessage(Message msg) {
		// TODO 自动生成的方法存根
		
	}

	public void initView() {
		// TODO 自动生成的方法存根
		BottomTag_home= (BottomTagView) findViewById(R.id.BottomTag_home);
		BottomTag_chat= (BottomTagView) findViewById(R.id.BottomTag_chat);
		BottomTag_me= (BottomTagView) findViewById(R.id.BottomTag_me);
		texttitle = (TextView) this.findViewById(R.id.titleText);
		page1=this.findViewById(R.id.page1);
		page2=this.findViewById(R.id.page2);
		
	}
	public void initData() {
		// TODO 自动生成的方法存根
		
	}
	public void onTagClick(View v) {
		resetTag();
		// TODO 自动生成的方法存根
		switch (v.getId()) {
		case R.id.BottomTag_home:
			BottomTag_home.setIconAlpha(1);
			setSelected(0);
			break;
		case R.id.BottomTag_chat:
			BottomTag_chat.setIconAlpha(1);
			setSelected(1);
			break;
		case R.id.BottomTag_me:
			BottomTag_me.setIconAlpha(1);
			setSelected(2);	
				break;
		default:
			break;
		}
	}
	private void setSelected(int index) {
		// TODO 自动生成的方法存根
		mFragmentManager =getSupportFragmentManager();
		mTransaction = mFragmentManager.beginTransaction();
		hideFragment(mTransaction);
		switch(index){
		case 0:
			if(mFragment_home==null){
				mFragment_home =new Fragment_home(this);
				mTransaction.add(R.id.fragment_zone, mFragment_home);
			}else{
				mTransaction.show(mFragment_home);
			}
			changePage(mFragment_home.getCurrentPage());
			switch(mFragment_home.getCurrentPage()){
				case 0:
					changeTitle(R.string.title_qiang_all);
					break;
				case 1:
					changeTitle(R.string.title_qiang_focus);
					break;
				case 2:
					changeTitle(R.string.title_qiang_fav);
					break;
			
			}
			break;
		case 1:
			if(mFragment_chat==null){
				mFragment_chat =new Fragment_chat(this);
				mTransaction.add(R.id.fragment_zone, mFragment_chat);
			}else{
				mTransaction.show(mFragment_chat);
			}
			changeTitle(R.string.title_chat);
			break;
		case 2:
			if(mFragment_me==null){
				mFragment_me =new Fragment_me();
				mTransaction.add(R.id.fragment_zone, mFragment_me);
			}else{
				mTransaction.show(mFragment_me);
			}
			changeTitle(R.string.title_me);
			break;
			
		default:
			break;
		}
		mTransaction.commit();
	}

	private void hideFragment(FragmentTransaction mTransaction) {
		// TODO 自动生成的方法存根
		changePage(4);
		if(mFragment_home!=null){
			mTransaction.hide(mFragment_home);
		}
		if(mFragment_chat!=null){
			mTransaction.hide(mFragment_chat);
		}
		if(mFragment_me!=null){
			mTransaction.hide(mFragment_me);
		}
		
	}

	protected void resetTag(){
		BottomTag_home.setIconAlpha(0);
		BottomTag_me.setIconAlpha(0);
		BottomTag_chat.setIconAlpha(0);
	}

	@Override
	public void changeTitle(String title) {
		texttitle.setText(title);
	}
	@Override
	public void changeTitle(int titleRes) {
		texttitle.setText(getString(titleRes));
	}

	@Override
	public void changePage(int page) {
		// TODO 自动生成的方法存根
		switch(page){
		case 0:
			page1.setVisibility(View.VISIBLE);
			page2.setVisibility(View.INVISIBLE);
			break;
		case 1:
			page1.setVisibility(View.INVISIBLE);
			page2.setVisibility(View.VISIBLE);
			break;
		default:
			page1.setVisibility(View.INVISIBLE);
			page2.setVisibility(View.INVISIBLE);
			break;
		}
		
	}
 	@Override
	protected void onActivityResult(int arg0,int  arg1,Intent arg2){ 
		super.onActivityResult(arg0, arg1, arg2);
		setSelected(1);
	} 
	 
}
