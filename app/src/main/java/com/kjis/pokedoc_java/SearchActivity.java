package com.kjis.pokedoc_java;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class SearchActivity extends AppCompatActivity {
    String API_BASE_URL = "https://pokeapi.co/api/v2/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        findViewById(R.id.btnSearch).setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.btnSearch) {
                new Thread() {
                    public void run() {
                        try {
                            JSONObject obj= new JSONObject(callAPI("GET"));
                            System.out.println(obj);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }.start();
            }
        }
    };

    public String callAPI(String requestType) {

        try {
            Log.e("API call", "API 호출");
            // Open Connection
            URL url = new URL(API_BASE_URL + "pokemon/ditto");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(requestType);
            conn.setRequestProperty("Content-Type", "application/json");
            Log.e("getResponseCode", conn.getResponseCode() + " " + conn.HTTP_OK);
            InputStream is = conn.getInputStream();

            // Get Stream
            StringBuilder sb = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            String line;

            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            return sb.toString();

        } catch (Exception e) {
            Log.e("REST_API", requestType + " method failed: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }
}
