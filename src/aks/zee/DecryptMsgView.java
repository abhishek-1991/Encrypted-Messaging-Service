package aks.zee;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class DecryptMsgView extends Activity {
	String msgid,id;
	String body;
	TextView tv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Bundle b=getIntent().getExtras();
		setTitle(b.getString("address"));
		setContentView(R.layout.msgview);
		msgid=b.getString("id");
		DBAdapter1 db=new DBAdapter1(this);
		db.open();
		Cursor c=db.get_id("id");		
		if(c.moveToFirst())
		{
			id=c.getString(c.getColumnIndex("word"));
		}
		//Log.d("event", id);
		c.close();
		db.close();
		byte[] uid=Base64.decode(id, Base64.DEFAULT);
		SecretKey key=new SecretKeySpec(uid, 0, uid.length, "DES");
		try {
			body=SMSSecurity.DES.decrypt(Base64.decode(b.getString("body").substring(0, b.getString("body").length()-id.length()), Base64.DEFAULT), key);
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			Toast.makeText(this, "Error Occurred!!!", Toast.LENGTH_SHORT).show();
			startActivity(new Intent(DecryptMsgView.this,Readms.class));
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			Toast.makeText(this, "Error Occurred!!!", Toast.LENGTH_SHORT).show();
			startActivity(new Intent(DecryptMsgView.this,Readms.class));
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			Toast.makeText(this, "Error Occurred!!!", Toast.LENGTH_SHORT).show();
			startActivity(new Intent(DecryptMsgView.this,Readms.class));
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			Toast.makeText(this, "Error Occurred!!!", Toast.LENGTH_SHORT).show();
			startActivity(new Intent(DecryptMsgView.this,Readms.class));
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			Toast.makeText(this, "Error Occurred!!!", Toast.LENGTH_SHORT).show();
			startActivity(new Intent(DecryptMsgView.this,Readms.class));
		}
		tv=(TextView)findViewById(R.id.msgviewtv);
		tv.setText(body);
		if(body.startsWith("Location;"))
		{
			showDialog(0);
		}
	}
	@Override
	protected Dialog onCreateDialog(int id) {
		// TODO Auto-generated method stub
		switch (id) {
		case 0:
		return new AlertDialog.Builder(this)
		.setIcon(R.drawable.warning)
		.setTitle("Location")
		.setPositiveButton("OK", new
		DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,
					int whichButton){
				String[] loc=body.split(";");
				Intent i=new Intent(DecryptMsgView.this,Mapactivity.class);
				Bundle b1=new Bundle();
				b1.putDouble("lati", Double.parseDouble(loc[1])*1E6);
				b1.putDouble("longi", Double.parseDouble(loc[2])*1E6);
				i.putExtras(b1);
				startActivity(i);
			}
		})
		.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
			}
		})
		.setMessage("This is a Location. Do you want to Project it on Map?")
		.create();
	}
	return null;
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
			setResult(RESULT_OK);
			Uri uri=Uri.parse("content://sms/"+msgid);
			getContentResolver().delete(uri, null, null);
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
		finish();
	}
	

}
