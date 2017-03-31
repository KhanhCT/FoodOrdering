package com.example.deepblue.miniproject2_1451032;

import android.app.IntentService;
import android.content.Intent;
import android.provider.CalendarContract;

/**
 * Created by deepblue on 9/13/2016.
 */
public class GCMRegistrationIntentService extends IntentService {
    public static final String REGISTRATION_SUCCESS = "RegistrationSuccess";
    public static final String REGISTRATION_ERROR = "RegistrationError";
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public GCMRegistrationIntentService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }

    private void registerGCM(){
        Intent registrationComplete  = null;
        String token = null;
        try{
            
        }catch (Exception e){

        }
    }
}
