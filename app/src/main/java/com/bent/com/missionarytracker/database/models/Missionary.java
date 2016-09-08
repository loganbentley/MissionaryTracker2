package com.bent.com.missionarytracker.database.models;

public class Missionary {
	
	int _id;
	String _name, _mission, _departure, _arrival, _email, _gender, _image;
	
	public Missionary() {
		
	}
	
	public Missionary(int id, String name, String mission, String departure, String arrival, String email, String gender, String image)
	{
		this._id = id;
		this._name = name;
		this._mission = mission;
		this._departure = departure;
		this._arrival = arrival;
		this._email = email;
		this._gender = gender;
		this._image = image;
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
	public String getMission(){
		return this._mission;
	}
	public void setMission(String mission){
		this._mission = mission;
	}
	public String getDeparture(){
		return this._departure;
	}
	public void setDeparture(String departure){
		this._departure	= departure;
	}
	public String getArrival(){
		return this._arrival;
	}
	public void setArrival(String arrival){
		this._arrival = arrival;
	}
	public String getEmail(){
		return this._email;
	}
	public void setEmail(String email){
		this._email = email;
	}
	public String getGender(){
		return this._gender;
	}
	public void setGender(String gender){
		this._gender = gender;
	}
	public String getImage(){
		return this._image;
	}
	public void setImage(String image){
		this._image = image;
	}

}
