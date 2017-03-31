package com.example.deepblue.miniproject2_1451032;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Bill on 14/09/16.
 */
public class RegisterRequest extends StringRequest {

    private static final String REGISTER_REQUEST_URL = "http://hieuluu2911.net23.net/Documents/Register.php";
    private Map<String, String> params;

    public RegisterRequest(String username, String password, String name, int birthyear, String email, boolean isadmin, Response.Listener<String> listener)
    {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);
        params.put("name", name);
        params.put("birthyear", birthyear + "");
        params.put("email", email);
        params.put("isadmin", isadmin + "");
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
