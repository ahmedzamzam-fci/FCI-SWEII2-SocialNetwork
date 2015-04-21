package com.FCI.SWE.ServicesModels;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.json.simple.JSONObject;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Transaction;

public class HashtagEntity {
	private String hashtag;
	private String content;
	private String owner;
	private String place;
	

	public  HashtagEntity(String hash,String content1,String owner1,String place1 ){
		hashtag=hash;
		content=content1;
		owner=owner1;
		place=place1;
		
	}
	public String getPlace() {
		return place;
	}
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
	public String getHashtag() {
		return hashtag;
	}
	public void setHashtag(String hashtag) {
		this.hashtag = hashtag;
	}
	public Boolean saveHashtag() {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Transaction txn = datastore.beginTransaction();
		Query gaeQuery = new Query("Hashtags");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());
		System.out.println("Size = " + list.size());
	
		try {
		Entity employee = new Entity("Hashtags", list.size() + 2);

		employee.setProperty("hashtag", this.hashtag);
		employee.setProperty("content", this.content);
		employee.setProperty("owner", this.owner);
		employee.setProperty("place", this.place);

		datastore.put(employee);
		txn.commit();
		}finally{
			if (txn.isActive()) {
		        txn.rollback();
		    }
		}
		return true;

	}
	public static Vector<HashtagEntity> searchTag(String hashTag) {
		DatastoreService dataStore = DatastoreServiceFactory
				.getDatastoreService();
		Query gae = new Query("Hashtags");
		PreparedQuery preparedQuery = dataStore.prepare(gae);
		Vector<HashtagEntity> returnedUsers = new Vector<HashtagEntity>();
		for (Entity entity : preparedQuery.asIterable()) {
			String currentHash = entity.getProperty("hashtag").toString();
			if (currentHash.contains(hashTag)) {
				HashtagEntity tag = new HashtagEntity(entity.getProperty("hashtag").toString(),
						entity.getProperty("content").toString(),
						entity.getProperty("owner").toString(),entity.getProperty("place").toString());
				returnedUsers.add(tag);
			}
		}
		return returnedUsers;

	}
	public JSONObject toJson(){
		JSONObject object = new JSONObject();
		object.put("hashtag", hashtag);
		object.put("content", content);
		object.put("owner", owner);
		object.put("place", place);
		return object;
	}

}
