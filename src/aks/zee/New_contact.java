package aks.zee;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class New_contact extends Activity {
	DBAdapter2 ob=new DBAdapter2(this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setTitle("Add Contact");
		setContentView(R.layout.main2);
		 Button b1=(Button)findViewById(R.id.button1);
		 b1.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				EditText e1=(EditText)findViewById(R.id.editText1);
				EditText e2=(EditText)findViewById(R.id.editText2);
		     	EditText e3=(EditText)findViewById(R.id.editText3);
				String name= e1.getText().toString();
				String phno=e2.getText().toString();
				String id=e3.getText().toString();
				if(name.equals("") && phno.equals("") && id.equals(""))
				{
					Toast.makeText(New_contact.this, "Fill each Entry", Toast.LENGTH_SHORT).show();
					e1.requestFocus();
				}
				else
				{
				ob.open();
				long l=ob.insertcontact(phno, name, id);
				ob.close();
				if (l==-2)
				{
					AlertDialog alertDialog = new AlertDialog.Builder(New_contact.this).create();
					alertDialog.setTitle("Reset...");
					alertDialog.setMessage("Contact Already exist ?");
					 alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
						
						public void onClick(DialogInterface dialog, int which) {
							
							
						}
					});
					 alertDialog.setIcon(R.drawable.ic_launcher);
					 alertDialog.show();
					 
					
				}
				else
				
				
				{	Toast.makeText(getBaseContext(), "success", Toast.LENGTH_SHORT).show();
				
				Intent i=new Intent(New_contact.this,View_Contacts1.class);
				startActivity(i);
				finish();
				// TODO Auto-generated method stub
				}
				}
			}
		});
		 Button b2=(Button)findViewById(R.id.button2);
		 b2.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				Intent i=new Intent(New_contact.this,View_Contacts1.class);
				startActivity(i);
				// TODO Auto-generated method stub
				
			}
		});

	}
	

}
