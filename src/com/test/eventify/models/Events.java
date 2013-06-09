package com.test.eventify.models;

import java.io.Serializable;
import java.util.List;

import com.google.api.client.util.Key;
import com.test.eventify.CloudEntity;

public class Events implements Serializable {
	@Key
	String id;
	
	@Key
	String place_id;
    
	@Key
	String user_id;
    
	@Key
	String title;
    
	@Key
	List<String> invited_ids;
	
	@Key
	CloudEntity entity;
	
	@Key
	List<Events> events;
	
	public Events(CloudEntity ce){
		this.entity = ce;
	}
	
	public List<Events> fromEntities(List<CloudEntity> e){
		events.clear();
		for(CloudEntity ce : e){
			events.add(new Events(ce));
		}
		return events;
	}
	
	public CloudEntity createEntity(){
		CloudEntity ce = new CloudEntity("Events");
		ce.put("id", this.id);
		ce.put("title", this.title);
		ce.put("place_id", this.place_id);
		ce.put("user_id", this.user_id);
		ce.put("invited_ids", this.invited_ids);
		
		return ce;
	}
	
	
	
	
	
	
	
}
