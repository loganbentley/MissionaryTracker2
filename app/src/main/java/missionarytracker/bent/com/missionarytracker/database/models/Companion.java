package missionarytracker.bent.com.missionarytracker.database.models;

public class Companion {
	
	int _id, area_id;
	String _name;
	
	public Companion() {
		
	}
	
	public Companion(int id, String name)
	{
		this._id = id;
		this._name = name;
	}
	
	public int getID(){
		return this._id;
	}
	public int getAreaID() {
		return this.area_id;
	}
	public void setAreaID(int id){
		this.area_id = id;
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
}
