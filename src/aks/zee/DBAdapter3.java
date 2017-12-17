package aks.zee;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBAdapter3 {
	public static final String KEY_ID="_id";
	public static final String KEY_PHNO="ph_no";
	public static final String KEY_BODY="body";
	private static final String DATABASE_NAME="MyDB";
	private static final String DATABASE_TABLE="sent_sms";
	private static final int DATABASE_VERSION=1;
	private final Context context;
	private DatabaseHelper DBHelper;
	private SQLiteDatabase db;
	
	public DBAdapter3(Context ctx)
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
	public DBAdapter3 open() throws SQLException
	{
		db=DBHelper.getWritableDatabase();
		return this;
	}
	public void close()
	{
		db.close();
	}
	public long insertentry(String phno,String body)
	{
		ContentValues initialvalues=new ContentValues();
		initialvalues.put(KEY_PHNO, phno);
		initialvalues.put(KEY_BODY, body);
		return db.insert(DATABASE_TABLE, null, initialvalues);
	}
	public Cursor getallentries()
	{
		return db.query(false, DATABASE_TABLE, new String[] {KEY_ID,KEY_PHNO,KEY_BODY}, null, null, null, null, null, null);
	}
	public boolean deleteentry(String Id)
	{
	return db.delete(DATABASE_TABLE, KEY_ID + "=" + Id, null) > 0;
	}
}
