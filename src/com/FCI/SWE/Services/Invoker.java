package com.FCI.SWE.Services;

public class Invoker {
	Notification theNotification;
	
	public Invoker(	Notification theObj){
		theNotification=theObj;
		
	}
	
	public boolean  excute(String sender,String  reciver, String content){
		boolean flag=false;
		flag=theNotification.SaveNotification(sender,reciver,content);
		return flag;
	}

}
