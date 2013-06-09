/*
 * Copyright (c) 2013 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package com.test.eventify.sample.guestbook;

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

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

/**
 * Sample Guestbook app with Mobile Backend Starter.
 *
 */
public class GuestbookActivity extends CloudBackendActivity {

  // data formatter for formatting createdAt property
  private static final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss ", Locale.US);

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
    //initdb();
  }

  private void initdb() {
	  F filter = new F();
	  filter = F.eq("ids", "CE:1a0c351d-8b6d-4091-882f-f5665" +
	  		"104b069");
	  initUser();
	//CloudEntity users = new CloudEntity("users");
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
	
	//Toast.makeText(getApplicationContext(), "Running", Toast.LENGTH_LONG).show();
	/*try {
	CloudEntity	result = getCloudBackend().get("users", "CE:1468e3d3-8c69-46ce-abc2-0b76bfea3de5");
	Toast.makeText(getApplicationContext(), result.toString(), Toast.LENGTH_LONG).show();
	Log.i("USER", result.toString());
	} catch (IOException e) {
		// TODO Auto-generated catch block
		Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_LONG).show();
		Log.i("USER", "FAILED");
		e.printStackTrace();
	}*/
	/*users.put("g_id","");
	users.put("my_eventd","");
	users.put("invited_events","");
	CloudCallbackHandler<CloudEntity> handler = new CloudCallbackHandler<CloudEntity>() {

		@Override
		public void onComplete(CloudEntity results) {
			// TODO Auto-generated method stub
			
		}
		
	};
	getCloudBackend().insert(users, handler);
	CloudEntity events = new CloudEntity("events");
	events.put("title","");
	events.put("place_id","");
	events.put("invite_id","");
	CloudCallbackHandler<CloudEntity> ehandler = new CloudCallbackHandler<CloudEntity>() {

		@Override
		public void onComplete(CloudEntity results) {
			// TODO Auto-generated method stub
			
		}
		
	};
	getCloudBackend().insert(events, ehandler);

	CloudEntity places = new CloudEntity("places");
	places.put("longi","");
	places.put("lati", "");
	places.put("address", "");
	CloudCallbackHandler<CloudEntity> phandler = new CloudCallbackHandler<CloudEntity>() {

		@Override
		public void onComplete(CloudEntity results) {
			// TODO Auto-generated method stub
			
		}
		
	};
	getCloudBackend().insert(places, phandler);
*/
}

private void initUser() {
	// TODO Auto-generated method stub
	CloudEntity users = new CloudEntity("Users");
	users.put("g_id","123");
	users.put("my_eventd","[12,34]");
	users.put("invited_events","56,78");
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

@Override
  protected void onPostCreate() {
    super.onPostCreate();
    listAllPosts();
  }

  // execute query "SELECT * FROM Guestbook ORDER BY _createdAt DESC LIMIT 50"
  // this query will be re-executed when matching entity is updated
  private void listAllPosts() {

    // create a response handler that will receive the query result or an error
    CloudCallbackHandler<List<CloudEntity>> handler = new CloudCallbackHandler<List<CloudEntity>>() {
      @Override
      public void onComplete(List<CloudEntity> results) {
        posts = results;
        updateGuestbookUI();
      }

      @Override
      public void onError(IOException exception) {
        handleEndpointException(exception);
      }
    };

    // execute the query with the handler
    getCloudBackend().listByKind("Guestbook", CloudEntity.PROP_CREATED_AT, Order.DESC, 50,
        Scope.FUTURE_AND_PAST, handler);
  }

  private void handleEndpointException(IOException e) {
    Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
    btSend.setEnabled(true);
  }

  // convert posts into string and update UI
  private void updateGuestbookUI() {
    final StringBuilder sb = new StringBuilder();
    
    for (CloudEntity post : posts) {
    	//getPost(post.getId());
      sb.append(sdf.format(post.getCreatedAt()) + getCreatorName(post) + ": " + post.get("message")
          + "\n"+ post.getId());
    }
    tvPosts.setText(sb.toString());
  }

  private void getPost(String id) {
	// TODO Auto-generated method stub
	
	    
}

// removing the domain name part from email address
  private String getCreatorName(CloudEntity b) {
    if (b.getCreatedBy() != null) {
      return " " + b.getCreatedBy().replaceFirst("@.*", "");
    } else {
      return "<anonymous>";
    }
  }

  // post a new message to server
  public void onSendButtonPressed(View view) {
	  initdb();
	  List<String> lst = new ArrayList<String>();
	  lst.add("Fucked to the core");
	  lst.add("Don't give a heck");
	  lst.add("about the bitch");
    // create a CloudEntity with the new post
    CloudEntity newPost = new CloudEntity("Guestbook");
    newPost.put("message", etMessage.getText().toString());
    //newPost.put("values", lst);
    // create a response handler that will receive the result or an error
    CloudCallbackHandler<CloudEntity> handler = new CloudCallbackHandler<CloudEntity>() {
      @Override
      public void onComplete(final CloudEntity result) {
        posts.add(0, result);
        updateGuestbookUI();
        etMessage.setText("");
        btSend.setEnabled(true);
      }

      @Override
      public void onError(final IOException exception) {
        handleEndpointException(exception);
      }
    };

    // execute the insertion with the handler
    getCloudBackend().insert(newPost, handler);
    btSend.setEnabled(false);
  }

  // handles broadcast message and show a toast
  @Override
  public void onBroadcastMessageReceived(List<CloudEntity> l) {
    for (CloudEntity e : l) {
      String message = (String) e.get(BROADCAST_PROP_MESSAGE);
      int duration = Integer.parseInt((String) e.get(BROADCAST_PROP_DURATION));
      Toast.makeText(this, message, duration).show();
    }
  }
}
