package com.zss.imageloader;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dz4.ishop.listener.TitlechangeListener;
import com.dz4.ishop.ui.MainActivity;
import com.dz4.ishopping.R;
import com.zss.actionbar.zss_actionbar;
 
import com.zss.actionbar.zss_actionbar.ImageOnClickListener;
import com.zss.activity.ShowImgItemActivity;
import com.zss.bean.ImageFloder;
import com.zss.imageloader.ListImageDirPopupWindow.OnImageDirSelected;

public class ShowImageActivity extends Activity implements OnImageDirSelected,ImageOnClickListener
{
	private ProgressDialog mProgressDialog;

	List<String>  SelectedImageList = new LinkedList<String>();
	public static ArrayList<String>  imgItem = new ArrayList<String>();
	public static ArrayList<String>  imgDirPath = new ArrayList<String>();
	/**
	 * 閻庢稒锚閸嬪秹寮崶锔筋偨濠㈣鎷�閼垫垿鎯冮崟顐ｇ闁绘娲﹂弳鐔兼煂閿燂拷	 */
	private int mPicsSize;
	/**
	 * 闁搞儱澧芥晶鏍极娴兼潙娅ら柡鍫嫹椤﹀潡鎯冮崟顒佺�濞寸姾娉涢敓锟�	 */
	private File mImgDir;
	/**  
	 * 闁圭鎷峰﹢渚�儍閸曨偅绂堥柣妤嬫嫹
	 */
	private List<String> mImgs;

	private GridView mGirdView;
	private MyAdapter mAdapter;
	private zss_actionbar actionbar;
	/**
	 * 濞戞挸鐡ㄥ鍌炴儍閸曨喚绐￠柛鏂烘櫇鐞氼偊鏁嶉敓锟介敓鏂ゆ嫹濞存粌閰ｅΣ璇差潰閵忕姵鍊卞☉鎿勬嫹闁叉粓寮崶锔筋偨濠㈣泛婀卞▓鎴炲緞濮橆収鍋ч柟娈垮亝閿燂拷
	 */
	private HashSet<String> mDirPaths = new HashSet<String>();

	/**
	 * 闁规鍋呭鍧楀箯閸喖鐓傞柟纰夋嫹濠�線鎯冮崟顐ｇ闁绘娲﹂弸鍐╃鐠烘亽浠�
	 */
	private List<ImageFloder> mImageFloders = new ArrayList<ImageFloder>();

	private RelativeLayout mBottomLy;

	private TextView mChooseDir;
	private TextView mImageCount;
	private TextView  texttitle;
 
	int totalCount = 0;

	private int mScreenHeight;

	private ListImageDirPopupWindow mListImageDirPopupWindow;

	private Handler mHandler = new Handler()
	{
		public void handleMessage(android.os.Message msg)
		{
			mProgressDialog.dismiss();
			//为View绑定数据  
			data2View();
			//为View绑定数据  
			initListDirPopupWindw();
		}
	};

	private ImageOnClickListener sx;

	/** 
     * 为View绑定数据 
     */  
	private void data2View()
	{
		if (mImgDir == null)
		{
			Toast.makeText(getApplicationContext(), "没扫描到任何图片",
					Toast.LENGTH_SHORT).show();
			return;
		}

		//Flie.list() 返回�?��字符串数组，这些字符串指定此抽象路径名表示的目录中的文件和目录�? 
		mImgs = Arrays.asList(mImgDir.list());
		/** 
         * 可以看到文件夹的路径和图片的路径分开保存，极大的减少了内存的消�?�?
         */  
		// mImgs 图片路径
		//mImgDir.getAbsolutePath() 图片文件夹路�?	
		Log.i("mImgDir", mImgDir.list().toString());
		Log.i("mimgs", mImgs.get(0));
		Log.i("mAbsolutePath", mImgDir.getAbsolutePath());
		mAdapter = new MyAdapter(getApplicationContext(), mImgs,
				R.layout.zss_grid_item, mImgDir.getAbsolutePath());
		mGirdView.setAdapter(mAdapter);
		mImageCount.setText(totalCount + "张");
	};

