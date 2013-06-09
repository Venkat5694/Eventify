package com.test.eventify.models;

import java.util.List;

import com.google.api.client.util.Key;
import com.test.eventify.CloudEntity;

public class Users {
	
	@Key
    String id;
    
	@Key
	String username;
    
	@Key
	String g_id;
    	
	@Key
	String phone_no;
	
	@Key
	String invited_events;
    
	@Key
	String my_events;
	
	@Key
	CloudEntity entity;
	
	@Key
	List<Users> users;
	
	public Users(){
		
	}
	
	public Users(CloudEntity ce){
		this.entity = ce;
		/*this.id = ce.get("id").toString();
		this.g_id = ce.get("g_id").toString();
		this.username = ce.get("username").toString();
		this.phone_no = ce.get("phone_no").toString();
		this.my_events = ce.get("my_events").toString();
		this.invited_events = ce.get("invited_events").toString();*/
	}
	
	public List<Users> fromEntities(List<CloudEntity> e){
		users.clear();
		for(CloudEntity ce : e){
			users.add(new Users(ce));
		}
		return users;
	}
	
	public CloudEntity createEntity(){
		CloudEntity ce = new CloudEntity("Users");
		ce.put("id", this.id);
		ce.put("username", this.username);
		ce.put("phone_no", this.phone_no);
		ce.put("g_id", this.g_id);
		ce.put("my_events", this.my_events);
		ce.put("invited_events", this.invited_events);
		
		return ce;
	}
	
	
	
	
}
