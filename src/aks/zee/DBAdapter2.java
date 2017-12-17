package aks.zee;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBAdapter2 {
	public static final String KEY_ROW="row_id";
	public static final String KEY_NAME="name";
	public static final String KEY_PHNO="ph_no";
	public static final String KEY_ID="_id";
	private static final String DATABASE_NAME="MyDB";
	private static final String DATABASE_TABLE="phone_book1";
	private static final int DATABASE_VERSION=1;
	
	private final Context context;
	private DatabaseHelper DBHelper;
	private SQLiteDatabase db;
	
	public DBAdapter2(Context ctx)
	{
		this.context=ctx;
		DBHelper=new DatabaseHelper(context);
	}
	private static class DatabaseHelper extends SQLiteOpenHelper
	{
		DatabaseHelper(Context context)
		{
			super(context,DATABASE_NAME,null,DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			
			// TODO Auto-generated method stub
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			
		}
	}
	public DBAdapter2 open() throws SQLException
	{
		db=DBHelper.getWritableDatabase();
		return this;
	}
	public void close()
	{
		db.close();
	}
	public long insertcontact(String phno,String name,String id)
	{
		DBAdapter2 ob=new DBAdapter2(context);
		 boolean j,k,l,m=false;
		 ob.open();
		 
	        Cursor c=ob.getallcontacts();
	        
	        if(c.moveToFirst())
	        {
	        	do
	        	{
	        		String cname=c.getString(0);
	        		String cph_no=c.getString(c.getColumnIndex("ph_no"));
	        		String cpu_Id=c.getString(c.getColumnIndex("_id"));
	        		 j=cname.equals(name); 
	        		 
	        		 k=cph_no.equals(phno); 
	        		 l=cpu_Id.equals(id);
	        		 if (j==true && k==true && l== true)
	        		 {
	        			 m=true;
	        			 break;
	        		
	        		 }
	       	
	        	}while(c.moveToNext());
	        	 
	        }
	         
       	 	c.close();
	        
	         if (m==true)
	         {	        	
	        	return -2; 
	         }
	         else
		{
	    ContentValues initialvalues=new ContentValues();
		initialvalues.put(KEY_PHNO, phno);
		initialvalues.put(KEY_NAME, name);
		initialvalues.put(KEY_ID, id);
		
		return db.insert(DATABASE_TABLE, null, initialvalues);
		}
	}
	public boolean deletecontact(String phone)
	{
	return db.delete(DATABASE_TABLE,KEY_PHNO + "='" + phone+"'",null)>0;	
	}
	
	public boolean updatecontact(String phno,String name,String id,String old_name)

	{
		
	             
		ContentValues args=new ContentValues();
		args.put(KEY_NAME, name);
		args.put(KEY_PHNO, phno);
		args.put(KEY_ID, id);
		return db.update(DATABASE_TABLE, args, KEY_NAME + "='" + old_name +"'", null)>0;
	         
	}
	public Cursor getContact(String phno) throws SQLException
	{
		Cursor mCursor = db.query(true, DATABASE_TABLE, new String[] {KEY_PHNO,KEY_ID}, KEY_PHNO + "='" + phno+"'", null,null, null, null, null);
				if (mCursor != null) {
				mCursor.moveToFirst();
				}
				return mCursor;
	}
	public Cursor getallcontacts()
	{
		return db.query(DATABASE_TABLE, new String[] {KEY_NAME, KEY_PHNO, KEY_ID}, null, null, null, null, null);
	}

}
