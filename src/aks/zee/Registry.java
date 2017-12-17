package aks.zee;

import java.security.NoSuchAlgorithmException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

public class Registry extends Activity {
	EditText et1,et2;
	Button btn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main12);
		et1=(EditText)findViewById(R.id.et1reg);
		et2=(EditText)findViewById(R.id.et2reg);
		btn=(Button)findViewById(R.id.btnreg);
		btn.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if((et1.getText()!=null&&et2.getText()!=null)&&et1.getText().toString().equals(et2.getText().toString()))
				{	try
					{
					 SMSSecurity.DES.KeyGen(Registry.this);
					}
					catch(NoSuchAlgorithmException e)
					{
						showDialog(0);
					}
					DBAdapter1 db=new DBAdapter1(Registry.this);
					db.open();
					db.insertvalid("Password", et1.getText().toString());
					db.close();
					Intent i=new Intent();
					i.setData(Uri.parse("1"));
					setResult(RESULT_OK,i);
					finish();
				}
				else
				{
					et1.setText("");
					et2.setText("");
					et2.requestFocus();
				}
				
			}
		});
	}
	@Override
	protected Dialog onCreateDialog(int id) {
		// TODO Auto-generated method stub
		switch (id) {
		case 0:
		return new AlertDialog.Builder(this)
		.setIcon(R.drawable.warning)
		.setTitle("Alert!")
		.setPositiveButton("OK", new
		DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,
					int whichButton){}
		})
		.setMessage("Error Occurred Aborting")
		.create();
	}
	return null;
	}
}
