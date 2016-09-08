package com.bent.MissionaryTracker.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.bent.MissionaryTracker.database.models.Missionary;


import java.util.ArrayList;
import java.util.List;

import com.bent.MissionaryTracker.database.models.Area;
import com.bent.MissionaryTracker.database.models.Companion;
import com.bent.MissionaryTracker.database.tables.TableArea;
import com.bent.MissionaryTracker.database.tables.TableCompanion;
import com.bent.MissionaryTracker.database.tables.TableMissionary;

public class DBHelper extends SQLiteOpenHelper {
	
	private static final String DATABASE_NAME = "todotable.db";
	private static final int DATABASE_VERSION = 15;
	
	public DBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	//Method is called during creation of the database
	@Override
	public void onCreate(SQLiteDatabase database) {
		TableMissionary.onCreate(database);
		TableArea.onCreate(database);
		TableCompanion.onCreate(database);
	}
	//Method is called during an upgrade of the database, if you increase the database version
	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
		TableMissionary.onUpgrade(database, oldVersion, newVersion);
		TableArea.onUpgrade(database, oldVersion, newVersion);
		TableCompanion.onUpgrade(database, oldVersion, newVersion);
	}
	public void addMissionary(Missionary missionary){
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put(TableMissionary.COLUMN_NAME, missionary.getName());
		values.put(TableMissionary.COLUMN_MISSION, missionary.getMission());
		values.put(TableMissionary.COLUMN_EMAIL, missionary.getEmail());
		values.put(TableMissionary.COLUMN_DEPARTURE, missionary.getDeparture());
		values.put(TableMissionary.COLUMN_ARRIVAL, missionary.getArrival());
		values.put(TableMissionary.COLUMN_GENDER, missionary.getGender());
		values.put(TableMissionary.COLUMN_IMAGE, missionary.getImage());
		
		//insert row
		db.insert(TableMissionary.TABLE_MISSIONARY, null, values);
		db.close();
	}
	public void deleteMissionary(Missionary missionary)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TableMissionary.TABLE_MISSIONARY, TableMissionary.COLUMN_ID + " = ?",
				new String[] { String.valueOf(missionary.getID()) });
		
		db.close();
	}
	public int updateMissionary(Missionary missionary){
		
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put(TableMissionary.COLUMN_ID, missionary.getID());
		values.put(TableMissionary.COLUMN_NAME, missionary.getName());
		values.put(TableMissionary.COLUMN_MISSION, missionary.getMission());
		values.put(TableMissionary.COLUMN_EMAIL, missionary.getEmail());
		values.put(TableMissionary.COLUMN_DEPARTURE, missionary.getDeparture());
		values.put(TableMissionary.COLUMN_ARRIVAL, missionary.getArrival());
		values.put(TableMissionary.COLUMN_GENDER, missionary.getGender());
		values.put(TableMissionary.COLUMN_IMAGE, missionary.getImage());
		
		//updating row
		int result = db.update(TableMissionary.TABLE_MISSIONARY, values, TableMissionary.COLUMN_ID + " = ?", new String[] { String.valueOf(missionary.getID()) });

		db.close();
		return result;
	
	}
	public List<Missionary> getAllMissionaries() {
		List<Missionary> contactList = new ArrayList<Missionary>();
		// Select All Query
		String selectQuery = "SELECT * FROM " + TableMissionary.TABLE_MISSIONARY +" ORDER BY " + TableMissionary.COLUMN_NAME;

		
		SQLiteDatabase sqlDB = this.getWritableDatabase();
		
		Cursor cursor = sqlDB.rawQuery(selectQuery, null);
		
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Missionary missionary = new Missionary();
				missionary.setID(Integer.parseInt(cursor.getString(0)));
				missionary.setName(cursor.getString(1));
				missionary.setMission(cursor.getString(2));
				missionary.setDeparture(cursor.getString(3));
				missionary.setArrival(cursor.getString(4));
				missionary.setEmail(cursor.getString(5));
				missionary.setGender(cursor.getString(6));
				missionary.setImage(cursor.getString(7));
				
	
				
				// Adding contact to list
				contactList.add(missionary);
			} while (cursor.moveToNext());
		}
		cursor.close();
		// close inserting data from database
		sqlDB.close();
		// return contact list
		return contactList;
	}
	public int getMissionariesCount(){
		String countQuery = "SELECT * FROM " + TableMissionary.TABLE_MISSIONARY;
		SQLiteDatabase sqlDB = this.getReadableDatabase();
		Cursor cursor = sqlDB.rawQuery(countQuery, null);
		cursor.close();
		
		sqlDB.close();
		return cursor.getCount();
	}
	public void addArea(Area area){
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put(TableArea.COLUMN_MISSIONARY_ID, area.getMissionaryID());
		values.put(TableArea.COLUMN_NAME, area.getName());
		values.put(TableArea.COLUMN_STARTDATE, area.getStartDate());
		values.put(TableArea.COLUMN_ENDDATE, area.getEndDate());
		
		//insert row
		db.insert(TableArea.TABLE_AREA, null, values);
		db.close();
	}
	public void deleteArea(Area area) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TableArea.TABLE_AREA, TableArea.COLUMN_ID + " = ?",
				new String[] { String.valueOf(area.getID()) });
		
		db.close();
	}
	public void deleteAllAreasByMissionary(Missionary missionary) {
		SQLiteDatabase db = this.getWritableDatabase();
		
		db.delete(TableArea.TABLE_AREA, TableArea.COLUMN_MISSIONARY_ID + " = ?",
				new String[] { String.valueOf(missionary.getID()) });
		
		db.close();
		
	}
	public int updateArea(Area area){
		
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put(TableArea.COLUMN_ID, area.getID());
		values.put(TableArea.COLUMN_NAME, area.getName());
		values.put(TableArea.COLUMN_STARTDATE, area.getStartDate());
		values.put(TableArea.COLUMN_ENDDATE, area.getEndDate());
		
		//updating row
		int result = db.update(TableArea.TABLE_AREA, values, TableArea.COLUMN_ID + " = ?", new String[] { String.valueOf(area.getID()) });

		db.close();
		return result;
	
	}
	public List<Area> getAllAreas() {
		List<Area> areaList = new ArrayList<Area>();
		// Select All Query
		String selectQuery = "SELECT * FROM " + TableArea.TABLE_AREA +" ORDER BY " + TableArea.COLUMN_NAME;

		
		SQLiteDatabase sqlDB = this.getWritableDatabase();
		
		Cursor cursor = sqlDB.rawQuery(selectQuery, null);
		
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Area area = new Area();
				area.setID(Integer.parseInt(cursor.getString(0)));
				area.setName(cursor.getString(1));
				area.setStartDate(cursor.getString(2));
				area.setEndDate(cursor.getString(3));
				
				// Adding area to list
				areaList.add(area);
			} while (cursor.moveToNext());
		}
		cursor.close();
		// close inserting data from database
		sqlDB.close();
		// return area list
		return areaList;
	}
	public List<Area> getAreaByMissionaryID(int missionaryID) {
		List<Area> areaList = new ArrayList<Area>();
		
		String selectQuery = "SELECT * FROM " + TableArea.TABLE_AREA + " WHERE " + TableArea.COLUMN_MISSIONARY_ID + " = " + missionaryID;
		
		SQLiteDatabase sqlDB = this.getWritableDatabase();
		
		Cursor cursor = sqlDB.rawQuery(selectQuery, null);
		
		if(cursor.moveToFirst()) {
			do {
				Area area = new Area();
				area.setID(Integer.parseInt(cursor.getString(0)));
				area.setName(cursor.getString(1));
				area.setStartDate(cursor.getString(3));
				area.setEndDate(cursor.getString(4));
				
				areaList.add(area);
			} while (cursor.moveToNext());
		}
		cursor.close();
		
		sqlDB.close();
		
		return areaList;
		
	}
	public int getAreasCount(){
		String countQuery = "SELECT * FROM " + TableArea.TABLE_AREA;
		SQLiteDatabase sqlDB = this.getReadableDatabase();
		Cursor cursor = sqlDB.rawQuery(countQuery, null);
		
		int count = cursor.getCount();
		cursor.close();
		
		sqlDB.close();
		return count;
	}
	public void addCompanion(Companion companion){
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put(TableCompanion.COLUMN_AREA_ID, companion.getAreaID());
		values.put(TableCompanion.COLUMN_NAME, companion.getName());
		
		//insert row
		db.insert(TableCompanion.TABLE_COMPANION, null, values);
		db.close();
	}
	public void deleteCompanion(Companion companion) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TableCompanion.TABLE_COMPANION, TableCompanion.COLUMN_ID + " = ?",
				new String[] { String.valueOf(companion.getID()) });
		
		db.close();
	}
	public void deleteAllCompanionsByAreaID(Area area) {
		SQLiteDatabase db = this.getWritableDatabase();
		
		db.delete(TableCompanion.TABLE_COMPANION, TableCompanion.COLUMN_AREA_ID + " = ?",
				new String[] { String.valueOf(area.getID()) });
		
		db.close();
		
	}
	public int updateCompanion(Companion companion){
		
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put(TableCompanion.COLUMN_ID, companion.getID());
		values.put(TableCompanion.COLUMN_AREA_ID, companion.getAreaID());
		values.put(TableCompanion.COLUMN_NAME, companion.getName());
		
		//updating row
		int result = db.update(TableCompanion.TABLE_COMPANION, values, TableCompanion.COLUMN_ID + " = ?", new String[] { String.valueOf(companion.getID()) });

		db.close();
		return result;
	
	}
	public List<Companion> getCompanionByAreaID(int areaID) {
		List<Companion> companionList = new ArrayList<Companion>();
		
		String selectQuery = "SELECT * FROM " + TableCompanion.TABLE_COMPANION + " WHERE " + TableCompanion.COLUMN_AREA_ID + " = " + areaID;
		
		SQLiteDatabase sqlDB = this.getWritableDatabase();
		
		Cursor cursor = sqlDB.rawQuery(selectQuery, null);
		
		if(cursor.moveToFirst()) {
			do {
				Companion companion = new Companion();
				companion.setID(Integer.parseInt(cursor.getString(0)));
				companion.setName(cursor.getString(1));
				companion.setAreaID(Integer.parseInt(cursor.getString(2)));
				
				companionList.add(companion);
			} while (cursor.moveToNext());
		}
		cursor.close();
		
		sqlDB.close();
		
		return companionList;
	}
	public void deleteAllCompanionsByArea(Area area) {
		SQLiteDatabase db = this.getWritableDatabase();
		
		db.delete(TableCompanion.TABLE_COMPANION, TableCompanion.COLUMN_AREA_ID + " = ?",
				new String[] { String.valueOf(area.getID()) });
		
		db.close();
		
	}
	public void deleteAllCompanionsByMissionary(Missionary missionary) {
		SQLiteDatabase db = this.getWritableDatabase();
		
		List<Area> areas = getAreaByMissionaryID(missionary.getID());
		
		for(int i = 0; i < areas.size(); i++)
		{
			deleteAllCompanionsByArea(areas.get(i));
		}
	}

}
