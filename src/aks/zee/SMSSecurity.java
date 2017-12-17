package aks.zee;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import android.content.Context;
import android.util.Base64;

public class SMSSecurity {
	static class DES
	{
		static void KeyGen(Context context) throws NoSuchAlgorithmException
		{  
				KeyGenerator keygenerator = KeyGenerator.getInstance("DES");
				SecretKey key = keygenerator.generateKey();
				DBAdapter1 db=new DBAdapter1(context);
				db.open();
				db.insertvalid("id", Base64.encodeToString(key.getEncoded(), Base64.DEFAULT));
				db.close();		
		}
		
		static String encrypt(String message,SecretKey k) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException
		{
			Cipher descipher=Cipher.getInstance("DES/ECB/PKCS5Padding");
			descipher.init(Cipher.ENCRYPT_MODE, k);
			byte[] msgenc=descipher.doFinal(message.getBytes());
			return (Base64.encodeToString( msgenc, Base64.DEFAULT ));
		}
		
		static String decrypt(byte[] message,SecretKey k) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException
		{
			
			Cipher descipher=Cipher.getInstance("DES/ECB/PKCS5Padding");
			descipher.init(Cipher.DECRYPT_MODE, k);
			byte[] msgdec=descipher.doFinal(message);
			return (new String(msgdec));
		}
	}
}
