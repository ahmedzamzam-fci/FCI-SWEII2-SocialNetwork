package com.FCI.SWE.Services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;









import org.glassfish.jersey.server.mvc.Viewable;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.FCI.SWE.Models.User;
import com.FCI.SWE.ServicesModels.Datastore_Notification;
import com.FCI.SWE.ServicesModels.FriendRequest;
import com.FCI.SWE.ServicesModels.GroupInfo;
import com.FCI.SWE.ServicesModels.HashtagEntity;
import com.FCI.SWE.ServicesModels.PostEntity;
import com.FCI.SWE.ServicesModels.UserEntity;

/**
 * This class contains REST services, also contains action function for web
 * application
 * 
 * @author Mohamed Samir
 * @version 1.0
 * @since 2014-02-12
 *
 */
@Path("/")
@Produces(MediaType.TEXT_PLAIN)
public class UserServices {
private  static String  current;
private static int Id=0;
	
	/**
	 * Registration Rest service, this service will be called to make
	 * registration. This function will store user data in data store
	 * 
	 * @param uname
	 *            provided user name
	 * @param email
	 *            provided user email
	 * @param pass
	 *            provided password
	 * @return Status json
	 */
	@POST
	@Path("/RegistrationService")
	public String registrationService(@FormParam("uname") String uname,
			@FormParam("email") String email, @FormParam("password") String pass) {
		UserEntity user = new UserEntity(uname, email, pass);
		JSONObject object = new JSONObject();
		if(user.saveUser()==true){
		object.put("Status", "OK");
		return object.toString();
		}
		else if(user.saveUser()==false){
			object.put("Status", "failed");
			
		}
		return object.toString();
	}
	/**
	 * Login Rest Service, this service will be called to make login process
	 * also will check user data and returns new user from datastore
	 * @param uname provided user name
	 * @param pass provided user password
	 * @return user in json format
	 */
	@POST
	@Path("/LoginService")
	public String loginService(@FormParam("uname") String uname,
			@FormParam("password") String pass) {
		
		JSONObject object = new JSONObject();
		UserEntity user = UserEntity.getUser(uname, pass);
		if (user == null) {
			object.put("Status", "Failed");
} else {
			object.put("Status", "OK");
			object.put("name", user.getName());
			object.put("email", user.getEmail());
			object.put("password", user.getPass());
			object.put("id", user.getId());
			current=user.getName();
			
		}
		return object.toString();
		}
	


	
	@POST
	@Path("/AddFriendService")
	public String SendFriendRequest(@FormParam("uname") String reciever) {
		JSONObject object1 = new JSONObject();
		boolean flag = UserEntity.searchUser(reciever);
		if (flag==true) {
			FriendRequest request = new FriendRequest(current, reciever,false);
			if(request.saveRequest(current,reciever)==false){
				
				object1.put("Status", "failed");
			}else{
			object1.put("Status", "OK");
			} 
		}
		else {
			object1.put("Status", "failed");
		}
		return object1.toString() ;
		}
	
	@POST
	@Path("/NotificationService")
	public String save(
	@FormParam("recieve") String recieve, @FormParam("content") String content
	,@FormParam("type") String type ) {
		Datastore_Notification mm=new Datastore_Notification();
		Notification m=new Message_Notification(mm);
		Invoker i=new Invoker(m);
		boolean flag=i.excute(current,recieve,content);
		JSONObject object = new JSONObject();
		if(flag==true){
			object.put("Status", "OK");
		}
		else{
			object.put("Status", "failed");
		}
		return object.toString();
		}
	@POST
	@Path("/CreateGroupConverstaion")
	public String CreateGroupConverstatiom(
	@FormParam("content") String comtent) {
		ArrayList<String>x=new ArrayList<String>();
		int id=getId();
		
		x.add("abc");
		x.add("a");
		boolean flag=notify(current,x,id);
		JSONObject object = new JSONObject();
		if(flag==true){
			object.put("Status", "OK");
		}
		else{
			object.put("Status", "failed");
		}
		setId(id+1);
		return object.toString();
		}
	
	public boolean notify(String userNow,ArrayList<String> x,int identification){
		GroupInfo obj= new GroupInfo();
		boolean flag=obj.update(userNow,x,identification);
		if(flag==true){
			return true;
		}
		else {
			return false;
		}
		}
	
	@POST
	@Path("/PendingRequest")
	public String AcceptFriendRequest(
			@FormParam("reciever") String reciever) {
		JSONObject object1 = new JSONObject();
		boolean flag = FriendRequest.addFriendStore(current,reciever);
		if (flag==true) {
			object1.put("Status", "OK");
			} else {
			
			object1.put("Status", "failed");
		}
		return object1.toString() ;
		}
	
	@POST
	@Path("/SearchHashtag")
	public String searchHashtag(@FormParam("hash") String hash){
		Vector<HashtagEntity> hashtag = HashtagEntity.searchTag(hash);
		JSONArray returnedJson = new JSONArray();
		for (HashtagEntity tag : hashtag) {
			returnedJson.add(tag.toJson());
		}
		return returnedJson.toJSONString();		
	}

	

	public static int getId() {
		return Id;
	}
	public static void setId(int id) {
		Id = id;
	}
	@POST
	@Path("/WritePost")
	public String WritePost(@FormParam("place") String place,@FormParam("content") String content
			){
		boolean hashtag=false;
		int y=content.length();
		int Start=0;
		int end=0;
		for(int i=0;i<y;i++){
			if(content.charAt(i)=='#'){
				Start=i;
				hashtag=true;
				break;
			}
				}
		if(hashtag==true){
	 String temp=content.substring(4,y);
	 int z=temp.length();
	 for(int i=0;i<z;i++){
		if(temp.charAt(i)==' '){
			end=i;
			break;
			}
		}
	 String contentHastag=temp.substring(0,end);
	 HashtagEntity hash=new HashtagEntity(contentHastag,content,current,place);
	 boolean hastagflag=hash.saveHashtag();
	 PostEntity post=new PostEntity(place,current,content);
		JSONObject object = new JSONObject();
		if(post.savePost()==true && hastagflag==true){
		object.put("Status", "OK");
		return object.toString();
		}
		else if(post.savePost()==false){
			object.put("Status", "failed");
			
		}
		return object.toString();
}
else{	
		PostEntity post=new PostEntity(place,current,content);
		JSONObject object = new JSONObject();
		if(post.savePost()==true){
		object.put("Status", "OK");
		return object.toString();
		}
		else if(post.savePost()==false){
			object.put("Status", "failed");
			
		}
		return object.toString();
	
	}
	}
	}