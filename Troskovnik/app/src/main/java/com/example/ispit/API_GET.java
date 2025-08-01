package com.example.ispit;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class API_GET {
    public static void getJSON(String url , final ReadDataHandler rdh ){
        AsyncTask<String, Void, String > task = new AsyncTask<String, Void, String>() {
            String response = "";

            @Override
            protected String doInBackground(String... strings){


                //uspostava sa http konekcijom
                try {
                    URL link = new URL(strings[0]);
                    HttpURLConnection connection = (HttpURLConnection) link.openConnection();
                    BufferedReader br =  new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String red;
                    while((red = br.readLine()) != null ){
                        response += red + "\n";
                    }

                    br.close();
                    connection.disconnect();
                }catch (Exception e){
                    response +=   "[desio exception]" + "\n";
                }

                return response;
            }

            @Override
            protected void onPostExecute(String s){
                rdh.setJson(response);
                rdh.sendEmptyMessage(0);
            }

        };
        task.execute(url);
    }
}
