package com.example.deepblue.miniproject2_1451032;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.util.SparseBooleanArray;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
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
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderNew extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ListView listView;
    ListMenu adapter;
    ArrayList<Food> MenuList;
    int numOfTable;
    int isadmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_new);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //
        Bundle extras = getIntent().getExtras();
        numOfTable = extras.getInt("numOfTable");
        isadmin = extras.getInt("isadmin");
        setTitle("Table "+numOfTable);
        listView = (ListView)findViewById(R.id.listOrder);
        /////
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new docJSON().execute("http://miniproject2.esy.es/getMenu.php?table=Table"+numOfTable);
            }
        });
        /////

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), FoodDetail.class);
                intent.putExtra("id",MenuList.get(position).getID());
                intent.putExtra("isadmin",isadmin);
                intent.putExtra("numOfTable",numOfTable);
                startActivity(intent);
            }
        });

/*
        if(extras.getInt("keyScreen")==2){

            if(MenuList==null) {
                MenuList = new ArrayList<Food>();
                getDataFromMenu(extras);
                adapter = new ListMenu(this, R.layout.order_row, MenuList);
                listView.setAdapter(adapter);
            }
            else {

                getDataFromMenu(extras);
                listView.setAdapter(adapter);
            }
        }


        if(extras.getInt("keyScreen")==3){
            if(MenuList==null) {
                MenuList = new ArrayList<Food>();
                getDataFromMenu(extras);
                adapter = new ListMenu(this, R.layout.order_row, MenuList);
                listView.setAdapter(adapter);
            }
            else{
                getDataFromMenu(extras);
                listView.setAdapter(adapter);
            }
        }
*/
        //Toast.makeText(this,getTotal(),Toast.LENGTH_LONG).show();
        //Toast.makeText(getApplicationContext(),listView.getAdapter().getCount(),Toast.LENGTH_LONG).show();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MenuOrder.class);
                intent.putExtra("numOfTable",numOfTable);
                intent.putExtra("isadmin",isadmin);
                startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

/*
    private int getTotal() {
        int total=0;

        for(Food t : MenuList)
            total+=t.getPrice();
        return total;
    }
*/
/*
    private void getDataFromMenu(Bundle extras) {
        if (extras != null) {
            String value;
            int price ;
            int id;
            int count = extras.getInt("count");
            //listView = extras.get
            //The key argument here must match that used in the other activity


            //MenuList.add(new Food("Big hero 6", 30000));
            //MenuList.add(new Food("How to train your dragon", 60000));
            //MenuList.add(new Food("Zootopia", 80000));
            for(int i=0;i<count;++i) {
                value = extras.getString("key"+i);
                price = extras.getInt("price"+i);
                id = extras.getInt("id"+i);
                if(value!=null)
                    MenuList.add(new Food(id,value, price));
            }
            //adapter = new ListMenu(this,R.layout.order_row,MenuList);
            //listView.setAdapter(adapter);
            //listView.getAdapter().getCount();
        }
        //setTitle(""+total);
    }
*/
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.order_new, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if(id == R.id.pay){
            int sum=0;
            for(int i=0;i<listView.getAdapter().getCount();++i){
                sum+=(MenuList.get(i).getPrice()*MenuList.get(i).getQuantity());
            }
            setTitle("Table "+numOfTable + " - "+sum+" ₫");
        }
        if (id == R.id.delete) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    new goiWebServices().execute("http://miniproject2.esy.es/execute.php?table=Table"+numOfTable+"&action=destroy");
                    new docJSON().execute("http://miniproject2.esy.es/getMenu.php?table=Table"+numOfTable);
                }
            });
        }
        if (id == R.id.camera) {
            Date now = new Date();
            android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);

            try {
                // image naming and path  to include sd card  appending name you choose for file
                String mPath = Environment.getExternalStorageDirectory().toString() + "/" + now + ".jpg";

                // create bitmap screen capture
                View v1 = getWindow().getDecorView().getRootView();
                v1.setDrawingCacheEnabled(true);
                Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
                v1.setDrawingCacheEnabled(false);

                File imageFile = new File(mPath);

                FileOutputStream outputStream = new FileOutputStream(imageFile);
                int quality = 100;
                bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
                outputStream.flush();
                outputStream.close();

                openScreenshot(imageFile);
            } catch (Throwable e) {
                // Several error may come out with file handling or OOM
                e.printStackTrace();
            }
        }

        return super.onOptionsItemSelected(item);
    }


    private void openScreenshot(File imageFile) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(imageFile);
        intent.setDataAndType(uri, "image/*");
        startActivity(intent);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {

        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {
            Intent intent = new Intent(getApplicationContext(),View1.class);
            Toast.makeText(this,"The Order has been sent.",Toast.LENGTH_LONG).show();
            Bundle extras = getIntent().getExtras();
            int numOfTable = extras.getInt("numOfTable");
            intent.putExtra("isadmin",isadmin);
            intent.putExtra("numOfTable",numOfTable);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
                    MenuList.add(new Food(getID,foodGetString,price,quantity));
                }
                adapter = new ListMenu(OrderNew.this,R.layout.order_row,MenuList);
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

        List nameValuePair = new ArrayList(2);

        nameValuePair.add(new BasicNameValuePair("action", "delete"));
        //nameValuePair.add(new BasicNameValuePair("id", ));
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
