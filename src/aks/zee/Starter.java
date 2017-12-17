package aks.zee;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;

public class Starter extends Activity {
	Intent i;
	boolean close=false,b=false;
	int request_code_1=1,request_code_2=2,a=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main11);
		
		final DBAdapter1 db=new DBAdapter1(this);
		db.open();
		Cursor c=db.get_all();
		boolean t=c.moveToFirst();
		c.close();
		db.close();
		if(t)
		{   
			b=true;
			startActivityForResult(new Intent(this,Authen.class),request_code_1);
		}
		else
		{	
			b=true;
			startActivityForResult(new Intent(this,Registry.class),request_code_2);
		}
		
	}
     public void onActivityResult(int requestcode,int resultcode,Intent i)
		{	
			if(requestcode==request_code_1||requestcode==request_code_2)
			{	
				b=false;
				if(resultcode==RESULT_OK)
				{   if(requestcode==request_code_1)
				    {
					String in=i.getData().toString();
					if(!in.isEmpty())
					{	
						final DBAdapter1 db=new DBAdapter1(this);
						db.open();
						Cursor c1=db.get_id("Password");
						
						if(c1.moveToFirst())
						{
							if(in.equals(c1.getString(1)))
							{	db.close();
								close=true;
								startActivity(new Intent(this,Readms.class));
							}
							else
							{   db.close();
								finish();
								Toast.makeText(this, "Wrong Password!!!!", Toast.LENGTH_SHORT).show();
							}
						}
						else{finish();}
					}
					else
					{
						finish();
					}
				    }
				    if(requestcode==request_code_2)
				    {
				    	int flag=Integer.parseInt(i.getData().toString());
				    	if(flag==1)
				    	{	close=true;
				    		startActivity(new Intent(this,Readms.class));
				    	}
				    	else
				    	{
				    		finish();
				    	}
				    }
				}
			}
		}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		if(close)
		{
			finish();
		}
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if(!b)
		{
			finish();
		}
	}
	
     
}
