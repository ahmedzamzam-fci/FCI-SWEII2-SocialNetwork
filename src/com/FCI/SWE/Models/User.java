package com.FCI.SWE.Models;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;



public class User {
	private long id;
	private String name;
	private String email;
	private String password;
	
	private static User currentActiveUser;

	/**
	 * Constructor accepts user data
	 * 
	 * @param name
	 *            user name
	 * @param email
	 *            user email
	 * @param password
	 *            user provided password
	 */
	public User(String name, String email, String password) {
		this.name = name;
		this.email = email;
		this.password = password;

	}
	
	public User() {
		// TODO Auto-generated constructor stub
	}

	private void setId(long id){
		this.id = id;
	}
	
	public long getId(){
		return id;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getPass() {
		return password;
	}
	
	public static User getCurrentActiveUser(){
		return currentActiveUser;
	}
	
	/**
	 * 
	 * This static method will form UserEntity class using json format contains
	 * user data
	 * 
	 * @param json
	 *            String in json format contains user data
	 * @return Constructed user entity
	 */
	public static User getUser(String json) {

		JSONParser parser = new JSONParser();
		try {
			JSONObject object = (JSONObject) parser.parse(json);
			currentActiveUser = new User(object.get("name").toString(), 
					object.get("email").toString(), object.get("password").toString());
			currentActiveUser.setId(Long.parseLong(object.get("id").toString()));
			return currentActiveUser;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}
	public static User Signout(){
		currentActiveUser=null;
		return currentActiveUser;
	}
	public static User parseUserInfo(String json){

		JSONParser parser= new JSONParser();
		try{
		JSONObject object=(JSONObject)parser.parse(json);
		User user=new User();
		user.setEmail(object.get("email").toString());
		user.setName(object.get("name").toString());
		return user;
		}
		catch(ParseException e){
		e.printStackTrace();
		}


		return null;

		}

	private void setName(String string) {
		name=string;
		
	}

	private void setEmail(String string) {
		email=string;
		
	}




}
