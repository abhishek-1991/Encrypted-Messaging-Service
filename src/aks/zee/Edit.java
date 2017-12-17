package aks.zee;

import android.app.Activity;
import android.content.Intent;
//import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Edit extends Activity {
	int row_id=0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		final DBAdapter2 db=new DBAdapter2(this);
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main2);
		 final EditText e1=(EditText)findViewById(R.id.editText1);
		 final EditText e2=(EditText)findViewById(R.id.editText2);
     	 final EditText e3=(EditText)findViewById(R.id.editText3);
     	Bundle b=getIntent().getExtras();
     	b=getIntent().getExtras();
         final Integer row_id=b.getInt("1");
        String s=row_id.toString();
        Log.d("event", s);
     	final String name=b.getString("2");
     	Log.d("event",name);
     	String phone=b.getString("3");
     	String pu_id=b.getString("4");
     	e1.setText(name);
     	e2.setText(phone);
     	e3.setText(pu_id);
    
    Button b1=(Button)findViewById(R.id.button1);
    b1.setOnClickListener(new View.OnClickListener() {
		
		public void onClick(View v) {
			String new_name=e1.getText().toString();
			 String new_phone=e2.getText().toString();
			 String new_pu_id=e3.getText().toString();
			
			db.open();
			db.updatecontact(new_phone, new_name, new_pu_id,name);
			db.close();
			Intent i=new Intent(Edit.this,View_Contacts1.class);
			startActivity(i);
			finish();
			
		}
	});
    
    Button b2=(Button)findViewById(R.id.button2);
    b2.setOnClickListener(new View.OnClickListener() {
		
		public void onClick(View v) {
			Intent i=new Intent(Edit.this,View_Contacts1.class);
			startActivity(i);
			
			
		}
	});
     
	}
	
}
     	
   
	
	
	

