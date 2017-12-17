package aks.zee;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class ReadSentSMS extends ListActivity {
	ArrayList<Map<String,String>> msg=new ArrayList<Map<String,String>>();
	ArrayList<String> msgbody=new ArrayList<String>();
	ArrayList<String> msgid=new ArrayList<String>();
	Button bt;
	boolean flag=false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setTitle("Sent Messages");
		setContentView(R.layout.listmsg);
		bt=(Button)findViewById(R.id.composebtn);
		DBAdapter3 db=new DBAdapter3(this);
		db.open();
		Cursor c=db.getallentries();
		if(c.moveToFirst())
		{
			do
			{	Map<String,String> entry=new HashMap<String, String>();
				entry.put("address", c.getString(c.getColumnIndex("ph_no")));
				if(c.getString(c.getColumnIndex("body")).length()>21)
				{
					entry.put("body", c.getString(c.getColumnIndex("body")).substring(0, 21)+"...");
				}
				else
				{
					entry.put("body", c.getString(c.getColumnIndex("body")));
				}
				msg.add(entry);
				msgbody.add(c.getString(c.getColumnIndex("body")));
				msgid.add(c.getString(c.getColumnIndex("_id")));
				
			}while(c.moveToNext());
			SimpleAdapter ob=new SimpleAdapter(this, msg, R.layout.msgtext, new String[]{"address","body"}, new int[]{R.id.address,R.id.date});
    		setListAdapter(ob);
    		registerForContextMenu(getListView());			
		}
		db.close();
		c.close();
		bt.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				flag=true;
				Intent i=new Intent(ReadSentSMS.this,NewMsgView.class);
				Bundle b=new Bundle();
				b.putString("ph_no", "");
				b.putString("id", "");
				b.putString("body", "");
				i.putExtras(b);
				startActivity(i);
				
			}});
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		Intent i=new Intent(this,ShowSentSMS.class);
		Bundle b=new Bundle();
		b.putString("address",msg.get(position).get("address"));
		b.putString("body", msgbody.get(position));
		b.putString("id", msgid.get(position));
		i.putExtras(b);
		startActivityForResult(i, 4);
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
			DBAdapter3 db=new DBAdapter3(ReadSentSMS.this);
			db.open();
			db.deleteentry(msgid.get(index));
			db.close();
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
		MenuItem mnu2=menu.add(0, 1, 1, "Contacts");
				mnu2.setAlphabeticShortcut('c');
				mnu2.setIcon(R.drawable.collections_view_as_list);
			return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId())
		{
		case 0:
			flag=true;
			Intent i=new Intent(ReadSentSMS.this,NewMsgView.class);
			Bundle b=new Bundle();
			b.putString("ph_no", "");
			b.putString("id", "");
			b.putString("body", "");
			i.putExtras(b);
			startActivity(i);
			return true;
		case 1:
			Intent i1=new Intent(ReadSentSMS.this,View_Contacts1.class);
			Bundle b1=new Bundle();
			b1.putString("body", "");
			i1.putExtras(b1);
			startActivity(i1);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if(requestCode==4)
		{
			if(resultCode==RESULT_OK)
			{
				finish();
			}
		}
	}
	
}
