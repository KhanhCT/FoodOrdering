package com.example.deepblue.miniproject2_1451032;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Bill on 18/09/16.
 */
public class GetRequest extends StringRequest {

    private static final String GET_REQUEST_URL = "http://hieuluu2911.net23.net/Documents/Get.php";
    private Map<String, String> params;

    public GetRequest(String username, Response.Listener<String> listener)
    {
        super(Method.POST, GET_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("username", username);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
