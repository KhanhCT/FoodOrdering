package com.example.deepblue.miniproject2_1451032;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ToggleButton;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
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
import java.util.ArrayList;
import java.util.List;

public class kitchenTable extends AppCompatActivity {
    int numOfTable;
    ArrayList<Food> MenuList;
    ListMenu adapter;
    ListView listView;
    ToggleButton toggle;
    int ID;
    int isadmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kitchen_table);
        Bundle extras = getIntent().getExtras();
        isadmin = extras.getInt("isadmin");
        numOfTable = extras.getInt("numOfTable");
        setTitle("Table"+numOfTable);
        //
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        //
        listView = (ListView)findViewById(R.id.listFood);
        //toggle = (ToggleButton)findViewById(R.id.toggle);
        /////
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new docJSON().execute("http://miniproject2.esy.es/getMenu.php?table=Table"+numOfTable);
            }
        });
        /////
    }

    public void onToggle(View view){
        /*
        if(toggle.isChecked()){
            new goiWebServices().execute("http://miniproject2.esy.es/execute.php?table=Table"+numOfTable+"&action=toggle&flag=1&id="+ID);
        }*/

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.kitchen_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                Intent intent = new Intent(getApplicationContext(), kitchen.class);
                intent.putExtra("isadmin", isadmin);
                startActivity(intent);
                // app icon in action bar clicked; goto parent activity.
                return true;
            case R.id.action_done:
                done();
                Intent intent1 = new Intent(getApplicationContext(), kitchen.class);
                intent1.putExtra("isadmin", isadmin);
                startActivity(intent1);// app icon in action bar clicked; goto parent activity.
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void done(){
        ArrayList<Food> a = new ArrayList<Food>();
        final SparseBooleanArray checkedItems = listView.getCheckedItemPositions();
        int checkedItemsCount = checkedItems.size();
        for (int i = 0; i < checkedItemsCount; ++i) {
            // Item position in adapter
            int position = checkedItems.keyAt(i);
            // Add team if item is checked == TRUE!
            if(checkedItems.valueAt(i)) {
                Food t = adapter.getItem(position);
                a.add(adapter.getItem(position));
                //getName = t.getName();
                //getPrice = t.getPrice();
                ID = t.getID();
                //intent.putExtra("key"+i,t.getName());
                //intent.putExtra("price"+i,t.getPrice());
                //intent.putExtra("id"+i,t.getID());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new goiWebServices().execute("http://miniproject2.esy.es/execute.php?table=Table"+numOfTable+"&action=toggle&flag=1&id="+ID);
                    }
                });
            }
        }
    }

    class docJSON extends AsyncTask<String,Integer,String> {

        @Override
        protected String doInBackground(String... params) {
            return docNoiDung_Tu_URL(params[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            MenuList = new ArrayList<Food>();
            //Toast.makeText(OrderNew.this,s,Toast.LENGTH_LONG).show();
            //ArrayList<String> arrFood = new ArrayList<String>();
            try {
                JSONArray mang = new JSONArray(s);
                for(int i=0;i<mang.length();i++){
                    JSONObject FoodJSON = mang.getJSONObject(i);
                    String foodGetString = FoodJSON.getString("food");
                    int price = FoodJSON.getInt("price");
                    int getID = FoodJSON.getInt("id");
                    int quantity = FoodJSON.getInt("quantity");
                    int flag = FoodJSON.getInt("flag");
                    if(flag==0)
                        MenuList.add(new Food(getID,foodGetString,price,quantity));
                }
                adapter = new ListMenu(kitchenTable.this,R.layout.kitchen_table,MenuList);
                listView.setAdapter(adapter);
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
