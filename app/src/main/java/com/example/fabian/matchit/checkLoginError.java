package com.example.fabian.matchit;

import org.json.JSONException;
import org.json.JSONObject;


public class checkLoginError {

    JSONObject joCheck;

    public checkLoginError(JSONObject joValue) {
        joCheck = joValue;
    }

    public boolean getValue(){

        try {
            // Als string niet bestaat is er geen error, stuurt die een false terug
            String stError = joCheck.getString("error");
            return true;
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }


}
