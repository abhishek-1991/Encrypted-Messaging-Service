package aks.zee;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class ShowSentSMS extends Activity {
	TextView tv;
	String msgid;
	boolean flag=false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Bundle b=getIntent().getExtras();
		setTitle("Sent SMS("+b.getString("address")+")");
		setContentView(R.layout.msgview);
		msgid=b.getString("id");
		tv=(TextView)findViewById(R.id.msgviewtv);
		tv.setText(b.getString("body"));
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuItem mnu1=menu.add(0, 0, 0, "Forward");
		mnu1.setAlphabeticShortcut('a');
		mnu1.setIcon(R.drawable.navigation_forward);
		MenuItem mnu2=menu.add(0, 1, 1, "Delete");
		mnu2.setAlphabeticShortcut('b');
		mnu2.setIcon(R.drawable.content_discard);
		return true;
		
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId())
		{
		case 0:
			Intent i=new Intent(this,NewMsgView.class);
			Bundle b=new Bundle();
			b.putString("ph_no","");
			b.putString("id", "");
			b.putString("body", tv.getText().toString());
			i.putExtras(b);
			startActivity(i);
			return true;
		case 1:
			flag=true;
			setResult(RESULT_OK);
			DBAdapter3 db=new DBAdapter3(ShowSentSMS.this);
			db.open();
			db.deleteentry(msgid);
			db.close();
			startActivity(new Intent(this,Readms.class));
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
		
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		if(flag)
		{
			finish();
		}
	}
}
