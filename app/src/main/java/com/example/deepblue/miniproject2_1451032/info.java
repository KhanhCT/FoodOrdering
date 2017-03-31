package com.example.deepblue.miniproject2_1451032;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

public class info extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        //
        setTitle("About");
        //
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
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

    public void DemoManager(View view){
        startActivity(new Intent(info.this,MainActivity.class));
    }
    public void DemoStaff(View view){
        startActivity(new Intent(info.this,Staff_main.class));
    }
}
