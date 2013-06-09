package com.test.eventify.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.google.api.client.util.Key;
import com.test.eventify.CloudEndpointUtils;
import com.test.eventify.CloudEntity;

/** Implement this class from "Serializable"
* So that you can pass this class Object to another using Intents
* Otherwise you can't pass to another actitivy
* */
public class PlacesList implements Serializable {

	@Key
	public String status;

	@Key
	public List<Place> results;
	
	public List<Place> fromEntities(List<CloudEntity> ce){
		for(CloudEntity pl : ce){
			results.add(new Place(pl));
		}
		return results;
	}

}