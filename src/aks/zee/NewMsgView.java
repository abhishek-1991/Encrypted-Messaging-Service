package aks.zee;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NewMsgView extends Activity {
	EditText et1,et2,et3;
	Button b1,b2;
	String msg;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setTitle("Compose");
		setContentView(R.layout.newmsgview);
		Bundle b=getIntent().getExtras();
		b1=(Button)findViewById(R.id.send_sms);
		b2=(Button)findViewById(R.id.contacts_sms);
		et1=(EditText)findViewById(R.id.to_sms);
		et2=(EditText)findViewById(R.id.id_sms);
		et3=(EditText)findViewById(R.id.body_sms);
		Log.d("event", et2.getText().toString()+"aks");
		et1.setText(b.getString("ph_no"));
		et2.setText(b.getString("id"));
		et3.setText(b.getString("body"));
		b1.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!et1.getText().toString().equals("") && !et2.getText().toString().equals(""))
				{
					if(et1.getText().toString().length()<10)
					{
						Toast.makeText(NewMsgView.this, "Enter Correct Phone Number", Toast.LENGTH_SHORT).show();
						et1.requestFocus();
					}
					else
					{	Log.d("debug", et2.getText().toString());
						if(!et2.getText().toString().endsWith("=") || (et2.getText().toString().length() != 12))
						{
							Toast.makeText(NewMsgView.this, "Wrong Id !!!", Toast.LENGTH_SHORT).show();
							et2.requestFocus();
						}
						else
						{
							Sendmsg send=new Sendmsg();
							send.sendSMS(NewMsgView.this, et1.getText().toString(), et3.getText().toString(),et2.getText().toString());
						}
					}
				}
				else
				{
					Toast.makeText(NewMsgView.this, "Enter Both Id & Phone Number", Toast.LENGTH_SHORT).show();
					et1.requestFocus();
				}
			}
		});
		b2.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i=new Intent(NewMsgView.this,View_Contacts1.class);
				Bundle b=new Bundle();
				b.putString("body", et3.getText().toString());
				i.putExtras(b);
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
		MenuItem mnu2=menu.add(0, 1, 1, "Send Location");
		mnu2.setAlphabeticShortcut('b');
		mnu2.setIcon(R.drawable.ic_action_locate);
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
					Toast.makeText(NewMsgView.this, "Enter Correct Phone Number", Toast.LENGTH_SHORT).show();
				}
				else
				{	
					
					if(et2.getText().toString().endsWith("=") && et2.getText().toString().length()!=12)
					{
						Toast.makeText(NewMsgView.this, "Wrong Id !!!", Toast.LENGTH_SHORT).show();
					}
					else
					{
						Sendmsg send=new Sendmsg();
						send.sendSMS(NewMsgView.this, et1.getText().toString(), et3.getText().toString(),et2.getText().toString());
					}
				}
			}
			else
			{
				Toast.makeText(NewMsgView.this, "Enter Both Id & Phone Number", Toast.LENGTH_SHORT).show();
				et1.requestFocus();
			}
			return true;
		case 1:
			Intent i1=new Intent(this, LocationSender.class);
			Bundle b1=new Bundle();
			b1.putString("ph_no", et1.getText().toString());
			b1.putString("body", et3.getText().toString());
			b1.putString("id", et2.getText().toString());
			i1.putExtras(b1);
			startActivity(i1);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
		
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}
	
	

}
