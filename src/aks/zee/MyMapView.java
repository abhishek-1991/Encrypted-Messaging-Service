package aks.zee;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.OnDoubleTapListener;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;

import com.google.android.maps.MapView;

public class MyMapView extends MapView {
	private Context context;
	private GestureDetector gesdet;

	public MyMapView(Context arg0, AttributeSet arg1) {
		super(arg0, arg1);
		context=arg0;
		// TODO Auto-generated constructor stub
		gesdet=new GestureDetector((OnGestureListener)context);
		gesdet.setOnDoubleTapListener((OnDoubleTapListener)context);
	}
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		if (this.gesdet.onTouchEvent(ev))
			   return true;
			  else
			   return super.onTouchEvent(ev);

	}

}
