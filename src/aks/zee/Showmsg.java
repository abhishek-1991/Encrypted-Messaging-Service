package aks.zee;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class Showmsg extends Activity {
	TextView tv;
	String msgid,addr;
	String id;
	boolean flag=false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Bundle b=getIntent().getExtras();
		addr=b.getString("address");
		setTitle(addr);
		setContentView(R.layout.msgview);
		msgid=b.getString("id");
		tv=(TextView)findViewById(R.id.msgviewtv);
		tv.setText(b.getString("body"));
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuItem mnu2=menu.add(0, 0, 0, "Delete");
			mnu2.setAlphabeticShortcut('b');
			mnu2.setIcon(R.drawable.content_discard);
			DBAdapter1 db=new DBAdapter1(this);
			db.open();
			Cursor c=db.get_id("id");
			if(c.moveToFirst())
			{
				id=c.getString(c.getColumnIndex("word"));
				Log.d("event",id);
			}
			db.close();
			if((tv.getText().toString().substring(0, tv.getText().toString().length()-id.length()).length())%4==0 && (tv.getText().toString().substring(0, tv.getText().toString().length()-id.length()).endsWith("=")))
		{MenuItem mnu3=menu.add(0, 1, 1, "Decrypt");
			mnu3.setAlphabeticShortcut('c');
			mnu3.setIcon(R.drawable.ic_action_cut);}
		return true;
		
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId())
		{
		case 0:
			flag=true;
			setResult(RESULT_OK);
			Uri uri=Uri.parse("content://sms/"+msgid);
			getContentResolver().delete(uri, null, null);
			startActivity(new Intent(this,Readms.class));
			return true;
		case 1:
			flag=true;
			Intent i1=new Intent(this,DecryptMsgView.class);
			Bundle b1=new Bundle();
			b1.putString("address",addr);
			b1.putString("id", msgid);
			b1.putString("body", tv.getText().toString());
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
		if(flag)
		{
			finish();
		}
	}

}
