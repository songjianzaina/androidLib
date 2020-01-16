package com.inswork.indexbar.bank;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class QuickIndexBar extends View {

	private Paint paint;
	private int cellWidth, cellHeight;
	private String TAG = "QuickIndex";
	private String[] indexArr = { "#","A", "B", "C", "D", "E", "F", "G", "H", "I",
			"J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
			"W", "X", "Y", "Z" };
	private int touchIndex = -1;

	public QuickIndexBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		paint = new Paint();
		paint.setColor(Color.parseColor("#516DDA"));
		paint.setTypeface(Typeface.DEFAULT_BOLD);// ����Ϊ����
		paint.setTextSize(20);
		paint.setAntiAlias(true);// 设置抗锯齿
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (cellWidth == 0)
			cellWidth = getMeasuredWidth();
		System.out.println(cellWidth);

		if (cellHeight == 0)
			cellHeight = getMeasuredHeight() / indexArr.length;
		// ��26����ĸ�ȷֻ��Ƶ���Ӧλ��
		for (int i = 0; i < indexArr.length; i++) {// ������ĸ����ߴ�,ѭ��������ĸ
			float x = cellWidth / 2 - paint.measureText(indexArr[i]) / 2;
			Rect bounds = new Rect();
			paint.getTextBounds(indexArr[i], 0, indexArr[i].length(), bounds);// ֻҪִ����,bounds�������������
			float y = cellHeight / 2 + bounds.height() / 2 + i * cellHeight;
			//按压字母的时候显示白色 不按压的时候显示蓝色
			paint.setColor(i == touchIndex ? Color.parseColor("#ffffff")
					: Color.parseColor("#516DDA"));
			canvas.drawText(indexArr[i], x, y, paint);
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int y = (int) event.getY();
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:

			case MotionEvent.ACTION_MOVE:
				if (isSameIndex(y / cellHeight))
					break;
				touchIndex = y / cellHeight;
				if (touchIndex >= 0 && touchIndex < indexArr.length) {
					String word = indexArr[touchIndex];
					if (onTouchIndexListener != null) {
						onTouchIndexListener.onTouchIndex(word);
					}
				}

				break;
			case MotionEvent.ACTION_UP:
				touchIndex = -1;
				break;

			default:
				break;
		}
		invalidate();
		return true;
	}

	public boolean isSameIndex(int currentTouchIndex) {
		return touchIndex == currentTouchIndex;
	}

	public OnTouchIndexListener onTouchIndexListener;

	public OnTouchIndexListener getOnTouchIndexListener() {
		return onTouchIndexListener;
	}

	public void setOnTouchIndexListener(
			OnTouchIndexListener onTouchIndexListener) {
		this.onTouchIndexListener = onTouchIndexListener;
	}

	public int getTouchIndex() {
		return touchIndex;
	}

	public void setTouchIndex(int touchIndex) {
		this.touchIndex = touchIndex;
	}

	public interface OnTouchIndexListener {
		void onTouchIndex(String word);
	}
//	private String[] indexArr = {"#","A", "B", "C", "D", "E", "F", "G", "H",
//			"I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U",
//			"V", "W", "X", "Y", "Z" };
//	private Paint paint;
//	private int width;
//	private float cellHeight;
//	private Context context;
//	private int fontsize;
//	private int blue=0xff0894ec;
//	private int black=0xff999999;
//	private int mid_transparent=0x11000000;
//	private int transparent=0x00000000;
//	public QuickIndexBar(Context context, AttributeSet attrs, int defStyle) {
//		super(context, attrs, defStyle);
//		this.context=context;
//		init();
//	}
//
//
//	public QuickIndexBar(Context context, AttributeSet attrs) {
//		super(context, attrs);
//		this.context=context;
//		init();
//	}
//
//	public QuickIndexBar(Context context) {
//		super(context);
//		this.context=context;
//		init();
//	}
//
//	private void init(){
//		fontsize=sp2px(context, 15);
//		paint = new Paint(Paint.ANTI_ALIAS_FLAG);//设置抗锯齿
//		paint.setColor(black);
//		paint.setTextSize(fontsize);
//		paint.setTextAlign(Paint.Align.CENTER);//设置文本的起点是文字边框底边的中心
//		paint.setStyle(Paint.Style.FILL);
//
//	}
//
//	@Override
//	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
//		super.onSizeChanged(w, h, oldw, oldh);
//		width = getMeasuredWidth();
//		//得到一个格子的高度
//		cellHeight = getMeasuredHeight()*1f/indexArr.length;
//	}
//
//	@Override
//	protected void onDraw(Canvas canvas) {
//		super.onDraw(canvas);
//		for (int i = 0; i < indexArr.length; i++) {
//			float x = width/2;
//			float y = cellHeight/2 + getTextHeight(indexArr[i])/2 + i*cellHeight;
//			if (lastIndex==i) {
//				paint.setColor(blue);
//				canvas.drawCircle(x, y-getTextHeight(indexArr[i])/2, fontsize/2+4, paint);
//				paint.setColor(Color.WHITE);
//			}else {
//				paint.setColor(black);
//			}
//			canvas.drawText(indexArr[i], x, y, paint);
//		}
//	}
//	private int lastIndex = -1;//记录上次的触摸字母的索引
//	@Override
//	public boolean onTouchEvent(MotionEvent event) {
//		switch (event.getAction()) {
//		case MotionEvent.ACTION_DOWN:
//			setBackgroundColor(mid_transparent);
//			float y1 = event.getY();
//			int index1 = (int) (y1/cellHeight);//得到字母对应的索引
//			if(lastIndex!=index1){
//				//说明当前触摸字母和上一个不是同一个字母
////				Log.e("tag", indexArr[index]);
//				//对index做安全性的检查
//				if(index1>=0 && index1<indexArr.length){
//					if(listener!=null){
//						listener.onTouchLetter(indexArr[index1]);
//					}
//				}
//			}
//			lastIndex = index1;
//			break;
//		case MotionEvent.ACTION_MOVE:
//			float y = event.getY();
//			int index = (int) (y/cellHeight);//得到字母对应的索引
//			if(lastIndex!=index){
//				//说明当前触摸字母和上一个不是同一个字母
////				Log.e("tag", indexArr[index]);
//				//对index做安全性的检查
//				if(index>=0 && index<indexArr.length){
//					if(listener!=null){
//						listener.onTouchLetter(indexArr[index]);
//					}
//				}
//			}
//			lastIndex = index;
//			break;
//		case MotionEvent.ACTION_UP:
//			setBackgroundColor(transparent);
//			//重置lastIndex
//			lastIndex = -1;
//			break;
//		}
//		//引起重绘
//		invalidate();
//		return true;
//	}
//
//	/**
//	 * 获取文本的高度
//	 * @param text
//	 * @return
//	 */
//	private int getTextHeight(String text) {
//		//获取文本的高度
//		Rect bounds = new Rect();
//		paint.getTextBounds(text,0,text.length(), bounds);
//		return bounds.height();
//	}
//
//	private OnTouchLetterListener listener;
//	public void setOnTouchLetterListener(OnTouchLetterListener listener){
//		this.listener = listener;
//	}
//	/**
//	 * 触摸字母的监听器
//	 * @author Administrator
//	 *
//	 */
//	public interface OnTouchLetterListener{
//		void onTouchLetter(String letter);
//	}
 
}
