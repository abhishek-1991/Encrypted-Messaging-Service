package aks.zee;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.util.Base64;
import android.widget.Toast;

public class Sendmsg {

	public void sendSMS(Context context,final String ph_no,final String body,final String id)
	{	String msg=null;
		Activity act=new Activity();
		DBAdapter3 db=new DBAdapter3(context);
		db.open();
		db.insertentry(ph_no, body);
		db.close();
		byte[] _id=Base64.decode(id, Base64.DEFAULT);
		SecretKey key=new SecretKeySpec(_id, 0, _id.length, "DES");
		try {
			msg=SMSSecurity.DES.encrypt(body, key);
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			Toast.makeText(context, "Error Occurred!!!", Toast.LENGTH_SHORT).show();
			act.startActivity(new Intent(context,Readms.class));
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			Toast.makeText(context, "Error Occurred!!!", Toast.LENGTH_SHORT).show();
			act.startActivity(new Intent(context,Readms.class));
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			Toast.makeText(context, "Error Occurred!!!", Toast.LENGTH_SHORT).show();
			act.startActivity(new Intent(context,Readms.class));
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			Toast.makeText(context, "Error Occurred!!!", Toast.LENGTH_SHORT).show();
			act.startActivity(new Intent(context,Readms.class));
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			Toast.makeText(context, "Error Occurred!!!", Toast.LENGTH_SHORT).show();
			act.startActivity(new Intent(context,Readms.class));
		}
		String SENT="aks.zee.SMS_SENT";
		String DELIVERED="aks.zee.SMS_DELIVERED";
		PendingIntent sentPI=PendingIntent.getBroadcast(context, 0, new Intent(SENT), 0);
		PendingIntent deliveredPI=PendingIntent.getBroadcast(context, 0, new Intent(DELIVERED), 0);
		SmsManager smsmgr=SmsManager.getDefault();
		ArrayList<String> smsmsg=smsmgr.divideMessage(msg+id);
		ArrayList<PendingIntent> sentIntents=new ArrayList<PendingIntent>();
		ArrayList<PendingIntent> deliveredIntents=new ArrayList<PendingIntent>();
		for(int i=0;i<smsmsg.size();i++)
		{
			sentIntents.add(sentPI);
			deliveredIntents.add(deliveredPI);
		}
		smsmgr.sendMultipartTextMessage(ph_no, null, smsmsg, sentIntents, deliveredIntents);
		Toast.makeText(context, "Sending Message...", Toast.LENGTH_SHORT).show();
	}
	
	
}