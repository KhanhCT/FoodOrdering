package com.example.deepblue.miniproject2_1451032;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


public class StaffActivity extends AppCompatActivity {
    EditText etUserName;
    EditText etPassword;
    int isadmin;
    String name;

    Button bLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff);
        etUserName = (EditText)findViewById(R.id.etUserName);
        etPassword = (EditText)findViewById(R.id.etPassword);
        bLogin = (Button)findViewById(R.id.bLogin);
        //
        setTitle("Login");


        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                final String username = etUserName.getText().toString();
                final String password = etPassword.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>(){

                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if(success){
                                int birthyear = jsonResponse.getInt("birthyear");
                                name = jsonResponse.getString("name");
                                String email = jsonResponse.getString("email");
                                isadmin = jsonResponse.getInt("isadmin");
                                /*
                                Intent intent = new Intent(StaffActivity.this, MainActivity.class);
                                intent.putExtra("username", username);
                                intent.putExtra("birthyear", birthyear);
                                intent.putExtra("name", name);
                                intent.putExtra("email", email);
                                */
                                AlertDialog.Builder builder = new AlertDialog.Builder(StaffActivity.this);
                                builder.setMessage("Hi,"+name+"!")
                                        .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (isadmin == 1)
                                                    startActivity(new Intent(StaffActivity.this,MainActivity.class));
                                                else
                                                    startActivity(new Intent(StaffActivity.this,Staff_main.class));
                                            }})
                                        .create()
                                        .show();
                            }
                            else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(StaffActivity.this);
                                builder.setMessage("Login Failed.\nRetry or\nFind out demo account in About.")
                                        .setNegativeButton("Ok", null)
                                        .create()
                                        .show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };

                LoginRequest loginRequest = new LoginRequest(username, password, responseListener);
                RequestQueue queue = Volley.newRequestQueue(StaffActivity.this);
                queue.add(loginRequest);
            }
        });
        //

    }

    public void moveToHome(View view){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("keyScreen",1);
        startActivity(intent);
    }
    public void getInfo(View view){
        startActivity(new Intent(this,info.class));
    }
}
