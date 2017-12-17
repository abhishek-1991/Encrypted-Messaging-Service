package aks.zee;

import java.util.List;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.os.Bundle;
import android.view.GestureDetector.OnDoubleTapListener;
import android.view.GestureDetector.OnGestureListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;

public class Mapactivity extends MapActivity implements OnGestureListener,OnDoubleTapListener {
	MapView map;
	MyLocationOverlay myLocationOverlay;
	GeoPoint p1;
	MapController mc;
	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
    /** Called when the activity is first created. */

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.mapview);
		Bundle b=getIntent().getExtras();
		double lon=b.getDouble("longi");
		double lat=b.getDouble("lati");
		p1=new GeoPoint((int)lat, (int)lon);
		map=(MapView)findViewById(R.id.map);
		map.setBuiltInZoomControls(true);
		mc=map.getController();
		myLocationOverlay=new MyLocationOverlay(this,map);
		myLocationOverlay.enableCompass();
		map.getOverlays().add(myLocationOverlay);
		MapOverlay mapoverlay=new MapOverlay();
		List<Overlay> listofOverlay=map.getOverlays();
		listofOverlay.add(mapoverlay);
		try
		{
		mc.animateTo(p1);
		mc.setZoom(13);
		}
		catch(Exception e){}
		finally{
		map.postInvalidate();}
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		myLocationOverlay.disableMyLocation();
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		myLocationOverlay.enableMyLocation();
	}
    class MapOverlay extends Overlay
    {

		@Override
		public boolean draw(Canvas canvas, MapView mapView, boolean shadow,
				long when) {
			// TODO Auto-generated method stub
			super.draw(canvas, mapView, shadow);
			Point screenpts=new Point();
			mapView.getProjection().toPixels(p1, screenpts);
			Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.marker);
			canvas.drawBitmap(bmp, screenpts.x+10, screenpts.y-34, null);
			return true;
		}
    	
    }
    
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu);
		CreateMenu(menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		return MenuChoice(item);
		
	}
	private void CreateMenu(Menu menu)
    {
    	MenuItem mnu1=menu.add(0, 0, 0, "My Location");
    	{
    		mnu1.setAlphabeticShortcut('a');
    		mnu1.setIcon(R.drawable.ic_action_locate);
    	}
    	MenuItem mnu2=menu.add(0, 1, 1, "Recieved Location");
    	{
    		mnu2.setAlphabeticShortcut('b');
    		mnu2.setIcon(R.drawable.marker);
    	}
    }
    private boolean MenuChoice(MenuItem item)
    {
    	switch(item.getItemId())
    	{
    	case 0: mc.animateTo(myLocationOverlay.getMyLocation());
    			mc.setZoom(13);
    			map.invalidate();
    			return true;
    	case 1:mc.animateTo(p1);
    			mc.setZoom(13);
    			map.invalidate();
    			return true;
    	}
    	return false;
    }
	public boolean onDoubleTap(MotionEvent e) {
		// TODO Auto-generated method stub
		int x = (int)e.getX(), y = (int)e.getY();;
	     Projection p = map.getProjection();
	     mc.animateTo(p.fromPixels(x, y));
	     mc.zoomIn();
	     return true;
		
	}
	public boolean onDoubleTapEvent(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}
	public boolean onSingleTapConfirmed(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		// TODO Auto-generated method stub
		return false;
	}
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
		return false;
	}
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}
    
}
