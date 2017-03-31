package com.example.deepblue.miniproject2_1451032;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class manage extends AppCompatActivity {
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage);
        listView = (ListView)findViewById(R.id.list_staff);
        //
        setTitle("Manager");
        //
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        //
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.manger, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                // app icon in action bar clicked; goto parent activity.
                return true;
            case R.id.add:
                startActivity(new Intent(this,RegisterActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    class GetFromServer {
        RequestQueue queue;
        String username;
        String name;
        int birthyear;
        String email;
        int isadmin;
        GetFromServer(String username, final Context currentActivity){
            this.username = username;

            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        boolean success = jsonResponse.getBoolean("success");

                        if(success){
                            birthyear = jsonResponse.getInt("birthyear");
                            name = jsonResponse.getString("name");
                            email = jsonResponse.getString("email");
                            isadmin = jsonResponse.getInt("isadmin");


                        }
                        else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(currentActivity);
                            builder.setMessage("Get Failed")
                                    .setNegativeButton("Retry", null)
                                    .create()
                                    .show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            };

            GetRequest getRequest = new GetRequest(username, responseListener);
            RequestQueue queue = Volley.newRequestQueue(currentActivity);
            queue.add(getRequest);
        }
    }
}
