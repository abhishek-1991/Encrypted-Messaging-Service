package aks.zee;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Authen extends Activity {
	EditText et;
	Button bt;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main13);
		et=(EditText)findViewById(R.id.etauth);
		bt=(Button)findViewById(R.id.btnauth);
		bt.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!et.getText().toString().isEmpty())
				{
				Intent i=new Intent();
				i.setData(Uri.parse(et.getText().toString()));
				setResult(RESULT_OK,i);
				finish();
				}
				else
				{
					et.setText("");
					Toast.makeText(Authen.this, "Please Enter Password", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
	

}
