package com.example.deepblue.miniproject2_1451032;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    EditText etUserName;
    EditText etPassWord;
    EditText etBirthyear;
    EditText etName;
    EditText etEmail;

    Button bRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        //
        setTitle("");

        etBirthyear = (EditText)findViewById(R.id.etBirthyear);
        etPassWord = (EditText)findViewById(R.id.etPassword);
        etUserName = (EditText)findViewById(R.id.etUserName);
        etName = (EditText)findViewById(R.id.etName);
        etEmail = (EditText)findViewById(R.id.etEmail);

        bRegister = (Button)findViewById(R.id.bRegister);

        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String username = etUserName.getText().toString();
                final String password = etPassWord.getText().toString();
                String birthyearstring = etBirthyear.getText().toString();
                final String name = etName.getText().toString();
                final String email = etEmail.getText().toString();
                if (username != null && password != null && name != null && email != null && birthyearstring!=null && birthyearstring !="") {
                    final int birthyear = Integer.parseInt(birthyearstring);
                    Response.Listener<String> responseListener = new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");

                                if (success) {
                                    Intent intent = new Intent(RegisterActivity.this, manage.class);
                                    RegisterActivity.this.startActivity(intent);
                                } else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                    builder.setMessage("Register Failed")
                                            .setNegativeButton("Retry", null)
                                            .create()
                                            .show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    };
                    RegisterRequest registerRequest = new RegisterRequest(username, password, name, birthyear, email, false, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                    queue.add(registerRequest);
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setMessage("Register Failed")
                            .setNegativeButton("Retry", null)
                            .create()
                            .show();
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                startActivity(new Intent(this,manage.class));
                // app icon in action bar clicked; goto parent activity.
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
