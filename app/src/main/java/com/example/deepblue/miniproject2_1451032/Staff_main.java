package com.example.deepblue.miniproject2_1451032;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

public class Staff_main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_main);
        setTitle("Home");
        //
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        //
    }

    public void moveToView1(View view){
        Intent intent = new Intent(getApplicationContext(), View1.class);
        intent.putExtra("isadmin",0);
        startActivity(intent);
        //Intent intent;
        //intent = new Intent();
        //startActivity(intent);
    }
    public void moveToKitchen(View view){
        Intent intent = new Intent(getApplicationContext(), kitchen.class);
        intent.putExtra("isadmin",0);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                startActivity(new Intent(this,StaffActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
