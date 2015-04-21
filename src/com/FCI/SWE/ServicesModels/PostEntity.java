package com.FCI.SWE.ServicesModels;

import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Transaction;

public class PostEntity {
	private String place;
	private String owner;
	private String content;
	private int NumberOfLikes;
	public PostEntity(String place2, String current, String content2) {
		place=place2;
		owner=current;
		content=content2;
		NumberOfLikes=0;
	}
	public String getPlace() {
		return place;
	}
	public void  PostEntity(){}
	
	
	public void setPlace(String place) {
		this.place = place;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getNumberOfLikes() {
		return NumberOfLikes;
	}
	public void setNumberOfLikes(int numberOfLikes) {
		NumberOfLikes = numberOfLikes;
	}
	
	public Boolean savePost() {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Transaction txn = datastore.beginTransaction();
		Query gaeQuery = new Query("Post");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());
		System.out.println("Size = " + list.size());
		try {
		Entity employee = new Entity("Post", list.size() + 2);

		employee.setProperty("place", this.place);
		employee.setProperty("owner", this.owner);
		employee.setProperty("content", this.content);
		employee.setProperty("NumberOfLikes", this.NumberOfLikes);
		datastore.put(employee);
		txn.commit();
		}finally{
			if (txn.isActive()) {
		        txn.rollback();
		    }
		}
		return true;

	}
	

}
