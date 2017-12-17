
package aks.zee;
import java.util.ArrayList;
import java.util.HashMap;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class View_Contacts1 extends ListActivity {
	DBAdapter2 db=new DBAdapter2(this);
	 ArrayList<HashMap<String,String>> list = 
	    	new ArrayList<HashMap<String,String>>(); 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setTitle("Phone Book"); 
		setContentView(R.layout.main3);
		 Button b1=(Button) findViewById(R.id.b);
	       b1.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				Intent i=new Intent(View_Contacts1.this,New_contact.class);
				startActivity(i);
				finish();
					
				
			}
		});
	     
	        //get all contacts//
	        db.open();
	        Cursor c=db.getallcontacts();
	        
	       
	        if(c.moveToFirst())
	        {
	        	do
	        	{
	        		HashMap<String,String> temp = new HashMap<String,String>();	
	        		String name=c.getString(c.getColumnIndex("name"));
	        		String ph_no=c.getString(c.getColumnIndex("ph_no"));
	        		String pu_Id=c.getString(c.getColumnIndex("_id"));
	        		temp.put("name", name);
	        		temp.put("phone", ph_no);
	        		temp.put("puId", pu_Id);
	        		list.add(temp);	
	        		
	        		
	        	}while(c.moveToNext());
	        	Log.d("event", "aks2");
	        }
	        c.close();
	        db.close();

	        
	        SimpleAdapter adapter = new SimpleAdapter(
	        		this,
	        		list,
	        		R.layout.phonebook_row,
	        		new String[] {"name","phone","puId"},
	        		new int[] {R.id.text1,R.id.text2, R.id.text3}
	        		);
	        setListAdapter(adapter);
	        registerForContextMenu(getListView());
		
		
	}
	protected void onListItemClick(ListView l, android.view.View v, int position, long id) {

	    super.onListItemClick(l, v, position, id);
	   String phone1=list.get(position).get("phone");
    	String pu_id1=list.get(position).get("puId");
	    
	    Bundle b=getIntent().getExtras();
		Bundle b1=new Bundle();
		if(b.getString("body").startsWith("Location;"))
		{
			b1.putString( "ph_no",phone1);
			b1.putString( "id",pu_id1);
			Intent I=new Intent(this,LocationSender.class);
			I.putExtras(b1);
			startActivityForResult(I,1);
		}
		else
		{
			b1.putString("body",b.getString("body"));
			b1.putString( "ph_no",phone1);
			b1.putString( "id",pu_id1);
			Intent I=new Intent(this,NewMsgView.class);
			I.putExtras(b1);
			startActivityForResult(I,1);
		}
	   
	   
	}
	
@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}
	
@Override
public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflator= getMenuInflater();
		inflator.inflate(R.layout.context_menu, menu);
	}
   
    
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		
		 
		 switch (item.getItemId()) {
	        case R.id.Ed:
	        	int i=info.position;
	        	
	        	String name1=list.get(i).get("name");
	        	Log.d("name", name1);
	        	String phone1=list.get(i).get("phone");
	        	String pu_id1=list.get(i).get("puId");
	        	Bundle b=new Bundle();
	        	b.putInt("1", i);
	        	b.putString("2", name1);
	        	b.putString("3", phone1);
	        	b.putString("4", pu_id1);
	        	Intent k=new Intent(View_Contacts1.this,Edit.class);
	        	
	        	k.putExtras(b);
	        	startActivityForResult(k,1);
	            //editNote(info.id);
	            return true;
	        case R.id.Del:
	        	 Integer j =info.position;
	        	
	        	db.open();
	            /* Cursor c=db.getallcontacts();
	             
	        	 c.moveToPosition(j);
	        	 String name=c.getString(1);
	        	 */
	        	String p=list.get(j).get("phone");
	             db.deletecontact(list.get(j).get("phone"));
	             Log.d("phone",p);
	             db.close();
	             Intent l=new Intent(this,View_Contacts1.class);
	 			startActivity(l);
				
	          
	           return true;
	            
	       default:     
		       return super.onContextItemSelected(item);
	}    
    
    
}
}

