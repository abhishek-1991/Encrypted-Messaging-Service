package aks.zee;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import aks.zee.R;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class Readms extends ListActivity {
	ArrayList<Map<String,String>> msg=new ArrayList<Map<String,String>>();
	ArrayList<String> msgbody=new ArrayList<String>();
	ArrayList<String> msgid=new ArrayList<String>();
	Button bt;
	String id1;
	boolean flag=false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setTitle("Inbox");
		setContentView(R.layout.listmsg);
		bt=(Button)findViewById(R.id.composebtn);
		DBAdapter1 db=new DBAdapter1(this);
		db.open();
		Cursor c1=db.get_id("id");
		if(c1.moveToFirst())
		{
			id1=c1.getString(c1.getColumnIndex("word"));
		}
		c1.close();
		db.close();
		Uri uri=Uri.parse("content://sms/inbox");
		Cursor c=getContentResolver().query(uri, null, null, null, null);
		if(c.moveToFirst())
		{
			do
			{	
				if((c.getString(c.getColumnIndexOrThrow("body"))).toString().endsWith(id1.substring(0, id1.length()-1)))
				{
				Map<String,String> entry=new HashMap<String,String>();
				entry.put("address", c.getString(c.getColumnIndexOrThrow("address")));
				entry.put("date", DateFormat.format("dd/MMMM/yyyy h:mm aa", new Date(Long.parseLong(c.getString(c.getColumnIndexOrThrow("date"))))).toString());
				msg.add(entry);
				msgbody.add(c.getString(c.getColumnIndexOrThrow("body")));
				msgid.add(c.getString(c.getColumnIndexOrThrow("_id")));
				}
				
			}while(c.moveToNext());
			c.close();
			SimpleAdapter ob=new SimpleAdapter(this, msg, R.layout.msgtext, new String[]{"address","date"}, new int[]{R.id.address,R.id.date});
    		setListAdapter(ob);
    		registerForContextMenu(getListView());
    		bt.setOnClickListener(new View.OnClickListener() {
				
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent i=new Intent(Readms.this,NewMsgView.class);
					Bundle b=new Bundle();
					b.putString("ph_no", "");
					b.putString("id", "");
					b.putString("body", "");
					i.putExtras(b);
					startActivity(i);
					
				}
			});
		}
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		Intent i=new Intent(this,Showmsg.class);
		Bundle b=new Bundle();
		b.putString("address",msg.get(position).get("address"));
		b.putString("body", msgbody.get(position));
		b.putString("id", msgid.get(position));
		i.putExtras(b);
		startActivityForResult(i, 3);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.contextmenu, menu);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		AdapterView.AdapterContextMenuInfo menuInfo;
		menuInfo=(AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
		int index=menuInfo.position;
		switch(item.getItemId())
		{
		case R.id.open :
			Intent i=new Intent(this,Showmsg.class);
			Bundle b=new Bundle();
			b.putString("address",msg.get(index).get("address"));
			b.putString("body", msgbody.get(index));
			i.putExtras(b);
			startActivity(i);
			return true;
		case R.id.delete :
			Uri uri=Uri.parse("content://sms/"+msgid.get(index));
			getContentResolver().delete(uri, null, null);
			flag=true;
			startActivity(new Intent(this,Readms.class));
			return true;
		default :
			return super.onContextItemSelected(item);
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuItem mnu1=menu.add(0, 0, 0, "Compose");
				mnu1.setAlphabeticShortcut('a');
				mnu1.setIcon(R.drawable.ic_action_edit);
		MenuItem mnu2=menu.add(0, 1, 1, "Sent SMS");
				mnu2.setAlphabeticShortcut('b');
				mnu2.setIcon(R.drawable.content_import_export);
		MenuItem mnu3=menu.add(0, 2, 2, "Contacts");
				mnu3.setAlphabeticShortcut('c');
				mnu3.setIcon(R.drawable.collections_view_as_list);
		MenuItem mnu4=menu.add(0, 3, 3, "View ID");
				mnu4.setAlphabeticShortcut('d');
				mnu4.setIcon(R.drawable.device_access_secure);
		MenuItem mnu6=menu.add(0, 4, 4, "Refresh");
				mnu6.setAlphabeticShortcut('d');
				mnu6.setIcon(R.drawable.ic_action_refresh);
		MenuItem mnu5=menu.add(0, 5, 5, "Reset ID");
				mnu5.setAlphabeticShortcut('d');
		MenuItem mnu7=menu.add(0, 6, 6, "Reset Password");
				mnu7.setAlphabeticShortcut('d');
			return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId())
		{
		case 0:
			Intent i=new Intent(Readms.this,NewMsgView.class);
			Bundle b=new Bundle();
			b.putString("ph_no", "");
			b.putString("id", "");
			b.putString("body", "");
			i.putExtras(b);
			startActivity(i);
			return true;
		case 1:
			startActivity(new Intent(Readms.this, ReadSentSMS.class));
			return true;
		case 2:
			Intent i1=new Intent(Readms.this, View_Contacts1.class);
			Bundle b1=new Bundle();
			b1.putString("body", "");
			i1.putExtras(b1);
			startActivity(i1);
			return true;
		case 3:
			showDialog(0);
			return true;
		case 5:
			showDialog(1);
			return true;
		case 4:
			startActivity(new Intent(Readms.this,Readms.class));
			finish();
			return true;
		case 6:
			startActivity(new Intent(Readms.this,ResetPass.class));
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if(requestCode==3)
		{
			if(resultCode==RESULT_OK)
			{
				finish();
			}
		}
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		// TODO Auto-generated method stub
		switch(id)
		{
		case 0:
			DBAdapter1 db=new DBAdapter1(Readms.this);
			db.open();
			Cursor c1=db.get_id("id");
			String id1=null;
			if(c1.moveToFirst())
			{
				id1=c1.getString(c1.getColumnIndex("word"));
			}
			c1.close();
			db.close();
			return new AlertDialog.Builder(this)
			.setIcon(R.drawable.device_access_secure)
			.setTitle("Public ID")
			.setPositiveButton("OK", new
			DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,
						int whichButton){}
			})
			.setMessage("Your ID is "+id1)
			.create();
		case 1:
			return new AlertDialog.Builder(this)
			.setIcon(R.drawable.warning)
			.setTitle("Alert!")
			.setPositiveButton("OK", new
			DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,
						int whichButton)
				{
					DBAdapter1 db=new DBAdapter1(Readms.this);
					db.open();
					db.delete_id("id");
					db.close();
					try {
						SMSSecurity.DES.KeyGen(Readms.this);
					} catch (NoSuchAlgorithmException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					startActivity(new Intent(Readms.this,Readms.class));
					finish();
				}
			})
			.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
				}
			})
			.setMessage("If you yill RESET your ID you may not be able to decrypt old SMSes")
			.create();
		}
		return null;
	}
	
	
	
	

}
