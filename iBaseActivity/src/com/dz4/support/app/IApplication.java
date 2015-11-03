package com.dz4.support.app;

import com.dz4.support.R;
import com.dz4.support.proxy.DialogProxy;
import com.dz4.support.service.LocalService;
import com.dz4.support.service.LocalService.LocalServiceBinder;
import com.dz4.support.utils.ActivityManagerUtils;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

public class IApplication extends Application {
	private NativeServiceConnection mServiceConnection;
	private LocalServiceBinder mLocalServiceBinder;
	@Override
	public void onCreate() {
		super.onCreate();
		
		//���öԻ���Ļ�������
		DialogProxy.setMsgDialogLayoutRes(R.layout.yi_dialog_template);
		DialogProxy.setMsgDialogTheme(R.style.Custom_Dialog_Dim);
		DialogProxy.setProgressDialogLayoutRes(R.layout.yi_progress_dialog_template);
		DialogProxy.setProgressDialogTheme(R.style.Custom_Dialog_Dim);
		
		//�������񣬲��󶨷���
		mServiceConnection = new NativeServiceConnection();
		Intent serviceItent =new Intent(getBaseContext(),LocalService.class);
		startService(serviceItent);
		bindService(serviceItent, mServiceConnection , BIND_AUTO_CREATE);
	}
	/**
	 * activity����
	 * 
	 * 
	 * @param ac
	 */
	public void addActivity(Activity ac){
		ActivityManagerUtils.getInstance().addActivity(ac);
	}
	
	public void exit(){
		ActivityManagerUtils.getInstance().removeAllActivity();
	}
	
	public Activity getTopActivity(){
		return ActivityManagerUtils.getInstance().getTopActivity();
	}
	/**
	 * �õ�ʵ������񽻻�����Binder
	 * 
	 * @return
	 */
	public LocalServiceBinder getLocalService(){
		return mLocalServiceBinder;
	}
	/**
	 * ����ʵ������񽻻���������
	 * 
	 * @return
	 */
	private class NativeServiceConnection implements ServiceConnection{

		@Override
		public void onServiceConnected(ComponentName name,
				IBinder service) {
			// TODO �Զ����ɵķ������
			mLocalServiceBinder=(LocalServiceBinder)service;
		}
		
		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO �Զ����ɵķ������
			mLocalServiceBinder=null;
		}
		
	}
}
