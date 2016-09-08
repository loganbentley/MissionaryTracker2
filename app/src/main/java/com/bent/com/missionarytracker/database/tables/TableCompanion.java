package com.bent.com.missionarytracker.database.tables;

import android.database.sqlite.SQLiteDatabase;

public class TableCompanion {
	
	//Database table
	public static final String TABLE_COMPANION = "companion";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_AREA_ID = "area_id";
	public static final String COLUMN_NAME = "name";
	
	//Database creation SQL statement
	private static final String DATABASE_CREATE = "create table "
			+ TABLE_COMPANION
			+ "("
			+ COLUMN_ID + " integer primary key autoincrement, "
			+ COLUMN_NAME + " text, "
			+ COLUMN_AREA_ID + " integer"
			+ ");";
	
	public static void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);
	}
	
	public static void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion){
		
		database.execSQL("DROP TABLE IF EXISTS " + TABLE_COMPANION);
		onCreate(database);
			
		
	}


}
