package com.test.eventify.models;

import java.io.Serializable;

import com.google.api.client.util.Key;
import com.test.eventify.CloudEntity;

/** Implement this class from "Serializable"
* So that you can pass this class Object to another using Intents
* Otherwise you can't pass to another actitivy
* */
public class Place implements Serializable {

	@Key
	public String id;
	
	@Key
	public double lat;
	
	@Key
	public double lng;
	
	@Key
	public String event_id;
	
	@Key
	public String name;
	
	@Key
	public String reference;
	
	@Key
	public String icon;
	
	@Key
	public String vicinity;
	
	@Key
	public Geometry geometry;
	
	@Key
	public String formatted_address;
	
	@Key
	public String formatted_phone_number;
	
	@Key
	public CloudEntity entity;
	
	public Place(CloudEntity e){
		this.entity = e;
	}
	public CloudEntity createEntity(){
		CloudEntity ce = new CloudEntity("places");
		ce.put("id", this.id);
		ce.put("event_id", this.event_id);
		ce.put("lat", this.lat);
		ce.put("lng", this.lng);
		ce.put("name", this.name);
		ce.put("address", formatted_address);
		ce.put("phone_no", this.formatted_phone_number);
		return ce;
	}
	
	@Override
	public String toString() {
		return name + " - " + id + " - " + reference;
	}
	
	public static class Geometry implements Serializable
	{
		@Key
		public Location location;
	}
	
	public static class Location implements Serializable
	{
		@Key
		public double lat;
		
		@Key
		public double lng;
	}
	
}
