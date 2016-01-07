package missionarytracker.bent.com.missionarytracker.database.tables;

import android.database.sqlite.SQLiteDatabase;

public class TableArea {
	
	//Database table
			public static final String TABLE_AREA = "area";
			public static final String COLUMN_ID = "_id";
			public static final String COLUMN_NAME = "name";
			public static final String COLUMN_MISSIONARY_ID = "missionary_id";
			public static final String COLUMN_STARTDATE = "start_date";
			public static final String COLUMN_ENDDATE = "end_date";
			
			//Database creation SQL statement
			private static final String DATABASE_CREATE = "create table "
					+ TABLE_AREA
					+ "("
					+ COLUMN_ID + " integer primary key autoincrement, "
					+ COLUMN_NAME + " text, "
					+ COLUMN_MISSIONARY_ID + " integer, "
					+ COLUMN_STARTDATE + " text, "
					+ COLUMN_ENDDATE + " text"
					+ ");";
			
			public static void onCreate(SQLiteDatabase database) {
				database.execSQL(DATABASE_CREATE);
			}
			
			public static void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion){
				
				database.execSQL("DROP TABLE IF EXISTS " + TABLE_AREA);
				onCreate(database);
				
				/*database.execSQL("CREATE TEMPORARY TABLE area_backup "
						+ "("
						+ COLUMN_ID + " integer primary key autoincrement, "
						+ COLUMN_NAME + " text"
						+ ");");
				
				database.execSQL("INSERT INTO area_backup SELECT "
						+ COLUMN_ID + ", "
						+ COLUMN_NAME + " FROM " + TABLE_AREA);
				
				database.execSQL("DROP TABLE IF EXISTS " + TABLE_AREA);
				
				database.execSQL(DATABASE_CREATE);
				
				database.execSQL("INSERT INTO " + TABLE_AREA + " SELECT "
						+ COLUMN_ID + ", "
						+ COLUMN_NAME + " FROM area_backup");
				
				database.execSQL("DROP TABLE IF EXISTS area_backup");
				*/
				
				
				
			}

}
