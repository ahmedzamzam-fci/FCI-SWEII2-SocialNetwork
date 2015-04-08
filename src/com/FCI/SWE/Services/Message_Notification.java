package com.FCI.SWE.Services;

import com.FCI.SWE.ServicesModels.Datastore_Notification;

public class Message_Notification implements Notification {
    Datastore_Notification obj;
	
    public Message_Notification (Datastore_Notification object){
    	obj=object;
    }
    @Override
    public boolean SaveNotification(String send, String recieve, String z) {
		boolean flag=false;
		flag=obj.saveRequest(send, recieve, z,"Message" );
		return flag;
	}
	}
