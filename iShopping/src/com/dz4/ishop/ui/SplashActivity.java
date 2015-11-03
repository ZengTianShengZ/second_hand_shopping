package com.dz4.ishop.ui;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.dz4.ishop.app.IshopApplication;
import com.dz4.ishopping.R;
import com.dz4.support.activity.BaseActivity;
/**
 * 
 * ����Ӧ�ó���򿪳�ʼ������ʾ��ӭ����
 * @author MZone
 *
 */
public class SplashActivity extends  BaseActivity{
	private static final int MSG_LOADING_TIMEOUT = 0x00;
	private static final int LOADING_DELAYED = 2000;
	private Handler mHandler=new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO �Զ����ɵķ������
			super.handleMessage(msg);
			switch(msg.what){
				case MSG_LOADING_TIMEOUT:
					launch();
					break;
			}
		}
	};

	private void launch() {
		// TODO �Զ����ɵķ������
		Intent intent =new Intent(SplashActivity.this,MainActivity.class);
		startActivity(intent);
		finish();
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		IshopApplication application = (IshopApplication)getApplication();
		if (application.isFirstLaunch()) {
			application.setFirstLaunch(false);
			setContentView(R.layout.activity_splash);
			mHandler.sendEmptyMessageDelayed(MSG_LOADING_TIMEOUT,
					LOADING_DELAYED);
		} else {
			launch();
		}
		
	}

	
}
