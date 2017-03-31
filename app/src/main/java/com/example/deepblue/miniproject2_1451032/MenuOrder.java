package com.example.deepblue.miniproject2_1451032;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.ListMenuItemView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

public class MenuOrder extends AppCompatActivity {
    ListView listMenuView;
    ListMenu adapter;
    ArrayList<Food> MenuList;
    String getName;
    int getPrice;
    int getID;
    String date;
    int isadmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MenuList = new ArrayList<Food>();
        /*
        MenuList.add(new Food("Bông bí xào tỏi", 60000));/*
        MenuList.add(new Food("Khổ qua rừng luộc", 60000));
        MenuList.add(new Food("Gà ủ muối", 360000));
        MenuList.add(new Food("Gà hấp thố", 360000));
        MenuList.add(new Food("Lẩu cá miền tây", 200000));
        MenuList.add(new Food("Cơm cháy", 30000));
        MenuList.add(new Food("Lẩu cua đồng", 280000));
        MenuList.add(new Food("Sườn nướng", 65000));
        MenuList.add(new Food("Heo mọi", 160000));
        MenuList.add(new Food("Sườn Hầm", 250000));
        MenuList.add(new Food("Gà ta hấp", 280000));
        MenuList.add(new Food("Khô mực", 105000));
        MenuList.add(new Food("Khoai tây chiên", 30000));
        MenuList.add(new Food("Đậu hủ chiên", 30000));
        MenuList.add(new Food("Đạu bắp nướng", 30000));
        MenuList.add(new Food("Bao tử heo", 180000));
        MenuList.add(new Food("Cà na", 60000));
        MenuList.add(new Food("Sườn xào chua ngọt", 80000));
        */
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Bundle extras = getIntent().getExtras();
        final int numOfTable = extras.getInt("numOfTable");
        isadmin = extras.getInt("isadmin");
        setTitle("Menu-Table "+numOfTable);

        listMenuView = (ListView)findViewById(R.id.menuList);
        /////
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new docJSON().execute("http://miniproject2.esy.es/json.php");
            }
        });
        ////
        //adapter = new ListMenu(this,R.layout.food_row,MenuList);
        //listMenuView.setAdapter(adapter);



//        initControls();
//        loadData();
/*
        ArrayAdapter<Food> adapter

                = new ArrayAdapter<Food>(this,

                android.R.layout.simple_list_item_multiple_choice,

                MenuList);

        listMenuView.setAdapter(adapter);
*/
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), OrderNew.class);
                ArrayList<Food> orderMenu = new ArrayList<Food>();
                final SparseBooleanArray checkedItems = listMenuView.getCheckedItemPositions();
                int checkedItemsCount = checkedItems.size();
                intent.putExtra("count",checkedItemsCount);
                for (int i = 0; i < checkedItemsCount; ++i) {
                    // Item position in adapter
                    int position = checkedItems.keyAt(i);
                    // Add team if item is checked == TRUE!
                    if(checkedItems.valueAt(i)) {
                        Food t = adapter.getItem(position);
                        orderMenu.add(adapter.getItem(position));
                        getName = t.getName();
                        getPrice = t.getPrice();
                        getID = t.getID();
                        intent.putExtra("key"+i,t.getName());
                        intent.putExtra("price"+i,t.getPrice());
                        intent.putExtra("id"+i,t.getID());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                new goiWebServices().execute("http://miniproject2.esy.es/execute.php?table=Table"+numOfTable+"&action=insert&id="+getID);
                            }
                        });
                    }
                }
                //for(Food t : orderMenu)
                //    intent.putExtra("key",t.getName());
                intent.putExtra("numOfTable",numOfTable);
                intent.putExtra("isadmin",isadmin);
                //////////
                /*
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new goiWebServices().execute("http://miniproject2.esy.es/execute.php");
                    }
                });
                */
                // ////////
                startActivity(intent);
            }
        });
    }


    class goiWebServices extends  AsyncTask<String,Integer,String>{

        @Override
        protected String doInBackground(String... params) {
            return makePostRequest(params[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            //Toast.makeText(MenuOrder.this,getName,Toast.LENGTH_LONG).show();
        }
    }

    class docJSON extends AsyncTask<String,Integer,String>{

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
                    int price = FoodJSON.getInt("price");
                    int getID = FoodJSON.getInt("id");
                    MenuList.add(new Food(getID,foodGetString,price,0));
                }
                adapter = new ListMenu(MenuOrder.this,R.layout.food_row,MenuList);
                listMenuView.setAdapter(adapter);
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

    private String makePostRequest(String u) {
        HttpClient httpClient = new DefaultHttpClient();

        // URL của trang web nhận request
        HttpPost httpPost = new HttpPost(u);

        // Các tham số truyền

        List nameValuePair = new ArrayList(1);
        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
        date = df.format(Calendar.getInstance().getTime());
        //Toast.makeText(getApplicationContext(),""+date,Toast.LENGTH_SHORT).show();

        //nameValuePair.add(new BasicNameValuePair("action", "insert"));
        nameValuePair.add(new BasicNameValuePair("date", date));
        /*
        nameValuePair.add(new BasicNameValuePair("food", getName));
        nameValuePair.add(new BasicNameValuePair("price", Integer.toString(getPrice)));
        nameValuePair.add(new BasicNameValuePair("flag", Integer.toString(0)));
        nameValuePair.add(new BasicNameValuePair("quantity", Integer.toString(1)));
        */


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

/*
    private void loadData() {
        MenuList = new ArrayList<Food>();
        MenuList.add(new Food("Big hero 6", 30000));
        MenuList.add(new Food("How to train your dragon", 60000));
        MenuList.add(new Food("Zootopia", 80000));
        MenuList.add(new Food("Big hero 6", 30000));
        MenuList.add(new Food("How to train your dragon", 60000));
        MenuList.add(new Food("Zootopia", 80000));
        MenuList.add(new Food("Big hero 6", 30000));
        MenuList.add(new Food("How to train your dragon", 60000));
        MenuList.add(new Food("Zootopia", 80000));
        //adapter.addAll(MenuList);

    }

    private void initControls() {
        listMenuView = (ListView)findViewById(R.id.menuList);
        adapter = new ListMenu(this,R.layout.food_row,MenuList);
        //MenuList = new ArrayList<>();
        listMenuView.setAdapter(adapter);
        /*listMenuView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Food food = (Food) listMenuView.getItemAtPosition(position);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                //intent.setData(Uri.parse(food.getPrice()));
                startActivity(intent);
            }
        });*/

}
