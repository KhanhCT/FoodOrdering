package com.example.deepblue.miniproject2_1451032;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class kitchen extends AppCompatActivity {
    ListView kitchenTable;
    ArrayList<String> table;
    int isadmin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kitchen);
        kitchenTable = (ListView)findViewById(R.id.kitchenTable);
        //
        setTitle("Kitchen");
        //
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        //
        //
        Bundle extras = getIntent().getExtras();
        isadmin = extras.getInt("isadmin");
        //
        table = new ArrayList<String>();

        table.add("Table1");
        table.add("Table2");
        table.add("Table3");
        table.add("Table4");
        table.add("Table5");
        table.add("Table6");
        table.add("Table7");
        table.add("Table8");

        ArrayAdapter adapter = new ArrayAdapter(
                kitchen.this,
                android.R.layout.simple_list_item_1,
                table
        );
        kitchenTable.setAdapter(adapter);

        kitchenTable.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), kitchenTable.class);
                intent.putExtra("isadmin",isadmin);
                intent.putExtra("numOfTable",position+1);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
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
}
