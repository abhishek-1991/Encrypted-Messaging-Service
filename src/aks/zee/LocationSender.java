package aks.zee;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LocationSender extends Activity {
	EditText et1,et2,et3;
	Button b1,b2;
	private LocationManager locmgr;
	Location location_net,location_gps,location;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setTitle("Send Location");
		setContentView(R.layout.newmsgview);
		Bundle b=getIntent().getExtras();
		locmgr=(LocationManager)getSystemService(LOCATION_SERVICE);
		location_net=locmgr.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		location_gps=locmgr.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		if(location_gps==null)
		{
			if(location_net==null)
			{
				Toast.makeText(this, "Location not Available", Toast.LENGTH_SHORT).show();
				Intent i1=new Intent(LocationSender.this,NewMsgView.class);
				i1.putExtras(b);
				startActivity(i1);
				finish();
			}
			else
			{
				location=location_net;
			}
		}
		else
		{
			location=location_gps;
		}
		b1=(Button)findViewById(R.id.send_sms);
		b2=(Button)findViewById(R.id.contacts_sms);
		et1=(EditText)findViewById(R.id.to_sms);
		et2=(EditText)findViewById(R.id.id_sms);
		et3=(EditText)findViewById(R.id.body_sms);
		et1.setText(b.getString("ph_no"));
		et2.setText(b.getString("id"));
		et3.setText("Location");
		et3.setFocusable(false);
		b1.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!et1.getText().toString().equals("") && !et2.getText().toString().equals(""))
				{
					if(et1.getText().toString().length()<10)
					{
						Toast.makeText(LocationSender.this, "Enter Correct Phone Number", Toast.LENGTH_SHORT).show();
					}
					else
					{	
						if(!et2.getText().toString().endsWith("=") || (et2.getText().toString().length()!=12))
						{
							Toast.makeText(LocationSender.this, "Wrong Id !!!", Toast.LENGTH_SHORT).show();
						}
						else
						{
							Sendmsg send=new Sendmsg();
							send.sendSMS(LocationSender.this, et1.getText().toString(), et3.getText().toString()+";"+location.getLatitude()+";"+location.getLongitude(),et2.getText().toString());
						}
					}
				}
				else
				{
					Toast.makeText(LocationSender.this, "Enter Both Id & Phone Number", Toast.LENGTH_SHORT).show();
					et1.requestFocus();
				}
			}
		});
		b2.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i=new Intent(LocationSender.this,View_Contacts1.class);
				Bundle b1=new Bundle();
				b1.putString("body", et3.getText().toString()+";"+location.getLatitude()+";"+location.getLongitude());
				i.putExtras(b1);
				startActivity(i);
				finish();
				
			}
		});
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuItem mnu1=menu.add(0, 0, 0, "Send");
		mnu1.setAlphabeticShortcut('a');
		mnu1.setIcon(R.drawable.social_send_now);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId())
		{
		case 0:
			if(!et1.getText().toString().equals("") && !et2.getText().toString().equals(""))
			{
				if(et1.getText().toString().length()<10)
				{
					Toast.makeText(LocationSender.this, "Enter Correct Phone Number", Toast.LENGTH_SHORT).show();
				}
				else
				{	
					if(!et2.getText().toString().endsWith("=") || (et2.getText().toString().length()!=12))
					{
						Toast.makeText(LocationSender.this, "Wrong Id !!!", Toast.LENGTH_SHORT).show();
					}
					else
					{
						Sendmsg send=new Sendmsg();
						send.sendSMS(LocationSender.this, et1.getText().toString(), et3.getText().toString()+";"+location.getLatitude()+";"+location.getLongitude(),et2.getText().toString());
					}
				}
			}
			else
			{
				Toast.makeText(LocationSender.this, "Enter Both Id & Phone Number", Toast.LENGTH_SHORT).show();
				et1.requestFocus();
			}
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
		
	}
}
