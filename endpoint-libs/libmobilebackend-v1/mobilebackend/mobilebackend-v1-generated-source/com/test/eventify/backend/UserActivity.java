package com.test.eventify.backend;

import android.app.AlertDialog;
import android.app.DownloadManager.Query;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.TileOverlay;
import com.test.eventify.R;
import com.test.eventify.CloudBackendActivity;
import com.test.eventify.CloudCallbackHandler;
import com.test.eventify.CloudEntity;
import com.test.eventify.CloudQuery;
import com.test.eventify.F;
import com.test.eventify.CloudQuery.Order;
import com.test.eventify.CloudQuery.Scope;
import com.test.eventify.models.Users;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;


public class UserActivity extends CloudBackendActivity {
	private static final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss ", Locale.ENGLISH);

	  private static final String BROADCAST_PROP_DURATION = "duration";

	  private static final String BROADCAST_PROP_MESSAGE = "message";

	  
	  
	  // UI components
	  private TextView tvPosts;
	  private EditText etMessage;
	  private Button btSend;

	  // a list of posts on the UI
	  List<CloudEntity> posts = new LinkedList<CloudEntity>();

	  // initialize UI
	  @Override
	  protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_main);
	    tvPosts = (TextView) findViewById(R.id.tvPosts);
	    etMessage = (EditText) findViewById(R.id.etMessage);
	    btSend = (Button) findViewById(R.id.btSend);
	    insertUser();
	    //getUser();
	  }

	private void getUser(String id) {
		F filter = new F();
		  filter = F.eq("ids", id);
		CloudEntity users = new CloudEntity("Users");
		CloudQuery cq = new CloudQuery("Users");
		
		cq.setFilter(filter);
		CloudCallbackHandler<List<CloudEntity>> qhandler = new CloudCallbackHandler<List<CloudEntity>>(){

			@Override
			public void onComplete(List<CloudEntity> result) {
				// TODO Auto-generated method stub
			
				Toast.makeText(getApplicationContext(), "Complete " + result.size(), Toast.LENGTH_SHORT).show();
				for(CloudEntity res : result){
					StringBuilder sb = new StringBuilder();
					Toast.makeText(getApplicationContext(), 
						sb.append(res.getId()), Toast.LENGTH_SHORT).show();
				}
			
			}

			@Override
			public void onError(IOException exception) {
				// TODO Auto-generated method stub
				super.onError(exception);
				Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_LONG).show();
				
			}
			
		};
		getCloudBackend().list(cq, qhandler );
		
		
	}

	private void insertUser() {
		// TODO Auto-generated method stub
		CloudEntity users = new CloudEntity("Users");
		users.put("username", "jhil jhil jhiga");
		users.put("phone_no", "98746513254");
		users.put("g_id","123");
		users.put("my_eventd","[12,34]");
		users.put("invited_events","56,78");
		Users us = new Users(users);
		
		CloudCallbackHandler<CloudEntity> handler = new CloudCallbackHandler<CloudEntity>() {

			@Override
			public void onComplete(CloudEntity results) {
				// TODO Auto-generated method stub
				results.put("ids", results.getId());
				CloudCallbackHandler<CloudEntity> uphandler = new CloudCallbackHandler<CloudEntity>() {

					@Override
					public void onComplete(CloudEntity results) {
						// TODO Auto-generated method stub
						Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_LONG).show();
						String id = results.get("ids").toString();
						Toast.makeText(getApplicationContext(), id = results.get("ids").toString(), Toast.LENGTH_LONG).show();
						getUser(id);
					}

					@Override
					public void onError(IOException exception) {
						// TODO Auto-generated method stub
						super.onError(exception);
						
					}
					
				};

				getCloudBackend().update(results, uphandler);
			}
			
		};
		getCloudBackend().insert(users, handler);

	}

}




