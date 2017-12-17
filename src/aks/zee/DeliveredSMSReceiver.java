package aks.zee;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class DeliveredSMSReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		switch(getResultCode())
		{
		case Activity.RESULT_OK:
			Toast.makeText(context, "SMS Successfully Delivered", Toast.LENGTH_SHORT).show();
			break;
		case Activity.RESULT_CANCELED:
			Toast.makeText(context, "SMS Not Delivered", Toast.LENGTH_SHORT).show();
			break;
		}
	}

}
