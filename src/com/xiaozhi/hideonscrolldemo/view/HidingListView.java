package com.xiaozhi.hideonscrolldemo.view;

import com.xiaozhi.hideonscrolldemo.listener.HidingScrollListener;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

public class HidingListView extends ListView {

	private static final String TAG = "HidingListView";

	private float startX;
	private float startY;
	private HidingScrollListener mListener;

	public void setScrollListener(HidingScrollListener mListener) {
		setOnScrollListener(mListener);
		this.mListener = mListener;
	}

	public HidingListView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public HidingListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public HidingListView(Context context) {
		super(context);
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		if (ev.getAction() == MotionEvent.ACTION_DOWN) {
			startX = ev.getX();
			startY = ev.getY();
		} else if (ev.getAction() == MotionEvent.ACTION_MOVE) {
			if (mListener == null) {
				return super.onTouchEvent(ev);
			}
			mListener.onScrolled(startX - ev.getX(), startY - ev.getY());
		}
		return super.onTouchEvent(ev);
	}

}