	/** 
     * 初始化展示文件夹的popupWindw 
     */ 
	private void initListDirPopupWindw()
	{
		mListImageDirPopupWindow = new ListImageDirPopupWindow(
				LayoutParams.MATCH_PARENT, (int) (mScreenHeight * 0.7),
				mImageFloders, LayoutInflater.from(getApplicationContext())
						.inflate(R.layout.zss_list_dir, null));

		mListImageDirPopupWindow.setOnDismissListener(new OnDismissListener()
		{

			@Override
			public void onDismiss()
			{
				// 设置背景颜色变暗 
				WindowManager.LayoutParams lp = getWindow().getAttributes();
				lp.alpha = 1.0f;
				getWindow().setAttributes(lp);
			}
		});
		// 设置选择文件夹的回调   
		mListImageDirPopupWindow.setOnImageDirSelected(this);
	}
 
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.zss_activity_select_image);

		DisplayMetrics outMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
		mScreenHeight = outMetrics.heightPixels;
 
		initView();
		getImages();
		initEvent();

	}

	/** 
     * 利用ContentProvider扫描手机中的图片，此方法在运行在子线程中 完成图片的扫描，�?��获得jpg�?��的那个文件夹 
     */  
	/*
	 * getImages主要就是扫描图片的代码，
	 * 我们�?��了一个Thread进行扫描，扫描完成以后，
	 * 我们得到了图片最多文件夹路径（mImgDir），
	 * 手机中图片数量（totalCount）；以及�?��包含图片文件夹信息（mImageFloders�?
	 */
	private void getImages()
	{
		if (!Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED))
		{
			Toast.makeText(this, "暂无外部存储", Toast.LENGTH_SHORT).show();
			return;
		}
		// 显示进度�?
		mProgressDialog = ProgressDialog.show(this, null, "图片正在加载�?..");

		new Thread(new Runnable()
		{
			@Override
			public void run()
			{

				String firstImage = null;
		 
				//MediaStore包括了多媒体数据库的�?��信息，包括音频，
				Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
				
				ContentResolver mContentResolver = ShowImageActivity.this
						.getContentResolver();

				// 只查询jpeg和png的图�? 
				/*
				 * public final Cursor query (Uri uri, String[] projection,String selection,String[] selectionArgs, String sortOrder)
				 * 第一个参数： Uri
				 * 第二个参数：projection 返回 Uri对应的内�?，ID和NAME �?= null  就只返回 NAME
				 * 第三个参数：selection，设置条件，相当于SQL语句中的where�?
				 * 第四个参数：selectionArgs，这个参数是要配合第三个参数使用的，如果你在第三个参数里面有？，将第四个参数赋给 第三个参�?�? �?
				 * 第五个参数，sortOrder，按照什么进行排序，相当于SQL语句中的Order by。如果想要结果按照ID的降序排�?
				 * 
				 * 它返回的查询结果�?��Cursor，这个Cursor就相当于数据库查询的中Result
				 */
				Cursor mCursor = mContentResolver.query(mImageUri, null,
						MediaStore.Images.Media.MIME_TYPE + "=? or "
								+ MediaStore.Images.Media.MIME_TYPE + "=?",
						new String[] { "image/jpeg", "image/png" },
						MediaStore.Images.Media.DATE_MODIFIED);

				Log.e("TAG", mCursor.getCount() + "");
				while (mCursor.moveToNext())
				{
					 // 获取图片的路�? 
					String path = mCursor.getString(mCursor
							.getColumnIndex(MediaStore.Images.Media.DATA));

					Log.e("TAG", path);
					// 拿到第一张图片的路径  
					if (firstImage == null)
						firstImage = path;
					// 获取该图片的父路径名  
					File parentFile = new File(path).getParentFile();
					  
					if (parentFile == null)
						continue;
					//运行出现空指针的�?if(parentFile.list()==null)continue �?切记~~~有些图片比较诡异~~;
					
					String dirPath = parentFile.getAbsolutePath();
					ImageFloder imageFloder = null;
					// 利用�?��HashSet防止多次扫描同一个文件夹（不加这个判断，图片多起来还是相当恐怖的~~�?  
					if (mDirPaths.contains(dirPath))
					{
						continue;
					} else
					{
						mDirPaths.add(dirPath);
						// 初始化imageFloder
						imageFloder = new ImageFloder();
						imageFloder.setDir(dirPath);
						imageFloder.setFirstImagePath(path);
					}

					//文件名过滤器  FilenameFilter()
					int picSize = parentFile.list(new FilenameFilter()
					{
						@Override
						public boolean accept(File dir, String filename)
						{
							if (filename.endsWith(".jpg")|| filename.endsWith(".png")|| filename.endsWith(".jpeg"))
								return true;
							//返回true的文件则合格
							return false;
						}
					}).length;
					totalCount += picSize;

					imageFloder.setCount(picSize);
					//�? Bean  加到  list 里面
					mImageFloders.add(imageFloder);

					if (picSize > mPicsSize)
					{
						mPicsSize = picSize;
						mImgDir = parentFile;
					}
				}
				mCursor.close();

				// 扫描完成，辅助的HashSet也就可以释放内存�? 
				mDirPaths = null;

				// 通知Handler扫描图片完成 
				mHandler.sendEmptyMessage(0x110);

			}
		}).start();

	}

	/**
	 * 鍒濆鍖朧iew
	 */
	private void initView()
	{
		mGirdView = (GridView) findViewById(R.id.id_gridView);
		mChooseDir = (TextView) findViewById(R.id.id_choose_dir);
		mImageCount = (TextView) findViewById(R.id.id_total_count);
 
		texttitle = (TextView) this.findViewById(R.id.titleText);
		
		mBottomLy = (RelativeLayout) findViewById(R.id.id_bottom_ly);
		
		actionbar = (zss_actionbar) findViewById(R.id.zss_actionbardd);

	}

	private void initEvent()
	{
		/** 
         * 为底部的布局设置点击事件，弹出popupWindow 
         */  
		mBottomLy.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				mListImageDirPopupWindow
						.setAnimationStyle(R.style.anim_popup_dir);
				mListImageDirPopupWindow.showAsDropDown(mBottomLy, 0, 0);

				// 设置背景颜色变暗 
				WindowManager.LayoutParams lp = getWindow().getAttributes();
				lp.alpha = .3f;
				getWindow().setAttributes(lp);
			}
		});
		actionbar.setBarText("xxxxx");
		actionbar.setViewGone(2);
		//actionbar.xxx(this);
		actionbar.setRightImage(R.drawable.bar_yesbutton);
		
		ImageView RightBarImage = actionbar.getRightImage();
		RightBarImage.setOnClickListener(new actionBarClicListener()); 
	}
	public class actionBarClicListener implements View.OnClickListener{
		 

		@Override
		    public void onClick(View view) {
		     Intent intent  = new Intent();
		     Bundle bundle = new Bundle();
		     
		     imgItem =  mAdapter.returnImgItem();
		     imgDirPath = mAdapter.returnImgDirPath();
		     
		     bundle.putStringArrayList("imgItem", imgItem);
		     bundle.putStringArrayList("imgDirPath", imgDirPath);
		     bundle.putString("IntentData","ShowImageActivity");
			 intent.putExtras(bundle);
			  
			 setResult(23, intent);
			 finish();//鍏抽棴褰撳墠 activity
			  
		    }
  
	 }
	@Override
	public void selected(ImageFloder floder)
	{

		mImgDir = new File(floder.getDir());
		mImgs = Arrays.asList(mImgDir.list(new FilenameFilter()
		{
			@Override
			public boolean accept(File dir, String filename)
			{
				if (filename.endsWith(".jpg") || filename.endsWith(".png")
						|| filename.endsWith(".jpeg"))
					return true;
				return false;
			}
		}));
		/** 
	     * 可以看到文件夹的路径和图片的路径分开保存，极大的减少了内存的消�?�?
	     */   
		mAdapter = new MyAdapter(getApplicationContext(), mImgs,
				R.layout.zss_grid_item, mImgDir.getAbsolutePath());
		mGirdView.setAdapter(mAdapter);
		
		// mAdapter.notifyDataSetChanged();
		mImageCount.setText(floder.getCount() + "閿燂拷");
		mChooseDir.setText(floder.getName());
		mListImageDirPopupWindow.dismiss();

	}

	@Override
	public void leftImageOnClickListener() {
		// TODO Auto-generated method stub
		finish();
	}

	@Override
	public void rightImageOnClickListener() {
 
	}

}
