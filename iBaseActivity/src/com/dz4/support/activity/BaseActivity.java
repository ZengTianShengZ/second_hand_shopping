package com.dz4.support.activity;

import com.dz4.support.R;
import com.dz4.support.app.IApplication;
import com.dz4.support.proxy.ActivityProxy;
import com.dz4.support.proxy.DialogProxy;
import com.dz4.support.proxy.DialogProxy.DialogExtProxiable;
import com.dz4.support.proxy.DialogProxy.DialogProxiable;
import com.dz4.support.proxy.ToastProxy.ToastProxiable;

import android.app.Activity;
import android.content.DialogInterface.OnCancelListener;
import android.os.Bundle;
import android.view.View.OnClickListener;
/**
 * ��װ���ù��ܵ�activity
 * ������˾���Ի�����
 * @author MZone
 *
 */
public class BaseActivity extends Activity implements ToastProxiable,DialogProxiable,DialogExtProxiable
{
	private ActivityProxy mActivityProxy;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO �Զ����ɵķ������
		super.onCreate(savedInstanceState);
		mActivityProxy=new ActivityProxy(this);
		
		//activity����ʱ������activity��������
		((IApplication)getApplication()).addActivity(this);
	}
	@Override
	protected void onDestroy() {
		// TODO �Զ����ɵķ������
		super.onDestroy();
		mActivityProxy.onDestroy();
	}
	@Override
	public void showToast(String text) {
		// TODO �Զ����ɵķ������
		mActivityProxy.showToast(text);
	}
	@Override
	public void showToast(int resourceId) {
		// TODO �Զ����ɵķ������
		mActivityProxy.showToast(resourceId);
	}
	/////////////////////////////////////////
	
	
	
	@Override
	public void showMsgDialog() {
		// TODO �Զ����ɵķ������
		mActivityProxy.showMsgDialog();
	}
	@Override
	public void showMsgDialogWithSize(int width, int height) {
		// TODO �Զ����ɵķ������
		mActivityProxy.showMsgDialogWithSize(width, height);
	}
	@Override
	public DialogProxy getDialogProxy() {
		// TODO �Զ����ɵķ������
		return mActivityProxy.getDialogProxy();
	}
	@Override
	public void cancelMsgDialog() {
		// TODO �Զ����ɵķ������
		mActivityProxy.cancelMsgDialog();
		
	}
	@Override
	public void showProgressDialog() {
		// TODO �Զ����ɵķ������
		mActivityProxy.showProgressDialog();
	}
	@Override
	public void cancelProgressDialog() {
		// TODO �Զ����ɵķ������
		mActivityProxy.showProgressDialog();
	}
	
	/****************************************
	 * 
	 * DialogExProxyable
	 * 
	 ******************************************/
	
	@Override
	public void showProgressDialog(String msg, OnCancelListener listener,
			boolean cancelable) {
		// TODO �Զ����ɵķ������
		mActivityProxy.showProgressDialog(msg, listener, cancelable);
	}
	@Override
	public void showProgressDialog(String msg) {
		// TODO �Զ����ɵķ������
		mActivityProxy.showProgressDialog(msg, null, true);
	}
	@Override
	public void showProgressDialog(int resid) {
		// TODO �Զ����ɵķ������
		mActivityProxy.showProgressDialog(getString(resid), null, true);
	}
	@Override
	public void showMsgDialog(String title, String detials, String btnLeft,
			String btnRight, OnClickListener btnLeftListener,
			OnClickListener btnRightListener) {
		// TODO �Զ����ɵķ������
		mActivityProxy.showMsgDialog(title, detials, btnLeft, btnRight, btnLeftListener, btnRightListener);
		
	}
	@Override
	public void showMsgDialog(String detials, String btnLeft,
			OnClickListener btnLeftListener) {
		// TODO �Զ����ɵķ������
		mActivityProxy.showMsgDialog(null, detials, btnLeft, null, btnLeftListener, null);
	}
	@Override
	public void showMsgDialog(String title, String detials, String btnLeft) {
		// TODO �Զ����ɵķ������
		mActivityProxy.showMsgDialog(title, detials, btnLeft, null, null, null);
	}
	@Override
	public void showMsgDialog(String detials, String btnLeft) {
		// TODO �Զ����ɵķ������
		mActivityProxy.showMsgDialog(null, detials, btnLeft, null, null, null);
	}
	@Override
	public void showMsgDialog(String detials) {
		// TODO �Զ����ɵķ������
		mActivityProxy.showMsgDialog(null, detials, getString(R.string.str_ok), null, null, null);
	}
	@Override
	public void showMsgDialog(int res) {
		// TODO �Զ����ɵķ������
		showMsgDialog(getString(res));
	}
}
