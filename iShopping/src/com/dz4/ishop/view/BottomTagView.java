package com.dz4.ishop.view;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Looper;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;

import com.dz4.ishopping.R;
/**
 * 
 * �ײ���ǩ
 * �Զ���View
 * �����Զ������ԣ�ͼ�꣬���֣�������ɫ�����ִ�С
 * @author MZone
 *
 */
public class BottomTagView extends View {

	private int mColor;
	private int mTextsize;
	private String mText;
	private Bitmap mIconBitmap;
	
	private Rect mTextRect;
	private Rect mIconRect;
	private Bitmap mBitmap;
	private Paint mIconPaint;
	private Paint mTextPaint;
	private Canvas mCanvas;
	private double Alpha;
	

	public BottomTagView(Context context) {
		this(context, null);
		// TODO �Զ����ɵĹ��캯�����
	}

	public BottomTagView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		// TODO �Զ����ɵĹ��캯�����
	}

	public BottomTagView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		TypedArray ta = this.getContext().obtainStyledAttributes(attrs,
				R.styleable.BottomTagView);
		int num = ta.getIndexCount();
		for (int i = 0; i < num; i++) {
			switch (ta.getIndex(i)) {
			case R.styleable.BottomTagView_tagicon:
				BitmapDrawable drawable = (BitmapDrawable) ta.getDrawable(R.styleable.BottomTagView_tagicon);
				mIconBitmap=drawable.getBitmap();				
				break;
			case R.styleable.BottomTagView_tagtext:
				mText=ta.getString(R.styleable.BottomTagView_tagtext);
				break;

			case R.styleable.BottomTagView_tagsize:
				mTextsize=(int)ta.getDimension(R.styleable.BottomTagView_tagsize, 10);
				break;
			case R.styleable.BottomTagView_tagcolor:
				mColor=ta.getInt(R.styleable.BottomTagView_tagcolor, 0x00ff00);
				break;
			}
		}
		mTextPaint=new Paint();
		mTextRect=new Rect();
		mTextPaint.setColor(0x00000000);
		mTextPaint.setTextSize(mTextsize);
		mTextPaint.getTextBounds(mText, 0, mText.length(), mTextRect);

		// TODO �Զ����ɵĹ��캯�����
	}
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO �Զ����ɵķ������
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int IconWide=Math.min(getMeasuredHeight()-getPaddingTop()-getPaddingBottom()-mTextRect.height(),
			getMeasuredWidth()-getPaddingRight()-getPaddingLeft());
		int top=(getMeasuredHeight()-mTextRect.height()-IconWide)/2;
		int left=(getMeasuredWidth()-IconWide)/2;
		mIconRect=new Rect(left,top,left+IconWide,top+IconWide);	
		
	}
	public void drawResourceBitmap(Canvas canvas){
		canvas.drawBitmap(mIconBitmap, null, mIconRect, null);
	}
	public void drawTargetBitmap(Canvas canvas,int alpha){
		mBitmap =Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Config.ARGB_8888);
		mCanvas =new Canvas(mBitmap);//������������ָ����������mBitmap
		mIconPaint=new Paint();
		mIconPaint.setColor(mColor);
		mIconPaint.setAlpha(alpha);
		mIconPaint.setDither(true);
		mIconPaint.setAntiAlias(true);
		mCanvas.drawRect(mIconRect, mIconPaint);//���������󻭳���ɫ����
		
		
		mIconPaint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));//���û���ģʽ
		mIconPaint.setAlpha(255);
		
		mCanvas.drawBitmap(mIconBitmap, null, mIconRect, mIconPaint);//���ӻ���ͼ��
		canvas.drawBitmap(mBitmap, 0,0,null);
	}
	private void drawTargetText(Canvas canvas, int alpha) {
		// TODO �Զ����ɵķ������
		mTextPaint.setColor(0x999999);
		mTextPaint.setAlpha(255-alpha);
		int x = getMeasuredWidth()/2 - mTextRect.width()/2;
		int y = getMeasuredHeight() - mTextRect.height()/2;
		canvas.drawText(mText, x, y, mTextPaint);
	}

	private void drawResourceText(Canvas canvas, int alpha) {
		// TODO �Զ����ɵķ������
		mTextPaint.setColor(mColor);
		mTextPaint.setAlpha(alpha);
		int x = getMeasuredWidth()/2 - mTextRect.width()/2;
		int y = getMeasuredHeight() - mTextRect.height()/2;
		canvas.drawText(mText, x, y, mTextPaint);
	}
	public void setIconAlpha(double alpha){
		this.Alpha=alpha;
		invalidateView();
	}
	private void invalidateView() {
		if(Looper.getMainLooper()==Looper.myLooper()){
			invalidate();
		}else
		{
			postInvalidate();
		}
		
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO �Զ����ɵķ������
		int alpha=(int) Math.ceil(255*Alpha);
		drawResourceBitmap(canvas);
		drawTargetBitmap(canvas, alpha);
		drawResourceText(canvas,alpha);
		drawTargetText(canvas,alpha);
	}
	private String STATUS_SYS="STATUS_SYS";
	private String STATUS_Alpha="STATUS_Alpha";
@Override
protected Parcelable onSaveInstanceState() {
	// TODO �Զ����ɵķ������
		Bundle bundle =new Bundle();
		bundle.putParcelable(STATUS_SYS, super.onSaveInstanceState());
		bundle.putDouble(STATUS_Alpha, Alpha);
	return bundle;
}
@Override
protected void onRestoreInstanceState(Parcelable state) {
	// TODO �Զ����ɵķ������
	if( state instanceof Bundle){
		Bundle bundle =(Bundle)state;
		Parcelable p=bundle.getParcelable(STATUS_SYS);
		Alpha=bundle.getDouble(STATUS_Alpha);
		super.onRestoreInstanceState(p);
	}
	
}


}
