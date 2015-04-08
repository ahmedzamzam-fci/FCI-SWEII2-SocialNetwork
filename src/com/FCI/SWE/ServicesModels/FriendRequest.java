package com.FCI.SWE.ServicesModels;
import java.util.List;
import java.util.Vector;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Transaction;

public class FriendRequest {
	public String Sender;
	public String Reciever;
	private boolean status ;
	
	public FriendRequest(String Send, String Recieve, Boolean f) {
		Sender=Send;
		Reciever=Recieve;
		setStatus(f);
	}
	public FriendRequest() {
		// TODO Auto-generated constructor stub
	}
	public String getSender() {
		return Sender;
	}
	public void setSender(String sender) {
		Sender = sender;
	}
	public String getReciever() {
		return Reciever;
	}
	public void setReciever(String reciever) {
		Reciever = reciever;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public Boolean saveRequest(String Sender,String Reciever) {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Transaction txn = datastore.beginTransaction();
		Query gaeQuery = new Query("FriendRequests");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());
		System.out.println("Size = " + list.size());
		if(searchRequest(Sender,Reciever)==true || Sender==Reciever ){
			return false;
		}
		else{
		try {
		Entity employee = new Entity("FriendRequests", list.size() + 2);

		employee.setProperty("sender", Sender);
		employee.setProperty("Recieve", Reciever);
		employee.setProperty("Status", false);
		
		datastore.put(employee);
		txn.commit();
		}
		finally{
		if (txn.isActive()) {
		        txn.rollback();
		    }
		}
		}
		return true;

	}
	public static boolean  searchRequest(String sender,String reciever) {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        boolean flag=false;
		Query gaeQuery = new Query("FriendRequests");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable()) {
			if (entity.getProperty("sender").toString().equals(sender)&&
					entity.getProperty("Recieve").toString().equals(reciever)) 
			{
					flag=true;	
					break;
			}
			flag=false;
		}
    
	return flag;
	}
	
	public static boolean  addFriendStore(String sender,String reciever) {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Transaction txn = datastore.beginTransaction();
        boolean flag=false;
		Query gaeQuery = new Query("FriendRequests");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable()) {
			if (entity.getProperty("sender").toString().equals(sender)&&
					entity.getProperty("Recieve").toString().equals(reciever) &&
					entity.getProperty("Status").equals(false)
				) 
			{
				
				entity.setProperty("Status",true);
				datastore.put(entity);
				txn.commit();

				flag=true;
				break;
			}
			flag=false;
		}
    
	return flag;
	}
	
	
	
	
	
	public static Vector<String> searchRequest(String uname){
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query gaeQuery = new Query("FriendRequests");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		Vector<String> returnedRequests=new Vector<String>();
		String x;
		for (Entity entity : pq.asIterable()) {
			entity.getKey().getId();
			String currentName=entity.getProperty("Recieve").toString();
			if(currentName.contains(uname)){
				x=entity.getProperty("sender").toString();
				returnedRequests.add(x);
			}
			
		}
		for(int i=0;i<returnedRequests.size();i++){
			System.out.println(returnedRequests.get(i));
		}
		return returnedRequests;
		
		
	}
	private void setId(long id) {
		// TODO Auto-generated method stub
		
	}	
	
	
	
	public static FriendRequest parseUserInfo(String json){
		JSONParser parser =new JSONParser();
		try{
			JSONObject object=(JSONObject)parser.parse(json);
			FriendRequest request=new FriendRequest();
			request.setSender(object.get("sender").toString());
			request.setReciever(object.get("Recieve").toString());
			return request;
			
		}catch(ParseException e){
			e.printStackTrace();
		}
		return null;
	}
	
	
	
}
