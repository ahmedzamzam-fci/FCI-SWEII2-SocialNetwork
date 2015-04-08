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
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Transaction;

public class Datastore_Notification{
	public String Sender;
	public String Reciever;
	public String type;
	public boolean status;
	public String content;
	
	public Datastore_Notification(){
		
	}
	public Datastore_Notification (String theSender, String theReciever, String Notification_type,String msgContent) {
		type=Notification_type;
		Sender=theSender;
		Reciever=theReciever;
		content=msgContent;
		setStatus(false);
	}

	private void setStatus(boolean b) {
	status=b;
	}
	
	public Boolean saveRequest(String Sender,String Reciever,String content,String type ) {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Transaction txn = datastore.beginTransaction();
		Query gaeQuery = new Query("Notification");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());
		System.out.println("Size = " + list.size());
		try {
		Entity employee = new Entity("Notification", list.size() + 2);

		employee.setProperty("sender", Sender);
		employee.setProperty("Reciever", Reciever);
		employee.setProperty("Status", false);
		employee.setProperty("content", content);
		employee.setProperty("type", type);
		
		datastore.put(employee);
		txn.commit();
		}
		finally{
		if (txn.isActive()) {
		        txn.rollback();
		    }
		}
		
		return true;
		}

	
	}
