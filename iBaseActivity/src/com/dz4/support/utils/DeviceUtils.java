package com.dz4.support.utils;

import android.content.Context;
import android.util.DisplayMetrics;
/**
 * 
 * �豸������
 * 
 * @author MZone
 *
 */
public class DeviceUtils {
	public static DisplayMetrics getDisplayMetrics(Context context)
	{
		return context.getResources().getDisplayMetrics();
	}
}
