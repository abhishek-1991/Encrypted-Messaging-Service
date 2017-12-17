package aks.zee;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ResetPass extends Activity {
	private Button btn;
	private EditText et1,et2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.resetpass_xml);
		et1=(EditText)findViewById(R.id.resetpassnew);
		et2=(EditText)findViewById(R.id.resetpassconfirm);
		btn=(Button)findViewById(R.id.resetpassbtn);
		btn.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(et1.getText().toString().equals(et2.getText().toString()))
				{
					DBAdapter1 db=new DBAdapter1(ResetPass.this);
					db.open();
					db.update("Password", et1.getText().toString());
					db.close();
					Toast.makeText(ResetPass.this, "Password changed successfully!!!", Toast.LENGTH_SHORT).show();
					finish();
				}
				else
				{
					Toast.makeText(ResetPass.this, "Password should be same in both boxes", Toast.LENGTH_SHORT).show();
					et1.setText("");
					et2.setText("");
					et1.requestFocus();
				}
			}
		});
	}

}
