package com.dz4.support.utils;

import java.util.Map;
import java.util.Set;

import android.content.Context;
import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;
/**
 * 
 * ������
 * @author MZone
 *
 */
public class UtilsTools
{
	private UtilsTools()
	{

	}
	/**
	 * 
	 *�ж�String�Ƿ�Ϸ�
	 * @param str
	 * @return
	 */
	public static boolean isStringInvalid(String str)
	{
		if (str == null || str.length() < 1)
		{
			return true;
		}
		return false;
	}
	/**
	 * 
	 * �㲥����
	 * @param context
	 * @param action
	 * @param params
	 */
	public static void broadcast(Context context, String action,
			Map<String, String> params)
	{
		Intent intent = new Intent(action);
		if (params != null)
		{
			Set<String> keys = params.keySet();
			for (String string : keys)
			{
				intent.putExtra(string, params.get(string));
			}
		}
		context.sendBroadcast(intent);
	}
}
