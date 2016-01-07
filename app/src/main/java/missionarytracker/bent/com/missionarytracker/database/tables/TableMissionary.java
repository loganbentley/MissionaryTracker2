package missionarytracker.bent.com.missionarytracker.database.tables;

import android.database.sqlite.SQLiteDatabase;

public class TableMissionary {
	
	//Database table
		public static final String TABLE_MISSIONARY = "missionary";
		public static final String COLUMN_ID = "_id";
		public static final String COLUMN_NAME = "name";
		public static final String COLUMN_MISSION = "mission";
		public static final String COLUMN_DEPARTURE = "departure";
		public static final String COLUMN_ARRIVAL = "arrival";
		public static final String COLUMN_EMAIL = "email";
		public static final String COLUMN_GENDER = "gender";
		public static final String COLUMN_IMAGE = "image";
		
		//Database creation SQL statement
		private static final String DATABASE_CREATE = "create table "
				+ TABLE_MISSIONARY
				+ "("
				+ COLUMN_ID + " integer primary key autoincrement, "
				+ COLUMN_NAME + " text, "
				+ COLUMN_MISSION + " text, "
				+ COLUMN_DEPARTURE + " text, "
				+ COLUMN_ARRIVAL + " text, "
				+ COLUMN_EMAIL + " text, "
				+ COLUMN_GENDER + " text, "
				+ COLUMN_IMAGE + " text"
				+ ");";
		
		public static void onCreate(SQLiteDatabase database) {
			database.execSQL(DATABASE_CREATE);
		}
		
		public static void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion){
			/*Log.w(MissionaryTable.class.getName(), "Upgrading database from version "
					+ oldVersion + " to " + newVersion
					+ ", which will destroy all old data");
			database.execSQL("DROP TABLE IF EXISTS " + TABLE_MISSIONARY);
			onCreate(database);*/
			
			database.execSQL("CREATE TEMPORARY TABLE missionary_backup "
					+ "("
					+ COLUMN_ID + " integer primary key autoincrement, "
					+ COLUMN_NAME + " text, "
					+ COLUMN_MISSION + " text, "
					+ COLUMN_DEPARTURE + " text, "
					+ COLUMN_ARRIVAL + " text, "
					+ COLUMN_EMAIL + " text, "
					+ COLUMN_GENDER + " text, "
					+ COLUMN_IMAGE + " text"
					+ ");");
			
			database.execSQL("INSERT INTO missionary_backup SELECT "
					+ COLUMN_ID + ", "
					+ COLUMN_NAME + ", "
					+ COLUMN_MISSION + ", "
					+ COLUMN_DEPARTURE + ", "
					+ COLUMN_ARRIVAL + ", "
					+ COLUMN_EMAIL + ", "
					+ COLUMN_GENDER + ", "
					+ COLUMN_IMAGE
					+ " FROM " + TABLE_MISSIONARY);
			
			database.execSQL("DROP TABLE IF EXISTS " + TABLE_MISSIONARY);
			
			database.execSQL(DATABASE_CREATE);
			
			database.execSQL("INSERT INTO " + TABLE_MISSIONARY + " SELECT "
					+ COLUMN_ID + ", "
					+ COLUMN_NAME + ", "
					+ COLUMN_MISSION + ", "
					+ COLUMN_DEPARTURE + ", "
					+ COLUMN_ARRIVAL + ", "
					+ COLUMN_EMAIL + ", "
					+ COLUMN_GENDER + ", "
					+ COLUMN_IMAGE 
					+ " FROM missionary_backup");
			
			database.execSQL("DROP TABLE IF EXISTS missionary_backup");
			
			
			
			
		}

}
