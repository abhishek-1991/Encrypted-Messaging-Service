package aks.zee;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBAdapter1 {
	public static final String KEY_FIELD="field";
	public static final String KEY_WORD="word";
	private static final String DATABASE_NAME="MyDB";
	private static final String DATABASE_TABLE="Valid";
	private static final int DATABASE_VERSION=1;
	private static final String DATABASE_CREATE="CREATE TABLE "+DATABASE_TABLE+" ("+KEY_FIELD+" TEXT NOT NULL, "+KEY_WORD+" TEXT NOT NULL);";
	private final Context context;
	private DatabaseHelper1 DBHelper;
	private SQLiteDatabase db;
	
	public DBAdapter1(Context ctx)
	{
		this.context=ctx;
		DBHelper=new DatabaseHelper1(context);
	}
	private static class DatabaseHelper1 extends SQLiteOpenHelper
	{
		DatabaseHelper1(Context context)
		{
			super(context,DATABASE_NAME,null,DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			try {
				 db.execSQL(DATABASE_CREATE);
				 db.execSQL("create table phone_book1 (row_id integer primary key autoincrement,ph_no text not null,name text not null,_id text not null);");
				 db.execSQL("create table sent_sms (_id integer primary key autoincrement,ph_no text not null,body text not null);");
				} 
			catch (SQLException e) {
				e.printStackTrace();
				}
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			
		}
	}
	public DBAdapter1 open() throws SQLException
	{
		db=DBHelper.getWritableDatabase();
		return this;
	}
	public void close()
	{
		db.close();
	}
	public long insertvalid(String field,String word)
	{
		ContentValues initialvalues=new ContentValues();
		initialvalues.put(KEY_FIELD, field);
		initialvalues.put(KEY_WORD, word);
		return db.insert(DATABASE_TABLE, null, initialvalues);
	}
	public boolean delete_id(String field)
	{
	return db.delete(DATABASE_TABLE, KEY_FIELD + "= '" +field+"'", null) > 0;
	}
	public Cursor get_id(String field) throws SQLException
	{
		return db.query(DATABASE_TABLE, new String[] {KEY_FIELD,KEY_WORD}, KEY_FIELD + "='"+field+"'", null,null, null, null, null);
		
	}
	public Cursor get_all() throws SQLException
	{
		return db.query(DATABASE_TABLE, new String[]{KEY_FIELD,KEY_WORD}, null, null, null, null, null);
	}
	public boolean update(String fld,String wrd)
	{
		ContentValues args=new ContentValues();
		args.put(KEY_WORD, wrd);
		return db.update(DATABASE_TABLE, args, KEY_FIELD+"='"+fld+"'", null)>0;
	}
}
