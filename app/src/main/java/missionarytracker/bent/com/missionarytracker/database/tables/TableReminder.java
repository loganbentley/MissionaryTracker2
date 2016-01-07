package missionarytracker.bent.com.missionarytracker.database.tables;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class TableReminder {

	//Database table
	public static final String TABLE_REMINDER = "missionary";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_SET = "set";
	public static final String COLUMN_TIME = "time";
	
	//Database creation SQL statement
	private static final String DATABASE_CREATE = "create table "
			+ TABLE_REMINDER
			+ "("
			+ COLUMN_ID + " integer primary key autoincrement, "
			+ COLUMN_SET + " integer, "
			+ COLUMN_TIME + " integer"
			+ ");";
	
	public static void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);
	}
	
	public static void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion){
		Log.w(TableMissionary.class.getName(), "Upgrading database from version "
				+ oldVersion + " to " + newVersion
				+ ", which will destroy all old data");
		database.execSQL("DROP TABLE IF EXISTS " + TABLE_REMINDER);
		onCreate(database);
	}

}
