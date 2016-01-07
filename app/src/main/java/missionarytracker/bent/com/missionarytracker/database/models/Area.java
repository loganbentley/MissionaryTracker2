package missionarytracker.bent.com.missionarytracker.database.models;

public class Area {
	
	int _id, missionary_id;
	String _name, _start, _end;
	
	public Area() {
		
	}
	
	public Area(int id, String name, String startDate, String endDate)
	{
		this._id = id;
		this._name = name;
		this._start = startDate;
		this._end = endDate;
	}
	
	public int getID(){
		return this._id;
	}
	public void setID(int id){
		this._id = id;
	}
	public String getName(){
		return this._name;
	}
	public void setName(String name){
		this._name = name;
	}
	public int getMissionaryID() {
		return this.missionary_id;
	}
	public void setMissionaryID(int missionaryID) {
		this.missionary_id = missionaryID;
	}
	public String getStartDate(){
		return this._start;
	}
	public void setStartDate(String startDate){
		this._start = startDate;
	}
	public String getEndDate(){
		return this._end;
	}
	public void setEndDate(String endDate){
		this._end = endDate;
	}

}
