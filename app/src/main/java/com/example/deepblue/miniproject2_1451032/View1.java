package com.example.deepblue.miniproject2_1451032;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class View1 extends AppCompatActivity {
    Button b ;
    int status=0;
    ArrayList<Food> MenuList;
    int isadmin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view1);
        setTitle("View");
        //
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        //
        Bundle extras = getIntent().getExtras();
        isadmin = extras.getInt("isadmin");
        //if(keyScreen == 2)
        //progressOrder();
    }
/*
    private void progressOrder() {

        Button table1 = (Button) findViewById(R.id.table1);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new docJSON().execute("http://miniproject2.esy.es/getMenu.php?table=Table1");
            }
        });
        if(status==0){
            table1.setBackgroundColor(Color.););
        }
        else if(status==1){
            table1.setText("Table 1\n" + "Preparing...");
            table1.setTextSize(10);
            table1.setBackgroundColor(Color.RED);
        }else{
            table1.setBackgroundColor(Color.GREEN);
        }
        /*
        ///
        Button table2 = (Button) findViewById(R.id.table2);
        table2.setText("Table 2\n" + "Preparing...");
        if(status==0){
            table2.setBackgroundColor(Color.GRAY);
        }
        else if(status==1){
            table2.setTextSize(10);
            table2.setBackgroundColor(Color.RED);
        }else{
            table1.setBackgroundColor(Color.GREEN);
        }
        Button table3 = (Button) findViewById(R.id.table3);
        table3.setText("Table 3\n" + "Preparing...");
        table3.setTextSize(10);
        table3.setBackgroundColor(Color.RED);

        Button table4 = (Button) findViewById(R.id.table4);
        table4.setText("Table 4\n" + "Preparing...");
        table4.setTextSize(10);
        table4.setBackgroundColor(Color.RED);

        Button table5 = (Button) findViewById(R.id.table5);
        table5.setText("Table 5\n" + "Preparing...");
        table5.setTextSize(10);
        table5.setBackgroundColor(Color.RED);

        Button table6 = (Button) findViewById(R.id.table6);
        table6.setText("Table 6\n" + "Preparing...");
        table6.setTextSize(10);
        table6.setBackgroundColor(Color.RED);

        Button table7 = (Button) findViewById(R.id.table7);
        table7.setText("Table 7\n" + "Preparing...");
        table7.setTextSize(10);
        table7.setBackgroundColor(Color.RED);

        Button table8 = (Button) findViewById(R.id.table8);
        table8.setText("Table 8\n" + "Preparing...");
        table8.setTextSize(10);
        table8.setBackgroundColor(Color.RED);


    }
    */

    public void moveToOrder(View view){
        Intent intent = new Intent(getApplicationContext(), OrderNew.class);
        switch(view.getId()) {
            case R.id.table1:
                intent.putExtra("numOfTable",1);
                break;
            case R.id.table2:
                intent.putExtra("numOfTable",2);
                break;
            case R.id.table3:
                intent.putExtra("numOfTable",3);
                break;
            case R.id.table4:
                intent.putExtra("numOfTable",4);
                break;
            case R.id.table5:
                intent.putExtra("numOfTable",5);
                break;
            case R.id.table6:
                intent.putExtra("numOfTable",6);
                break;
            case R.id.table7:
                intent.putExtra("numOfTable",7);
                break;
            case R.id.table8:
                intent.putExtra("numOfTable",8);
                break;
        }
        intent.putExtra("isadmin",isadmin);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case android.R.id.home:
                if(isadmin==0) {
                    Intent intent = new Intent(getApplicationContext(), Staff_main.class);
                    intent.putExtra("isadmin", 0);
                    startActivity(intent);
                    // app icon in action bar clicked; goto parent activity.
                }
                else {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("isadmin", 1);
                    startActivity(intent);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
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
            //Toast.makeText(MenuOrder.this,s,Toast.LENGTH_LONG).show();
            //ArrayList<String> arrFood = new ArrayList<String>();
            try {
                JSONArray mang = new JSONArray(s);
                for(int i=0;i<mang.length();i++){
                    JSONObject FoodJSON = mang.getJSONObject(i);
                    //IDtable = FoodJSON.getString("IDtable");
                    String foodGetString = FoodJSON.getString("food");
                    int price = FoodJSON.getInt("price");
                    int getID = FoodJSON.getInt("id");
                    int getQuantity = FoodJSON.getInt("quantity");
                    boolean getChecked = FoodJSON.getBoolean("checked");
                    boolean getFlag = FoodJSON.getBoolean("flag");
                    MenuList.add(new Food(getID,foodGetString,price,getQuantity));
                    if(MenuList == null){
                        status=0;
                        break;
                    }
                    else if(getFlag == false){
                        status=1;
                        break;
                    }
                }
                status=2;
                /*
                else if (MenuList != null){
                    for(int i=0;i<mang.length();i++){
                        JSONObject FoodJSON = mang.getJSONObject(i);
                    }
                }
                */
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
}
