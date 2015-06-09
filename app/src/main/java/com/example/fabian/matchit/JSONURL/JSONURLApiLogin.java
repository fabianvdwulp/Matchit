package com.example.fabian.matchit.JSONURL;

import android.util.Log;

import com.example.fabian.matchit.GlobalVariables;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class JSONURLApiLogin {

    public static JSONObject getJSONfromURL(String url, String username, String password) {
        InputStream is = null;
        String result = "";
        JSONObject jsonObj = null;


        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httpget = new HttpPost(url);

            String stLoginValues = "grant_type=password&username=" + username + "&password=" + password;
            HttpEntity entityValues = new ByteArrayEntity(stLoginValues.getBytes("UTF-8"));
            httpget.setEntity(entityValues);
            httpget.setHeader("Content-type", "application/json");
            httpget.setHeader("Accept-Language", GlobalVariables.LANGUAGE);

            HttpResponse response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();
            is = entity.getContent();

        } catch (Exception e) {
            Log.e("log_tag", "Error in http connection " + e.toString());
        }

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            result = sb.toString();

        } catch (Exception e) {
            Log.e("log_tag", "Error converting result " + e.toString());
        }

        try {

            jsonObj = new JSONObject(result);
        } catch (JSONException e) {
            Log.e("log_tag", "Error parsing data " + e.toString());
        }

        return jsonObj;
    }
}