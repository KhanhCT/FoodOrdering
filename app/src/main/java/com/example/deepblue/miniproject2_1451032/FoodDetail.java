package com.example.deepblue.miniproject2_1451032;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.ShareActionProvider;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class FoodDetail extends AppCompatActivity {
    int ID;
    int numOfTable;
    int getQuantity;
    int isadmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //

        //
        setTitle("");
        //
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        //
        Bundle extras = getIntent().getExtras();
        ID = extras.getInt("id");
        numOfTable = extras.getInt("numOfTable");
        isadmin = extras.getInt("isadmin");
        //
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new docJSON().execute("http://miniproject2.esy.es/getFoodTable.php?table=Table"+numOfTable+"&id="+ID);
            }
        });
        ///


    }

    public void plusQuantity(View view){
        final int plusQ=getQuantity+1;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new goiWebServices().execute("http://miniproject2.esy.es/execute.php?table=Table"+numOfTable+"&action=plusQ&quantity="+plusQ+"&id="+ID);
                new docJSON().execute("http://miniproject2.esy.es/getFoodTable.php?table=Table"+numOfTable+"&id="+ID);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_menu, menu);
        MenuItem shareItem = menu.findItem(R.id.action_share);
        ShareActionProvider myShareActionProvider = (ShareActionProvider) MenuItemCompat
                .getActionProvider(shareItem);

        Intent myIntent = new Intent();
        myIntent.setAction(Intent.ACTION_SEND);
        myIntent.putExtra(Intent.EXTRA_TEXT,
                "Share from the miniProject2...\n" +
                        "http://miniproject2.esy.es/getFoodTable.php?table=Table"+numOfTable+"&id="+ID);
        myIntent.setType("text/plain");

        myShareActionProvider.setShareIntent(myIntent);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), OrderNew.class);
        intent.putExtra("numOfTable",numOfTable);
        intent.putExtra("isadmin",isadmin);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_delete:
                delete();
                return true;
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                onBackPressed();
                return true;
            case R.id.action_share:
                share();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void share() {

    }

    private void delete(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new goiWebServices().execute("http://miniproject2.esy.es/execute.php?table=Table"+numOfTable+"&action=delete&id="+ID);
            }
        });
        Intent intent = new Intent(getApplicationContext(), OrderNew.class);
        intent.putExtra("numOfTable",numOfTable);
        intent.putExtra("isadmin",isadmin);
        startActivity(intent);
    }

    class docJSON extends AsyncTask<String,Integer,String> {

        @Override
        protected String doInBackground(String... params) {
            return docNoiDung_Tu_URL(params[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            //Toast.makeText(MenuOrder.this,s,Toast.LENGTH_LONG).show();
            //ArrayList<String> arrFood = new ArrayList<String>();
            try {
                JSONArray mang = new JSONArray(s);
                for(int i=0;i<mang.length();i++){
                    JSONObject FoodJSON = mang.getJSONObject(i);
                    String foodGetString = FoodJSON.getString("food");
                    getQuantity = FoodJSON.getInt("quantity");
                    int price = FoodJSON.getInt("price");
                    int getID = FoodJSON.getInt("id");
                    String getDate = FoodJSON.getString("date");
                    int getFlag = FoodJSON.getInt("flag");
                    TextView foodName = (TextView)findViewById(R.id.foodName);
                    foodName.setText(foodGetString);
                    TextView foodQuantity = (TextView)findViewById(R.id.quantity);
                    foodQuantity.setText("Quantity: "+getQuantity);
                    TextView foodPrice = (TextView)findViewById(R.id.foodPrice);
                    foodPrice.setText("Price: "+price+" ₫");
                    TextView today = (TextView)findViewById(R.id.today);
                    today.setText(getDate);
                    TextView progress = (TextView)findViewById(R.id.progress);
                    if(getFlag==0) {
                        progress.setText("Incomplete");
                        progress.setTextColor(Color.RED);
                    }
                    else{
                        progress.setText("Completed");
                        progress.setTextColor(Color.GREEN);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private static String docNoiDung_Tu_URL(String theUrl)
    {
        StringBuilder content = new StringBuilder();

        try
        {
            // create a url object
            URL url = new URL(theUrl);

            // create a urlconnection object
            URLConnection urlConnection = url.openConnection();

            // wrap the urlconnection in a bufferedreader
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            String line;

            // read from the urlconnection via the bufferedreader
            while ((line = bufferedReader.readLine()) != null)
            {
                content.append(line + "\n");
            }
            bufferedReader.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return content.toString();
    }

    class goiWebServices extends  AsyncTask<String,Integer,String>{

        @Override
        protected String doInBackground(String... params) {
            return makePostRequest(params[0]);
        }

        @Override
        protected void onPostExecute(String s) {

        }
    }

    private String makePostRequest(String u) {
        HttpClient httpClient = new DefaultHttpClient();

        // URL của trang web nhận request
        HttpPost httpPost = new HttpPost(u);

        // Các tham số truyền

        List nameValuePair = new ArrayList(0);

        //nameValuePair.add(new BasicNameValuePair("action", "delete"));
        //nameValuePair.add(new BasicNameValuePair("id", ID));
        //Encoding POST data
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String kq = "";
        try {
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            kq = EntityUtils.toString(entity);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return kq;
    }
}
