package com.FCI.SWE.ServicesModels;

import java.util.ArrayList;
import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Transaction;

public class GroupInfo {
	
	public boolean update (String curr,ArrayList<String> memebers,int ID){
		
		SaveInStore(curr,ID);
		int listSize=memebers.size();
		boolean flag=false;
		UserEntity User=new UserEntity();
		for(int i=0;i<listSize;i++){
		String temp=memebers.get(i);
			flag=UserEntity.searchUser(temp);
			if(flag==false){
				return false;
			}
			else {
				flag=true;
			}
			}
		int counter=0;
		for(String x : memebers){
			counter++;
			flag=SaveInStore(x,ID);
			if (flag==false){
				return false;
			}
			else{
				flag=true;
			}
		}
		System.out.println(counter);

		
		return flag;
	}
	public boolean SaveInStore(String x,int ids){
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Transaction txn = datastore.beginTransaction();
		Query gaeQuery = new Query("GroupInfo");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());
		try {
			System.out.println(x);

		Entity employee = new Entity("GroupInfo", list.size() + 2);
		employee.setProperty("memeber",x);
		employee.setProperty("GroupID",ids );
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
