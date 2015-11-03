package com.zss.imageloader;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.widget.ImageView;
 

import com.dz4.ishopping.R;
import com.zss.utils.CommonAdapter;
import com.zss.utils.ViewHolder;

public class ZssMyAdapter extends CommonAdapter<String>{

	/** 
     * 文件夹路径 
     */
	private  List<String>  mDirPath;
	
	public ZssMyAdapter(Context context, List<String> mDatas, int itemLayoutId, List<String> dirPath) {
		super(context, mDatas, itemLayoutId);
		this.mDirPath = dirPath;
	}

	@Override
	public void convert(ViewHolder helper, String item) {
		 int i = helper.getPosition();
		//    /storage/sdcard0/DCIM/Camera
		 // 设置图片  
		helper.setImageByUrl(R.id.id_show_image,mDirPath.get(i)+ "/" + item);
		
		final ImageView mImageView = helper.getView(R.id.id_show_image);
	}

}
